package ViralGurdApi.org.socialMedia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import ViralGurdApi.org.socialMedia.entity.Ai;
import ViralGurdApi.org.socialMedia.entity.Comments;
import ViralGurdApi.org.socialMedia.entity.Post;
import ViralGurdApi.org.socialMedia.repository.AiRepo;
import ViralGurdApi.org.socialMedia.repository.CommentRepository;
import ViralGurdApi.org.socialMedia.repository.PostsRepo;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BotService {

    private final StringRedisTemplate redisTemplate;
    private final ViralityService viralityService;
    private final NotificationService notificationService;
    private final PostsRepo postRepo;
    private final AiRepo aiRepo;
    private final CommentRepository commentRepo;

    public void botReply(Long postId, Long botId, Long userId) {

        // Horizontal cap
        String botCountKey = "post:" + postId + ":bot_count";
        Long count = redisTemplate.opsForValue().increment(botCountKey, 0);

        if (count != null && count >= 100) {
            throw new RuntimeException("Bot limit reached (100)");
        }

        // Cooldown
        String cooldownKey = "cooldown:bot_" + botId + ":user_" + userId;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(cooldownKey))) {
            throw new RuntimeException("Cooldown active (10 min)");
        }

        // Allow action
        redisTemplate.opsForValue().increment(botCountKey, 1);
        redisTemplate.opsForValue()
                .set(cooldownKey, "1", 10, TimeUnit.MINUTES);
         // SAVE COMMENT
         Ai bot = aiRepo.findById(botId)
                .orElseThrow(() -> new RuntimeException("Bot not found"));

        Comments comment = new Comments();
        comment.setPostId(postId);
        comment.setAuthorId(botId);
        comment.setAuthorType("AI");
        comment.setContent("AI Bot says: Nice post! 🚀");
        comment.setDepthLevel(1);
        comment.setCreatedAt(java.time.LocalDateTime.now());

        commentRepo.save(comment);  

        //virality check
        viralityService.incrementScore(postId, "BOT_REPLY");

        //Notification
        notificationService.handleBotNotification(userId, bot.getName());
    }
}