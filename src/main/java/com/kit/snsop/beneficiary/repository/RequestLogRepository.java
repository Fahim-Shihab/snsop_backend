package com.kit.snsop.beneficiary.repository;

import com.kit.snsop.beneficiary.domain.RequestLog;
import com.kit.snsop.beneficiary.enums.LogTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, Long>, JpaSpecificationExecutor<RequestLog> {

    @Query("SELECT r FROM RequestLog r " +
            "WHERE r.requestDate = (SELECT MAX(r2.requestDate) FROM RequestLog r2 WHERE r2.applicationId = r.applicationId) " +
            "AND r.requestId IN :requestId")
    List<RequestLog> findByRequestIdIn(@Param("requestId") List<String> requestId);


    @Query("SELECT r FROM RequestLog r " +
            "WHERE r.requestDate = (SELECT MAX(r2.requestDate) FROM RequestLog r2 WHERE r2.applicationId = r.applicationId and r2.operationType = :operationType) " +
            "AND r.requestId IN :requestId and r.operationType = :operationType")
    List<RequestLog> findByOperationTypeAndRequestIdIn(@Param("requestId") List<String> requestId, @Param("operationType") String operationType);

    Optional<RequestLog> findByRequestId(String requestId);
    Optional<RequestLog> findByApplicationId(String applicationId);
    Optional<RequestLog> findByApplicationIdAndOperationType(String applicationId, LogTypeEnum operationType);

    //    @Query("SELECT r " +
//            "FROM RequestLog r " +
//            "JOIN Beneficiary b ON r.applicationId = b.applicationId " +
//            "JOIN b.address a " +
//            "WHERE a.boma = :bomaId")
//    List<RequestLog> findByBoma(@Param("bomaId") Long bomaId);
}
