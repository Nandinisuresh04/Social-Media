package ViralGurdApi.org.socialMedia.controller;


import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ViralGurdApi.org.socialMedia.entity.Comments;
import ViralGurdApi.org.socialMedia.entity.Post;
import ViralGurdApi.org.socialMedia.service.PostService;
import ViralGurdApi.org.socialMedia.service.ViralityService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ViralityService viralityService;

    // Create a new post
    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(
        @RequestBody Post post) {
        Post created = postService.createPost(post);
        return ResponseEntity.ok(created);
    }
    
    //get post
    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    // Add comment to a post
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<?> addComment(
        @PathVariable Long postId,
        @RequestBody Comments comment) {
        try {
            Comments created = postService
                .addComment(postId, comment);
            return ResponseEntity.ok(created);
        } catch (RuntimeException e) {
            return ResponseEntity
                .status(429)
                .body(e.getMessage());
        }
    }

    // Like a post
    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<?> likePost(
        @PathVariable Long postId,
        @RequestParam Long userId) {
    	viralityService.incrementScore(postId, "HUMAN_LIKE");
        try {
            postService.likePost(postId, userId);
            return ResponseEntity.ok(
                "Post liked successfully");
        } catch (RuntimeException e) {
            return ResponseEntity
                .badRequest()
                .body(e.getMessage());
        }
        
    }
    
    //virality check
    @GetMapping("/posts/{postId}/virality")
    public String getVirality(@PathVariable Long postId) {
        return postService.getVirality(postId);
    }
    
    //ai comment
    @GetMapping("/posts/{postId}/comments")
    public List<Comments> getComments(@PathVariable Long postId) {
        return postService.getCommentsByPost(postId);
    }
    
}