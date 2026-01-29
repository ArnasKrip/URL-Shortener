package com.example.urlshortener.controller;

import com.example.urlshortener.dto.CreateLinkRequest;
import com.example.urlshortener.model.Link;
import com.example.urlshortener.service.LinkService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/links")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody CreateLinkRequest req) {
        Link link = linkService.create(req.getUrl());

        // Return both the code and the full short URL
        String shortUrl = "http://localhost:8080/" + link.getCode();

        return ResponseEntity
                .created(URI.create("/" + link.getCode()))
                .body(Map.of(
                        "code", link.getCode(),
                        "shortUrl", shortUrl,
                        "originalUrl", link.getOriginalUrl()
                ));
    }
}
