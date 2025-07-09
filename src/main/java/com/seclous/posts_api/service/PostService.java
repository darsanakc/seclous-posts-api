package com.seclous.posts_api.service;
import com.seclous.posts_api.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PostService {
    private final Map<String, Post> store = new ConcurrentHashMap<>();

    public List<Post> list() {
        return new ArrayList<>(store.values());
    }

    public Post get(String id) {
        return Optional.ofNullable(store.get(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Post create(Post post) {
        String id = UUID.randomUUID().toString();
        post.setId(id);
        store.put(id, post);
        return post;
    }

    public Post update(String id, Post post) {
        if (!store.containsKey(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        post.setId(id);
        store.put(id, post);
        return post;
    }

    public void delete(String id) {
        if (store.remove(id) == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
