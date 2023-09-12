package com.globalLogic.challenge.adapter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("SchedulerTaskService")
@RequiredArgsConstructor
@Slf4j
public class SchedulerTaskService {

    @Value("${schedule.batch.enabled}")
    private Boolean scheduledBatchenabled;

    private final SessionService sessionService;
    private static final int PROCESS_BATCH_SCHEDULED = 60000; 	//1 minuto (esta en miliseg)

    @Scheduled(fixedRate = PROCESS_BATCH_SCHEDULED)
    public void processScheduledBatch() {
        if(scheduledBatchenabled) {
            log.info("Updating login status in time: {}", LocalDateTime.now());
            sessionService.updateActivesSessions();
        } else {
            log.info("Session updating status is turned off");
        }
    }

}
