package com.kit.snsop.beneficiary.service;

import com.kit.snsop.beneficiary.enums.BiometricType;
import com.kit.snsop.beneficiary.enums.BiometricUserType;
import com.kit.snsop.beneficiary.payload.FileUploadResponse;
import com.kit.snsop.common.exceptions.ServiceException;
import com.kit.snsop.common.exceptions.ValidationException;
import com.kit.snsop.config.MinioConfig;
import io.minio.*;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {

    private final MinioClient minioClient;

    public FileUploadResponse save(MultipartFile file, String registrationId, BiometricUserType userType, BiometricType type, Integer seq) {
        ObjectWriteResponse response = null;
        if (file == null) {
            throw new ValidationException("Uploaded file is empty");
        }
        if (StringUtils.isEmpty(registrationId)) {
            throw new ValidationException("applicationId is empty");
        }
        if (userType == null) {
            throw new ValidationException("Biometric user type is empty");
        }
        if (type == null) {
            throw new ValidationException("Biometric type is empty");
        }
        if (seq == null) {
            seq = 0;
        }

        String url = userType.name() + "-" + type.name() + "-" + registrationId + "-" + seq;
        try {
            response = save(file.getInputStream(), url, file.getSize());
        } catch (Exception ex) {
            log.error("unable to upload file", ex);
            throw new ServiceException();
        }

        FileUploadResponse uploadResponse = FileUploadResponse.builder()
                .applicationId(registrationId)
                .etag(response.etag())
                .url(url).build();
        return uploadResponse;
    }

    public FileUploadResponse save(byte[] input, String registrationId, BiometricUserType userType, BiometricType type, Integer seq) {
        ObjectWriteResponse response = null;
        if (input == null || input.length == 0) {
            throw new ValidationException("Uploaded file is empty");
        }
        if (StringUtils.isEmpty(registrationId)) {
            throw new ValidationException("applicationId is empty");
        }
        if (userType == null) {
            throw new ValidationException("Biometric user type is empty");
        }
        if (type == null) {
            throw new ValidationException("Biometric type is empty");
        }
        if (seq == null) {
            seq = 0;
        }

        String url = userType.name() + "-" + type.name() + "-" + registrationId + "-" + seq;
        try {
            InputStream is = new ByteArrayInputStream(input);
            response = save(is, url, input.length);
        } catch (Exception ex) {
            log.error("unable to upload file", ex);
            throw new ServiceException();
        }

        FileUploadResponse uploadResponse = FileUploadResponse.builder()
                .applicationId(registrationId)
                .etag(response.etag())
                .url(url).build();
        return uploadResponse;
    }

    public ObjectWriteResponse save(InputStream is, String url, long size, String bucketName) {
        ObjectWriteResponse response;

        try {
            response = minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(bucketName)
                            .object(url)
                            .stream(is, size, -1)
                            .build()
            );
        } catch (Exception ex) {
            log.error("unable to upload file", ex);
            throw new ServiceException();
        }

        if (response == null) {
            throw new ServiceException("Could not upload file. Response is empty");
        }

        log.info("biometric uploaded successfully. {}", response.etag());

        return response;
    }

    public ObjectWriteResponse save(InputStream is, String url, long size) {
        return save(is, url, size, MinioConfig.COMMON_BUCKET_NAME);
    }

    public InputStream getInputStream(String url) throws Exception {
        return minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(MinioConfig.COMMON_BUCKET_NAME)
                        .object(url)
                        .build());
    }

    public boolean remove(String url) {
        try {
            minioClient.removeObjects(
                    RemoveObjectsArgs
                            .builder()
                            .bucket(MinioConfig.TEMP_BUCKET)
                            .objects(Collections.singletonList(new DeleteObject(url)))
                            .build());
            return true;
        } catch (Throwable r) {
            r.printStackTrace();
            return false;
        }
    }

    public boolean remove(String url, String bucket) {
        try {
            minioClient.removeObjects(
                    RemoveObjectsArgs
                            .builder()
                            .bucket(bucket)
                            .objects(Collections.singletonList(new DeleteObject(url)))
                            .build());
            return true;
        } catch (Throwable r) {
            r.printStackTrace();
            return false;
        }
    }

    public byte[] getObjectAsByte(String url) throws Exception {
        return getObjectAsByte(url, false);
    }

    public byte[] getObjectAsByte(String url, boolean saveLocation) throws Exception {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        InputStream is = getInputStream(url);
        byte[] img = IOUtils.toByteArray(is);
        FileOutputStream fos = null;
        if(saveLocation && img != null && img.length > 0){
            try{
                fos = new FileOutputStream("wsq-"+url);
                fos.write(img);
                fos.close();
            }catch(Exception ex){
                log.error("<>", ex);
            }
            finally{
                if(fos != null){
                    fos.close();
                }
            }
        }
        return img;
    }
}
