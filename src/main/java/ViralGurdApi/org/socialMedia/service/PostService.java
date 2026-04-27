package ViralGurdApi.org.socialMedia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ViralGurdApi.org.socialMedia.entity.Comments;
import ViralGurdApi.org.socialMedia.entity.Post;
import ViralGurdApi.org.socialMedia.repository.CommentRepository;
import ViralGurdApi.org.socialMedia.repository.PostsRepo;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostsRepo postRepository;
    private final CommentRepository commentRepository;
    private final RedisService redisService;
    private final ViralityService viralityService;

    // Create a new post
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    // Add comment to a post
    public Comments addComment(Long postId, Comments comment) {

        // Get post
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));



        //Depth check
        if (comment.getDepthLevel() > 20) {
            throw new RuntimeException("Maximum depth level of 20 reached");
        }

        // AI vs Human logic
        if ("AI".equals(comment.getAuthorType())) {

            // Horizontal cap (max 100 bots)
            Long botCount = redisService.incrementBotCount(postId);

            if (botCount > 100) {
                redisService.decrementBotCount(postId);
                throw new RuntimeException("Bot reply limit of 100 reached");
            }

            //Virality update
            redisService.updateViralityScore(postId, "BOT_REPLY");

        } else {
            //Human comment
            redisService.updateViralityScore(postId, "HUMAN_COMMENT");
        }

        return commentRepository.save(comment);
    }

    // Like a post
    public void likePost(Long postId, Long userId) {

        // check if post exists
        postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        //Update virality
        redisService.updateViralityScore(postId, "HUMAN_LIKE");
    }

    //Get post by ID
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    // Get virality score
    public String getVirality(Long postId) {
        return viralityService.getScore(postId);
    }

    // Get comments for a post
    public List<Comments> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}