package ViralGurdApi.org.socialMedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ViralityService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void incrementScore(Long postId, String type) {

        String key = "post:" + postId + ":virality_score";

        int points = 0;

        switch (type) {
            case "BOT_REPLY":
                points = 1;
                break;
            case "HUMAN_LIKE":
                points = 20;
                break;
            case "HUMAN_COMMENT":
                points = 50;
                break;
        }

        redisTemplate.opsForValue().increment(key, points);
    }

    public String getScore(Long postId) {
        String key = "post:" + postId + ":virality_score";
        return redisTemplate.opsForValue().get(key);
    }
}