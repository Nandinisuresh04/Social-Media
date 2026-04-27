package ViralGurdApi.org.socialMedia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RedisService redisService;

    // Called every time a ai interacts with a user's post
    public void handleBotNotification(
            Long userId, String botName) {

        String message = botName + 
            " replied to your post";

        // check if user was notified in last 15 minutes
        boolean cooldownActive = redisService
            .isNotificationCooldownActive(userId);

        if(cooldownActive) {
            redisService.addPendingNotification(
                userId, message);
            System.out.println(
                "Notification queued for User: " 
                + userId + " → " + message);
        } else {
            System.out.println(
                "Push Notification Sent to User: "
                + userId + " → " + message);

            // set 15 min cooldown
            redisService
                .setNotificationCooldown(userId);
        }
    }
}