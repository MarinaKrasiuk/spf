package com.coherentsolutions.spftaskmanagementsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private boolean notificationsEnabled = true;

    public boolean isNotificationsEnabled() { return notificationsEnabled; }

    public void setNotificationsEnabled(boolean notificationsEnabled) { this.notificationsEnabled = notificationsEnabled; }
}
