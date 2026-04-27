package ViralGurdApi.org.socialMedia.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import ViralGurdApi.org.socialMedia.entity.User;

public interface UserRepo  extends JpaRepository<User, Long>{

}
