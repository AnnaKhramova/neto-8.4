package ru.akhramova.service;

import org.springframework.stereotype.Service;
import ru.akhramova.exception.NotFoundException;
import ru.akhramova.model.Post;
import ru.akhramova.repository.PostRepository;
import ru.akhramova.repository.PostRepositoryImpl;

import java.util.List;

@Service
public class PostService {
  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public List<Post> all() {
    return repository.all();
  }

  public Post getById(long id) {
    return repository.getById(id).orElseThrow(NotFoundException::new);
  }

  public Post save(Post post) {
    return repository.save(post);
  }

  public void removeById(long id) {
    repository.removeById(id);
  }
}

