package com.coherentsolutions.spftaskmanagementsystem.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuditService {
    public void log(String message) {
        log.info("AUDIT: [{}] " , message);
    }
}
