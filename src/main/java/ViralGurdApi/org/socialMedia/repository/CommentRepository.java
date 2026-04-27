package ViralGurdApi.org.socialMedia.repository;

import org.springframework.data.jpa.repository
    .JpaRepository;
import org.springframework.stereotype.Repository;
import ViralGurdApi.org.socialMedia.entity.Comments;
import java.util.List;

@Repository
public interface CommentRepository
    extends JpaRepository<Comments, Long> {
    
    List<Comments> findByPostId(Long postId);
}