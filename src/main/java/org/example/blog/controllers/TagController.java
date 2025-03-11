package org.example.blog.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.blog.domain.dtos.CreateTagRequests;
import org.example.blog.domain.dtos.TagDto;
import org.example.blog.mappers.TagMapper;
import org.example.blog.services.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
    public final TagMapper tagMapper;
    public final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        List<TagDto> tags = tagService.getAllTags()
                .stream()
                .map(tagMapper::toTagDto)
                .toList();
        return ResponseEntity.ok(tags);
    }

    @PostMapping
    public ResponseEntity<List<TagDto>> createTags(@Valid @RequestBody CreateTagRequests createTagRequests) {
        List<TagDto> savedTags =
                tagService.createTags(createTagRequests.getNames())
                        .stream()
                        .map(tagMapper::toTagDto)
                        .toList();
        return new ResponseEntity<>(savedTags, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
