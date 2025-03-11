package org.example.blog.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.blog.domain.dtos.*;
import org.example.blog.domain.entities.Post;
import org.example.blog.domain.entities.User;
import org.example.blog.mappers.PostMapper;
import org.example.blog.services.PostService;
import org.example.blog.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId) {
        List<Post> posts = postService
                .getAllPosts(categoryId, tagId);
        List<PostDto> postDtos = posts
                .stream().map(postMapper::toDto)
                .toList();
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PostDto>> getDraftPosts(@RequestAttribute UUID userId) {
        User loggedInUser = userService.getUserById(userId);
        List<Post> posts = postService.getDraftPosts(loggedInUser);
        List<PostDto> postDtos = posts.stream().map(postMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestAttribute UUID userId
            ) {
        User loggedInUser = userService.getUserById(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
        Post post = postService.createPost(loggedInUser, createPostRequest);
        PostDto createdPostDto = postMapper.toDto(post);
        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto
    ) {
        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post updatePost = postService.UpdatePost(id, updatePostRequest);
        PostDto updatedPostDto = postMapper.toDto(updatePost);
        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(
            @PathVariable UUID id
    ) {
        Post post =  postService.getPostById(id);
        PostDto postDto = postMapper.toDto(post);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
