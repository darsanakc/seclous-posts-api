package com.seclous.posts_api.model;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private String id;

    @NotBlank
    private String author;

    @NotNull
    private Instant date;

    @NotBlank
    private String content;
}
