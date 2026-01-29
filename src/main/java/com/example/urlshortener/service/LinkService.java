package com.example.urlshortener.service;

import com.example.urlshortener.model.Link;
import com.example.urlshortener.repository.LinkRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RNG = new SecureRandom();

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Transactional
    public Link resolve(String code) {
        Link link = linkRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Unknown code"));

        if (link.getExpiresAt() != null && link.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Expired code");
        }

        long current = (link.getClickCount() == null) ? 0L : link.getClickCount();
        link.setClickCount(current + 1);

        return link;
    }

    @Transactional
    public Link create(String originalUrl) {
        String url = originalUrl == null ? "" : originalUrl.trim();
        if (url.isEmpty()) {
            throw new IllegalArgumentException("URL is required");
        }

        // basic normalisation
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        // retry loop for rare code collisions
        for (int attempt = 0; attempt < 5; attempt++) {
            Link link = new Link();
            link.setCode(generateCode(7));
            link.setOriginalUrl(url);

            // avoid null insert issues (since DB has NOT NULL)
            link.setClickCount(0L);
            link.setCreatedAt(LocalDateTime.now());

            try {
                return linkRepository.save(link);
            } catch (DataIntegrityViolationException e) {
                // likely unique collision on code; try again
            }
        }

        throw new IllegalStateException("Failed to generate a unique code after retries");
    }

    private String generateCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(RNG.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
