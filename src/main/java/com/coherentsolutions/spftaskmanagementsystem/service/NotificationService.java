package com.coherentsolutions.spftaskmanagementsystem.service;

import com.coherentsolutions.spftaskmanagementsystem.config.AppProperties;
import com.coherentsolutions.spftaskmanagementsystem.models.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * Service responsible for handling notifications for Task events.
 * Currently logs messages about task assignment, status changes, and deletions.
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationService {

    // TODO: Extend to support sending real notifications (email, etc.) instead of logging only.
    private final AppProperties appProperties;

    /**
     * Logs assignment notification for the task's assignee.
     */
    public void notifyAssignment(Task task) {
        if (!appProperties.isNotificationsEnabled()) return;
        log.info("Task assigned to [{}]", task.getAssigneeEmail());
    }
    /**
     * Logs assignment notification for the task's status.
     */
    public void notifyStatusChange(Task task) {
        if (!appProperties.isNotificationsEnabled()) return;
        log.info("Task [{}] status changed to [{}]", task.getId(), task.getStatus());
    }

    /**
     * Logs assignment notification for the task's deletion.
     */
    public void notifyDeletion(Long taskId) {
        if (!appProperties.isNotificationsEnabled()) return;
        log.info("Task [{}] has been deleted", taskId);
    }
}
