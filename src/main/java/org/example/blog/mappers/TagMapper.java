package org.example.blog.mappers;

import org.example.blog.domain.entities.PostStatus;
import org.example.blog.domain.dtos.TagDto;
import org.example.blog.domain.entities.Post;
import org.example.blog.domain.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    @Mapping(target = "postCount", source = "posts", qualifiedByName =
            "calculatePostCount")
    TagDto toTagDto(Tag tag);

    @Named("calculatePostCount")
    default Integer calculatePostCount(Set<Post> posts) {
        if (posts == null || posts.isEmpty()) {
            return 0;
        }
        return (int) posts.stream()
                    .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
                    .count();
    }
}
