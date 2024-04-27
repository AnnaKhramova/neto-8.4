package ru.akhramova.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.akhramova.controller.PostController;
import ru.akhramova.repository.PostRepository;
import ru.akhramova.repository.PostRepositoryImpl;
import ru.akhramova.service.PostService;

@Configuration
public class JavaConfig {
    @Bean
    public PostController postController(PostService service) {
        return new PostController(service);
    }

    @Bean
    public PostService postService(PostRepository repository) {
        return new PostService(repository);
    }

    @Bean
    public PostRepository postRepository() {
        return new PostRepositoryImpl();
    }
}
