package ru.akhramova.repository;

import ru.akhramova.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PostRepository {

  private Long maxId = 0L;

  private Map<Long, Post> posts = new ConcurrentHashMap<>();

  public List<Post> all() {
    return posts.values().stream().toList();
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0L) {
      maxId++;
      post.setId(maxId);
    } else if (post.getId() > maxId) {
      return null;
    }
    posts.put(post.getId(), post);
    return post;
  }

  public void removeById(long id) {
    posts.remove(id);
  }
}
