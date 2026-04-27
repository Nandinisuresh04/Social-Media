package ViralGurdApi.org.socialMedia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ViralGurdApi.org.socialMedia.entity.User;
import ViralGurdApi.org.socialMedia.repository.UserRepo;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepo userRepository;

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody User user) {
        User saved = userRepository.save(user);
        return ResponseEntity.ok(saved);
    }

    // Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(
            @PathVariable Long id) {
        return userRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity
                .notFound().build());
    }
}