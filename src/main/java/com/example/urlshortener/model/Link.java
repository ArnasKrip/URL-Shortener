package com.example.urlshortener.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "links")

public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 16)
    private String code;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "click_count", nullable = false)
    private long clickCount = 0;
    
    // JPA requires a no-arg constructor
    public Link() {}

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getOriginalUrl() { return originalUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public Long getClickCount() { return clickCount; }

    public void setCode(String code) { this.code = code; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public void setClickCount(Long clickCount) { this.clickCount = clickCount; }
}
