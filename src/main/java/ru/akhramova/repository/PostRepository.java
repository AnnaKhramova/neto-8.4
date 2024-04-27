package ru.akhramova.repository;

import ru.akhramova.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class PostRepository {
  static AtomicLong maxId = new AtomicLong(0);

  private Map<Long, Post> posts = new ConcurrentHashMap<>();

  public List<Post> all() {
    return new ArrayList<>(posts.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0L) {
      post.setId(maxId.incrementAndGet());
    } else if (post.getId() > maxId.longValue()) {
      return null;
    }
    posts.put(post.getId(), post);
    return post;
  }

  public void removeById(long id) {
    posts.remove(id);
  }
}
