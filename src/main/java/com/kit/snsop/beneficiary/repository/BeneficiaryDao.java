package com.kit.snsop.beneficiary.repository;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BeneficiaryDao {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void updateBeneficiarySyncStatus(String applicationId, Integer status){
        String sql = "update tbl_BeneficiaryServiceDet set mis_sync_status = :status where household_number = :applicationId";
        Query q = em.createNativeQuery(sql);
        q.setParameter("status", status).setParameter("applicationId", applicationId);
        q.executeUpdate();
    }

    @Transactional
    public void updateBeneficiaryEsSyncStatus(String applicationId, Integer status){
        String sql = "update tbl_BeneficiaryServiceDet set es_sync_status = :status where household_number = :applicationId";
        Query q = em.createNativeQuery(sql);
        q.setParameter("status", status).setParameter("applicationId", applicationId);
        q.executeUpdate();
    }

    public Object[] getBeneficiaryShortInfo(String referenceId){
        String sql = "select b.application_id, b.respondent_first_name, b.respondent_middle_name, b.respondent_last_name, \n"
                + "b.respondent_gender, b.respondent_id, b.respondent_age, b.respondent_phone_no, ad.state_id,\n"
                + "ad.county_id, ad.payam_id, ad.boma_id\n"
                + "from beneficiary b inner join address ad on b.address_id = ad.id\n"
                + "where b.application_id = :applicationId";
        Query q = em.createNativeQuery(sql);
        q.setParameter("applicationId", referenceId);
        return (Object[])q.getSingleResult();
    }

    public Object[] getAlternate1ShortInfo(String referenceId){
        String sql = "select b.application_id, p.payee_first_name, p.payee_middle_name,\n"
                + "p.payee_last_name, p.payee_gender, p.national_id, p.payee_age, p.payee_phone_no\n"
                + "from beneficiary b inner join alternate p on b.alternate_payee_1 = p.id\n"
                + "where b.application_id = :applicationId";
        Query q = em.createNativeQuery(sql);
        q.setParameter("applicationId", referenceId);
        return (Object[])q.getSingleResult();
    }

    public Object[] getAlternate2ShortInfo(String referenceId){
        String sql = "select b.application_id, p.payee_first_name, p.payee_middle_name,\n"
                + "p.payee_last_name, p.payee_gender, p.national_id, p.payee_age, p.payee_phone_no\n"
                + "from beneficiary b inner join alternate p on b.alternate_payee_2 = p.id\n"
                + "where b.application_id = :applicationId";
        Query q = em.createNativeQuery(sql);
        q.setParameter("applicationId", referenceId);
        return (Object[])q.getSingleResult();
    }

}
