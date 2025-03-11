package org.example.blog.domain.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTagRequests {
    @NotEmpty(message = "At least one tag name is required")
    @Size(max = 10, message = "Maximum {tags} tags allowed")
    private Set<@Size(min = 2, max = 50, message = "Category must be between {min} and {max} characters")
                @Pattern(regexp = "^[\\w\\s-]+$", message = "Category name can only contain letters, numbers, spaces and hyphens")
                String> names;
}
