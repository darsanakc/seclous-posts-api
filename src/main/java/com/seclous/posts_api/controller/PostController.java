package com.seclous.posts_api.controller;
import com.seclous.posts_api.model.Post;
import com.seclous.posts_api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @GetMapping
    public List<Post> list() {
        return service.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@Valid @RequestBody Post post) {
        return service.create(post);
    }

    @GetMapping("/{id}")
    public Post get(@PathVariable String id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable String id, @Valid @RequestBody Post post) {
        return service.update(id, post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
