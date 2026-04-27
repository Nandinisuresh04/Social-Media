package ViralGurdApi.org.socialMedia.scheduler;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ViralGurdApi.org.socialMedia.service.RedisService;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final RedisService redisService;

    // Runs for every five min
    @Scheduled(fixedRate = 300000)
    public void sweepPendingNotifications() {

        System.out.println(
            "CRON Sweeper Running...");

        // get all users with pending notifications
        Set<String> keys = redisService
            .getAllPendingNotifKeys();

        if(keys == null || keys.isEmpty()) {
            System.out.println(
                "No pending notifications found");
            return;
        }

        // process each user
        for(String key : keys) {

            // extract userId from key
            String userId = key
                .split(":")[1];

            // get all pending messages
            List<String> notifications = 
                redisService.getPendingNotifications(
                    Long.parseLong(userId));

            if(notifications == null || 
                notifications.isEmpty()) {
                continue;
            }

            // count notifications
            int count = notifications.size();

            // get first notification
            String firstNotif = notifications
                .get(0);

            // build summary message
            if(count == 1) {
                System.out.println(
                    "Summarized Push Notification" +
                    " to User " + userId + 
                    ": " + firstNotif);
            } else {
                System.out.println(
                    "Summarized Push Notification" +
                    " to User " + userId + ": " +
                    firstNotif + " and " + 
                    (count - 1) + " others" +
                    " interacted with your posts");
            }

            // clear pending list
            redisService.clearPendingNotifications(
                Long.parseLong(userId));

            System.out.println(
                "Cleared notifications " +
                "for User: " + userId);
        }
    }
}