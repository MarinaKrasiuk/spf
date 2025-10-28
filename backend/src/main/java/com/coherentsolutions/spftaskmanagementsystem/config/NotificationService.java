package com.coherentsolutions.spftaskmanagementsystem.config;

import com.coherentsolutions.spftaskmanagementsystem.models.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationService {
    public void notifyAssignment(Task task) {
        log.info("Task assigned to [{}]", task.getAssigneeEmail());
    }

    public void notifyStatusChange(Task task) {
        log.info("Task [{}] status changed to [{}]", task.getId(), task.getStatus());
    }

    public void notifyDeletion(Long taskId) {
        log.info("Task [{}] has been deleted", taskId);
    }
}
