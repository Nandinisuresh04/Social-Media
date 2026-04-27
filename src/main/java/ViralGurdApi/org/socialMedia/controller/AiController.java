package ViralGurdApi.org.socialMedia.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ViralGurdApi.org.socialMedia.entity.Ai;
import ViralGurdApi.org.socialMedia.repository.AiRepo;
import ViralGurdApi.org.socialMedia.service.BotService;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiRepo aiRepository;
    private final BotService botService;

    // Create a new AI bot
    @PostMapping
    public ResponseEntity<Ai> createAI(
            @RequestBody Ai ai) {
        Ai saved = aiRepository.save(ai);
        return ResponseEntity.ok(saved);
    }

    // Get AI by id
    @GetMapping("/{id}")
    public ResponseEntity<Ai> getAI(
            @PathVariable Long id) {
        return aiRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity
                .notFound().build());
    }
    //add comment to the post
    @PostMapping("/bot/{botId}/comment/{postId}")
    public ResponseEntity<?> botReply(
            @PathVariable Long botId,
            @PathVariable Long postId,
            @RequestParam Long userId) {

        try {
            botService.botReply(postId, botId, userId);
            return ResponseEntity.ok("Bot replied successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(429).body(e.getMessage());
        }
    }
   
}