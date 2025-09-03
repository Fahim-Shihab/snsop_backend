package com.kit.snsop.beneficiary.service;

import com.kit.snsop.beneficiary.domain.PrimaryBeneficiary;
import com.kit.snsop.beneficiary.enums.*;
import com.kit.snsop.beneficiary.model.BeneficiaryDto;
import com.kit.snsop.beneficiary.model.BiometricDto;
import com.kit.snsop.beneficiary.model.GeoLookupDto;
import com.kit.snsop.beneficiary.model.NomineeDto;
import com.kit.snsop.beneficiary.payload.FileUploadResponse;
import com.kit.snsop.beneficiary.repository.BeneficiaryDao;
import com.kit.snsop.beneficiary.repository.BeneficiaryESRepository;
import com.kit.snsop.beneficiary.repository.BeneficiaryRepository;
import com.kit.snsop.beneficiary.repository.PrimaryBeneficiaryRepository;
import com.kit.snsop.common.exceptions.ValidationException;
import com.kit.snsop.lookup.service.geo.GeoLookupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jnbis.api.Jnbis;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BeneficiaryServiceImpl implements BeneficiaryService {

    private final GeoLookupService lookupService;
    private final FileStorageService fileService;
    private final BeneficiaryESRepository esRepository;
    private final BeneficiaryDao beneficiaryDao;
    private final PrimaryBeneficiaryRepository repository;

    @Transactional
    public void saveBeneficiary(BeneficiaryDto beneficiaryDto) {

        if (beneficiaryDto.getBiometric() != null) {
            uploadBiometric(beneficiaryDto.getBiometric(), 0);
        }

        if (beneficiaryDto.getAlternatePayee1() != null && beneficiaryDto.getAlternatePayee1().getBiometric() != null) {
            uploadBiometric(beneficiaryDto.getAlternatePayee1().getBiometric(), 0);
        }

        if (beneficiaryDto.getAlternatePayee2() != null && beneficiaryDto.getAlternatePayee2().getBiometric() != null) {
            uploadBiometric(beneficiaryDto.getAlternatePayee2().getBiometric(), 1);
        }

        PrimaryBeneficiary beneficiaryEO = new PrimaryBeneficiary(beneficiaryDto);
        repository.save(beneficiaryEO);

        try {
            beneficiaryDto.setBiometric(null);
            if (beneficiaryDto.getAlternatePayee1() != null) {
                beneficiaryDto.getAlternatePayee1().setBiometric(null);
            }
            if (beneficiaryDto.getAlternatePayee2() != null) {
                beneficiaryDto.getAlternatePayee2().setBiometric(null);
            }
            beneficiaryDto.setCreated(new Date());
            beneficiaryDto.setUpdated(new Date());
            esRepository.save(beneficiaryDto);
            log.info(" ## ES Migration done for {} ", beneficiaryDto.getApplicationId());
        } catch (Exception ex) {
            beneficiaryDao.updateBeneficiaryEsSyncStatus(beneficiaryDto.getApplicationId(), 0);
            log.error("beneficiary ES sync failed for application id " + beneficiaryDto.getApplicationId(), ex);
        }
        log.info("Beneficiary information with application id {} successfully saved.", beneficiaryEO.getApplicationId());
    }

    public void validate(BeneficiaryDto dto) {

        if (dto.getAddress() != null) {
            try {
                Long bCode = Long.parseLong(dto.getAddress().getBoma());
                GeoLookupDto lookupDto = lookupService.getGeoLookup(bCode);
                if (lookupDto == null || lookupDto.getBCode() == null
                        || lookupDto.getPCode() == null || lookupDto.getCCode() == null
                        || lookupDto.getSCode() == null) {
                    log.error(" null lookup found ");
                    throw new ValidationException("Invalid boma id");
                }
                dto.getAddress().setBoma(lookupDto.getBCode().toString());
                dto.getAddress().setPayam(lookupDto.getPCode());
                dto.getAddress().setCountyId(lookupDto.getCCode());
                dto.getAddress().setStateId(lookupDto.getSCode());
            } catch (Exception ex) {
                log.error("", ex);
                throw new ValidationException("Invalid boma id");
            }

        }

        if (dto.getRespondentMaritalStatus() == MaritalStatusEnum.MARRIED) {
            if (StringUtils.isEmpty(dto.getSpouseFirstName())) {
                throw new ValidationException("Spouse first name should not be empty");
            }

            if (StringUtils.isEmpty(dto.getSpouseMiddleName())) {
                throw new ValidationException("Spouse middle name should not be empty");
            }

            if (StringUtils.isEmpty(dto.getSpouseLastName())) {
                throw new ValidationException("Spouse last name should not be empty");
            }
        }

        if (!dto.getIsOtherMemberPerticipating()) {
            if (dto.getNotPerticipationReason() == null) {
                throw new ValidationException("Non participation reason is missing");
            }
            if (dto.getNotPerticipationReason() == NonPerticipationReasonEnum.REASON_OTHER) {

                if (StringUtils.isEmpty(dto.getNotPerticipationOtherReason())) {
                    /*issue fix.
                     */
                    //throw new ValidationException("Need to specify other reason");
                    dto.setNotPerticipationOtherReason("Unknown");
                }
            }
        }

        if (dto.getRelationshipWithHouseholdHead() != null
                && dto.getRelationshipWithHouseholdHead() == RelationshipEnum.OTHER) {
            if (StringUtils.isEmpty(dto.getRelationshipOther())) {
                throw new ValidationException("Need to specify beneficiary other relationship");
            }
        }

        if (dto.getHouseholdIncomeSource() != null
                && dto.getHouseholdIncomeSource() == IncomeSourceEnum.OTHER) {
            if (StringUtils.isEmpty(dto.getIncomeSourceOther())) {
                throw new ValidationException("Need to specify beneficiary other income source");
            }
        }

        if (dto.getDocumentType() != DocumentTypeEnum.NONE) {
            if (StringUtils.isEmpty(dto.getRespondentId())) {
                throw new ValidationException("ID length should not be null");
            }
        }

        if (dto.getDocumentType() == DocumentTypeEnum.PASSPORT || dto.getDocumentType() == DocumentTypeEnum.NATIONAL_ID) {
            if (dto.getRespondentId().length() > 14 || dto.getRespondentId().length() < 5) {
                throw new ValidationException("ID length should be between 5 and 14 for passport or national ID");
            }
        }
        /* temporary comment
        if (dto.getDocumentType() == DocumentTypeEnum.OTHER) {
            if (StringUtils.isEmpty(dto.getDocumentTypeOther())) {
                throw new ValidationException("Need to specify document name");
            }
        }
         */
        BiometricDto bioDto = dto.getBiometric();

        if (bioDto.isNoFingerPrint()) {
            if (bioDto.getNoFingerprintReason() == null) {
                throw new ValidationException("Need to specify no fingerprint reason");
            }
            if (bioDto.getNoFingerprintReason() == NoFingerprintReason.Other && StringUtils.isEmpty(bioDto.getNoFingerprintReasonText())) {
                throw new ValidationException("Need to specify no fingerprint other reason");
            }
        } else {
            if (bioDto.getWsqLi() == null && bioDto.getWsqLt() == null && bioDto.getWsqLm() == null && bioDto.getWsqLr() == null && bioDto.getWsqLs() == null
                    && bioDto.getWsqRi() == null && bioDto.getWsqRt() == null && bioDto.getWsqRm() == null && bioDto.getWsqRr() == null && bioDto.getWsqRs() == null) {
                throw new ValidationException("Need to provide biometric data for application ID " + bioDto.getApplicationId());
            }
        }

        if (dto.getNominees() != null) {
            for (NomineeDto nomineeDto : dto.getNominees()) {
                if (nomineeDto.getRelationshipWithHouseholdHead() != null
                        && nomineeDto.getRelationshipWithHouseholdHead() == RelationshipEnum.OTHER) {
                    if (StringUtils.isEmpty(nomineeDto.getRelationshipOther())) {
                        throw new ValidationException("Need to specify nominee other relationship");
                    }
                }

                /*occupation issue fix. new enum SELECT added. if SELECT found,
                replacing it with OTHER and other text N/A is being set
                 */
                if (nomineeDto.getNomineeOccupation() != null
                        && nomineeDto.getNomineeOccupation() == OccupationEnum.SELECT) {
                    nomineeDto.setNomineeOccupation(OccupationEnum.OTHER);
                    nomineeDto.setOtherOccupation("Unknown");
                }
            }
        }

        if (dto.getAlternatePayee1() != null) {

            if (dto.getAlternatePayee1().getDocumentType() != DocumentTypeEnum.NONE) {
                if (StringUtils.isEmpty(dto.getAlternatePayee1().getNationalId())) {
                    throw new ValidationException("ID length should not be null");
                }
            }

            if (dto.getAlternatePayee1().getDocumentType() == DocumentTypeEnum.PASSPORT || dto.getAlternatePayee1().getDocumentType() == DocumentTypeEnum.NATIONAL_ID) {
                if (dto.getAlternatePayee1().getNationalId().length() > 14 || dto.getAlternatePayee1().getNationalId().length() < 5) {
                    throw new ValidationException("Alternate ID length should be between 5 and 14 for passport or national ID");
                }
            }

            /* temporary comment
            if (dto.getAlternatePayee1().getDocumentType() == DocumentTypeEnum.OTHER) {
                if (StringUtils.isEmpty(dto.getAlternatePayee1().getDocumentTypeOther())) {
                    throw new ValidationException("Need to specify alternate document name");
                }
            }
             */
            if (dto.getAlternatePayee1().getRelationshipWithHouseholdHead() != null
                    && dto.getAlternatePayee1().getRelationshipWithHouseholdHead() == RelationshipEnum.OTHER) {
                if (StringUtils.isEmpty(dto.getAlternatePayee1().getRelationshipOther())) {
                    throw new ValidationException("Need to specify alternate other relationship");
                }
            }

            BiometricDto biometricDto = dto.getAlternatePayee1().getBiometric();

            if (biometricDto.isNoFingerPrint()) {
                if (biometricDto.getNoFingerprintReason() == null) {
                    throw new ValidationException("Need to specify no fingerprint reason");
                }
                if (biometricDto.getNoFingerprintReason() == NoFingerprintReason.Other && StringUtils.isEmpty(biometricDto.getNoFingerprintReasonText())) {
                    throw new ValidationException("Need to specify no fingerprint other reason");
                }
            } else {
                if (biometricDto.getWsqLi() == null && biometricDto.getWsqLt() == null && biometricDto.getWsqLm() == null && biometricDto.getWsqLr() == null && biometricDto.getWsqLs() == null
                        && biometricDto.getWsqRi() == null && biometricDto.getWsqRt() == null && biometricDto.getWsqRm() == null && biometricDto.getWsqRr() == null && biometricDto.getWsqRs() == null) {
                    throw new ValidationException("Need to provide biometric data for alternate 1 of application " + biometricDto.getApplicationId());
                }
            }

        }

        if (dto.getAlternatePayee2() != null) {

            if (dto.getAlternatePayee2().getDocumentType() != DocumentTypeEnum.NONE) {
                if (StringUtils.isEmpty(dto.getAlternatePayee2().getNationalId())) {
                    throw new ValidationException("ID length should not be null");
                }
            }

            if (dto.getAlternatePayee2().getDocumentType() == DocumentTypeEnum.PASSPORT || dto.getAlternatePayee2().getDocumentType() == DocumentTypeEnum.NATIONAL_ID) {
                if (dto.getAlternatePayee2().getNationalId().length() > 14 || dto.getAlternatePayee2().getNationalId().length() < 5) {
                    throw new ValidationException("Alternate ID length should be between 5 and 14 for passport or national ID");
                }
            }
            /* temporary comment
            if (dto.getAlternatePayee2().getDocumentType() == DocumentTypeEnum.OTHER) {
                if (StringUtils.isEmpty(dto.getAlternatePayee2().getDocumentTypeOther())) {
                    throw new ValidationException("Need to specify alternate document name");
                }
            }
             */

            if (dto.getAlternatePayee2().getRelationshipWithHouseholdHead() != null
                    && dto.getAlternatePayee2().getRelationshipWithHouseholdHead() == RelationshipEnum.OTHER) {
                if (StringUtils.isEmpty(dto.getAlternatePayee2().getRelationshipOther())) {
                    throw new ValidationException("Need to specify alternate other relationship");
                }
            }

            BiometricDto biometricDto = dto.getAlternatePayee2().getBiometric();

            if (biometricDto.isNoFingerPrint()) {
                if (biometricDto.getNoFingerprintReason() == null) {
                    throw new ValidationException("Need to specify no fingerprint reason");
                }
                if (biometricDto.getNoFingerprintReason() == NoFingerprintReason.Other && StringUtils.isEmpty(biometricDto.getNoFingerprintReasonText())) {
                    throw new ValidationException("Need to specify no fingerprint other reason");
                }
            } else {
                if (biometricDto.getWsqLi() == null && biometricDto.getWsqLt() == null && biometricDto.getWsqLm() == null && biometricDto.getWsqLr() == null && biometricDto.getWsqLs() == null
                        && biometricDto.getWsqRi() == null && biometricDto.getWsqRt() == null && biometricDto.getWsqRm() == null && biometricDto.getWsqRr() == null && biometricDto.getWsqRs() == null) {
                    throw new ValidationException("Need to provide biometric data for alternate 2 of application " + biometricDto.getApplicationId());
                }
            }
        }
    }

    private void uploadBiometric(BiometricDto biometricDto, int seq) {
        FileUploadResponse uploadResponse = null;
        if(biometricDto.getPhotoData() != null){
            uploadResponse = fileService.save(biometricDto.getPhotoData(), biometricDto.getApplicationId(), biometricDto.getBiometricUserType(), BiometricType.PHOTO, seq);
            if (uploadResponse != null) {
                biometricDto.setPhoto(uploadResponse.getUrl());
            }
        }
        if(biometricDto.getWsqLtData() != null){
            uploadResponse = fileService.save(biometricDto.getWsqLtData(), biometricDto.getApplicationId(), biometricDto.getBiometricUserType(), BiometricType.LT, seq);
            if (uploadResponse != null) {
                biometricDto.setWsqLt(uploadResponse.getUrl());
            }
        }
        if(biometricDto.getWsqLiData() != null){
            uploadResponse = fileService.save(biometricDto.getWsqLiData(), biometricDto.getApplicationId(), biometricDto.getBiometricUserType(), BiometricType.LI, seq);
            if (uploadResponse != null) {
                biometricDto.setWsqLi(uploadResponse.getUrl());
            }
        }
        if(biometricDto.getWsqLmData() != null){
            uploadResponse = fileService.save(biometricDto.getWsqLmData(), biometricDto.getApplicationId(), biometricDto.getBiometricUserType(), BiometricType.LM, seq);
            if (uploadResponse != null) {
                biometricDto.setWsqLm(uploadResponse.getUrl());
            }
        }
        if(biometricDto.getWsqLrData() != null){
            uploadResponse = fileService.save(biometricDto.getWsqLrData(), biometricDto.getApplicationId(), biometricDto.getBiometricUserType(), BiometricType.LR, seq);
            if (uploadResponse != null) {
                biometricDto.setWsqLr(uploadResponse.getUrl());
            }
        }
        if(biometricDto.getWsqLsData() != null){
            uploadResponse = fileService.save(biometricDto.getWsqLsData(), biometricDto.getApplicationId(), biometricDto.getBiometricUserType(), BiometricType.LL, seq);
            if (uploadResponse != null) {
                biometricDto.setWsqLs(uploadResponse.getUrl());
            }
        }

        if(biometricDto.getWsqRtData() != null){
            uploadResponse = fileService.save(biometricDto.getWsqRtData(), biometricDto.getApplicationId(), biometricDto.getBiometricUserType(), BiometricType.RT, seq);
            if (uploadResponse != null) {
                biometricDto.setWsqRt(uploadResponse.getUrl());
            }
        }
        if(biometricDto.getWsqRiData() != null){
            uploadResponse = fileService.save(biometricDto.getWsqRiData(), biometricDto.getApplicationId(), biometricDto.getBiometricUserType(), BiometricType.RI, seq);
            if (uploadResponse != null) {
                biometricDto.setWsqRi(uploadResponse.getUrl());
            }
        }
        if(biometricDto.getWsqRmData() != null){
            uploadResponse = fileService.save(biometricDto.getWsqRmData(), biometricDto.getApplicationId(), biometricDto.getBiometricUserType(), BiometricType.RM, seq);
            if (uploadResponse != null) {
                biometricDto.setWsqRm(uploadResponse.getUrl());
            }
        }
        if(biometricDto.getWsqRrData() != null){
            uploadResponse = fileService.save(biometricDto.getWsqRrData(), biometricDto.getApplicationId(), biometricDto.getBiometricUserType(), BiometricType.RR, seq);
            if (uploadResponse != null) {
                biometricDto.setWsqRr(uploadResponse.getUrl());
            }
        }
        if(biometricDto.getWsqRsData() != null){
            uploadResponse = fileService.save(biometricDto.getWsqRsData(), biometricDto.getApplicationId(), biometricDto.getBiometricUserType(), BiometricType.RL, seq);
            if (uploadResponse != null) {
                biometricDto.setWsqRs(uploadResponse.getUrl());
            }
        }
    }

    public BeneficiaryDto getBeneficiary(String applicationId) {
        Optional<PrimaryBeneficiary> beneficiaryOp = repository.findByApplicationId(applicationId);

        if (beneficiaryOp.isPresent()) {
            PrimaryBeneficiary beneficiary = beneficiaryOp.get();
            BeneficiaryDto beneficiaryDto = new BeneficiaryDto(beneficiary);
            BiometricDto bioDto = setBiometricData(beneficiaryDto.getBiometric());
            beneficiaryDto.setBiometric(bioDto);
            if(beneficiaryDto.getAlternatePayee1() != null){
                bioDto = setBiometricData(beneficiaryDto.getAlternatePayee1().getBiometric());
                beneficiaryDto.getAlternatePayee1().setBiometric(bioDto);
            }
            if(beneficiaryDto.getAlternatePayee2() != null){
                bioDto = setBiometricData(beneficiaryDto.getAlternatePayee2().getBiometric());
                beneficiaryDto.getAlternatePayee2().setBiometric(bioDto);
            }
            return beneficiaryDto;
        }
        return null;
    }

    public BiometricDto setBiometricData(BiometricDto bio) {
        if (!StringUtils.isEmpty(bio.getPhoto())) {
            if (!StringUtils.isEmpty(bio.getPhoto())) {
                try {
                    byte[] photoData = fileService.getObjectAsByte(bio.getPhoto());
                    bio.setPhotoData(photoData);
                } catch (Exception ex) {
                    log.error("", ex);
                }
            }
        }
        if (!StringUtils.isEmpty(bio.getWsqLt())) {
            try {
                if (!StringUtils.isEmpty(bio.getWsqLt())) {
                    byte[] wsqData = fileService.getObjectAsByte(bio.getWsqLt());
                    byte[] jpgData = Jnbis.wsq().decode(wsqData).toJpg().asByteArray();
                    bio.setWsqLtData(jpgData);
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
        if (!StringUtils.isEmpty(bio.getWsqLi())) {
            try {
                if (!StringUtils.isEmpty(bio.getWsqLi())) {
                    byte[] wsqData = fileService.getObjectAsByte(bio.getWsqLi());
                    byte[] jpgData = Jnbis.wsq().decode(wsqData).toJpg().asByteArray();
                    bio.setWsqLiData(jpgData);
                }
            } catch (Exception ex) {
                log.error("", ex);
            }

        }
        if (!StringUtils.isEmpty(bio.getWsqLm())) {
            try {
                if (!StringUtils.isEmpty(bio.getWsqLm())) {
                    byte[] wsqData = fileService.getObjectAsByte(bio.getWsqLm());
                    byte[] jpgData = Jnbis.wsq().decode(wsqData).toJpg().asByteArray();
                    bio.setWsqLmData(jpgData);
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
        if (!StringUtils.isEmpty(bio.getWsqLr())) {
            try {
                if (!StringUtils.isEmpty(bio.getWsqLr())) {
                    byte[] wsqData = fileService.getObjectAsByte(bio.getWsqLr());
                    byte[] jpgData = Jnbis.wsq().decode(wsqData).toJpg().asByteArray();
                    bio.setWsqLrData(jpgData);
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
        if (!StringUtils.isEmpty(bio.getWsqLs())) {
            try {
                if (!StringUtils.isEmpty(bio.getWsqLs())) {
                    byte[] wsqData = fileService.getObjectAsByte(bio.getWsqLs());
                    byte[] jpgData = Jnbis.wsq().decode(wsqData).toJpg().asByteArray();
                    bio.setWsqLsData(jpgData);
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
        if (!StringUtils.isEmpty(bio.getWsqRt())) {
            try {
                if (!StringUtils.isEmpty(bio.getWsqRt())) {
                    byte[] wsqData = fileService.getObjectAsByte(bio.getWsqRt());
                    byte[] jpgData = Jnbis.wsq().decode(wsqData).toJpg().asByteArray();
                    bio.setWsqRtData(jpgData);
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
        if (!StringUtils.isEmpty(bio.getWsqRi())) {
            try {
                if (!StringUtils.isEmpty(bio.getWsqRi())) {
                    byte[] wsqData = fileService.getObjectAsByte(bio.getWsqRi());
                    byte[] jpgData = Jnbis.wsq().decode(wsqData).toJpg().asByteArray();
                    bio.setWsqRiData(jpgData);
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
        if (!StringUtils.isEmpty(bio.getWsqRm())) {
            try {
                if (!StringUtils.isEmpty(bio.getWsqRm())) {
                    byte[] wsqData = fileService.getObjectAsByte(bio.getWsqRm());
                    byte[] jpgData = Jnbis.wsq().decode(wsqData).toJpg().asByteArray();
                    bio.setWsqRmData(jpgData);
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
        if (!StringUtils.isEmpty(bio.getWsqRr())) {
            try {
                if (!StringUtils.isEmpty(bio.getWsqRr())) {
                    byte[] wsqData = fileService.getObjectAsByte(bio.getWsqRr());
                    byte[] jpgData = Jnbis.wsq().decode(wsqData).toJpg().asByteArray();
                    bio.setWsqRrData(jpgData);
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
        if (!StringUtils.isEmpty(bio.getWsqRs())) {
            try {
                if (!StringUtils.isEmpty(bio.getWsqRs())) {
                    byte[] wsqData = fileService.getObjectAsByte(bio.getWsqRs());
                    byte[] jpgData = Jnbis.wsq().decode(wsqData).toJpg().asByteArray();
                    bio.setWsqRsData(jpgData);
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
        }

        return bio;
    }

    @Transactional
    public void updateBeneficiary(BeneficiaryDto.BeneficiaryEditRequest beneficiaryDto) {

    }

    public void validate(BeneficiaryDto.BeneficiaryEditRequest dto) {

    }

}
