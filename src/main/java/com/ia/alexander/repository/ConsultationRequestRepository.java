package com.ia.alexander.repository;

import com.ia.alexander.entity.ConsultationRequest;
import com.ia.alexander.entity.UserSec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRequestRepository extends JpaRepository<ConsultationRequest, Long> {

    List<ConsultationRequest> findAllByUser (UserSec user);

}
