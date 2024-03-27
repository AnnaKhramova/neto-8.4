package ru.akhramova.repository;

import org.springframework.stereotype.Repository;
import ru.akhramova.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryImpl implements PostRepository {
  static AtomicLong maxId = new AtomicLong(0);

  private Map<Long, Post> posts = new ConcurrentHashMap<>();

  public List<Post> all() {
    return posts.values().stream().toList();
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
