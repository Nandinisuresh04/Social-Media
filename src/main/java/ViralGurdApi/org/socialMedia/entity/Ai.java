package ViralGurdApi.org.socialMedia.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ai")
public class Ai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "persona_description", columnDefinition = "TEXT")
    private String personaDescription;
}
