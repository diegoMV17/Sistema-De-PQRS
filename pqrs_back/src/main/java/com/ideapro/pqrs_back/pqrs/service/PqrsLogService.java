package com.ideapro.pqrs_back.pqrs.service;

import com.ideapro.pqrs_back.pqrs.model.PqrsLog;
import com.ideapro.pqrs_back.pqrs.repository.PqrsLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PqrsLogService {

    private final PqrsLogRepository logRepository;

    public PqrsLogService(PqrsLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public PqrsLog guardar(PqrsLog log) {
        return logRepository.save(log);
    }

    public List<PqrsLog> listarPorPqrs(Long pqrsId) {
        return logRepository.findByPqrsId(pqrsId);
    }
}
