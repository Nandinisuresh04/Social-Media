package ViralGurdApi.org.socialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ViralGurdApi.org.socialMedia.entity.Post;

public interface PostsRepo extends JpaRepository<Post, Long> {

}
