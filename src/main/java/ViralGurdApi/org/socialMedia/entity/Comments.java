package ViralGurdApi.org.socialMedia.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import ViralGurdApi.org.socialMedia.entity.Post.AuthorType;

@Data
@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "author_type", nullable = false)

    private String authorType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "depth_level", nullable = false)
    private int depthLevel;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

	public void setAuthorType(String string) {
		this.authorType = string;
		
	
		
	}
}
