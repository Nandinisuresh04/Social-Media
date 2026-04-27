package ViralGurdApi.org.socialMedia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, 
        String> redisTemplate;

    
    
    // HORIZONTAL CAP (ai count)
   	public Long incrementBotCount(Long postId) {
   		
   	        String key = "post:" + postId + 
   	            ":bot_count";
   	        return redisTemplate.opsForValue()
   	            .increment(key);
   	    }

   	

   	public void decrementBotCount(Long postId) {
   			  String key = "post:" + postId + 
   			            ":bot_count";
   			        redisTemplate.opsForValue()
   			            .decrement(key);
   		}
   	
   	
   	  //VIRALITY SCORE
   	public void updateViralityScore(Long postId, String interactionType) {

               String key = "post:" + postId + 
                   ":virality_score";

               switch(interactionType) {
                   case "BOT_REPLY" ->
                       redisTemplate.opsForValue()
                           .increment(key, 1);
                   case "HUMAN_LIKE" ->
                       redisTemplate.opsForValue()
                           .increment(key, 20);
                   case "HUMAN_COMMENT" ->
                       redisTemplate.opsForValue()
                           .increment(key, 50);
               }
   		
   	}
   		

      
    public Long getViralityScore(Long postId) {
        String key = "post:" + postId + 
            ":virality_score";
        String value = redisTemplate
            .opsForValue().get(key);
        return value != null ? 
            Long.parseLong(value) : 0L;
    }

    

    public Long getBotCount(Long postId) {
        String key = "post:" + postId + 
            ":bot_count";
        String value = redisTemplate
            .opsForValue().get(key);
        return value != null ? 
            Long.parseLong(value) : 0L;
    }

   //COOLDOWN CAP (ai vs human)
    public boolean isCooldownActive(
            Long botId, Long humanId) {
        String key = "cooldown:bot_" + 
            botId + ":human_" + humanId;
        return Boolean.TRUE.equals(
            redisTemplate.hasKey(key));
    }

    public void setCooldown(
            Long botId, Long humanId) {
        String key = "cooldown:bot_" + 
            botId + ":human_" + humanId;
        redisTemplate.opsForValue().set(
            key,
            "true",
            10,
            TimeUnit.MINUTES
        );
    }

    //NOTIFICATION THROTTLER
    public boolean isNotificationCooldownActive(
            Long userId) {
        String key = "notif_cooldown:user_" + userId;
        return Boolean.TRUE.equals(
            redisTemplate.hasKey(key));
    }

    public void setNotificationCooldown(
            Long userId) {
        String key = "notif_cooldown:user_" + userId;
        redisTemplate.opsForValue().set(
            key,
            "true",
            15,
            TimeUnit.MINUTES
        );
    }

    public void addPendingNotification(
            Long userId, String message) {
        String key = "user:" + userId + 
            ":pending_notifs";
        redisTemplate.opsForList()
            .leftPush(key, message);
    }

    public java.util.List<String> 
            getPendingNotifications(Long userId) {
        String key = "user:" + userId + 
            ":pending_notifs";
        return redisTemplate.opsForList()
            .range(key, 0, -1);
    }

    public void clearPendingNotifications(
            Long userId) {
        String key = "user:" + userId + 
            ":pending_notifs";
        redisTemplate.delete(key);
    }

    public java.util.Set<String> 
            getAllPendingNotifKeys() {
        return redisTemplate.keys(
            "user:*:pending_notifs");
    }
}