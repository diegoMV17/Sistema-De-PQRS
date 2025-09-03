package com.ideapro.pqrs_back.pqrs.repository;

import com.ideapro.pqrs_back.pqrs.model.PqrsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PqrsLogRepository extends JpaRepository<PqrsLog, Long> {
    List<PqrsLog> findByPqrsId(Long pqrsId);
}
