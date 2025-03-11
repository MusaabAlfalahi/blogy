package org.example.blog.services;

import org.example.blog.domain.dtos.CreatePostRequest;
import org.example.blog.domain.dtos.UpdatePostRequest;
import org.example.blog.domain.entities.Post;
import org.example.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post getPostById(UUID id);
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDraftPosts(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
    Post UpdatePost(UUID id, UpdatePostRequest updatePostRequest);
    void deletePost(UUID id);
}
