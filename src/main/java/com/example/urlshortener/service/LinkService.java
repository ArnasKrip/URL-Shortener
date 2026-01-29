package com.example.urlshortener.service;

import com.example.urlshortener.model.Link;
import com.example.urlshortener.repository.LinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Transactional
    public Link resolve(String code) {
        Link link = linkRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Unknown code"));

        // expiry check (if set)
        if (link.getExpiresAt() != null && link.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Expired code");
        }

        // increment click count
        long current = (link.getClickCount() == null) ? 0L : link.getClickCount();
        link.setClickCount(current + 1);

        return link; // JPA will flush update on commit
    }
}
