package ru.akhramova.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.akhramova.controller.PostController;
import ru.akhramova.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private static final String GET = "GET";
  private static final String POST = "POST";
  private static final String DELETE = "DELETE";

  private static final String PATH = "/api/posts";

  private PostController controller;

  @Override
  public void init() {
    // отдаём список пакетов, в которых нужно искать аннотированные классы
    final var context = new AnnotationConfigApplicationContext("ru.akhramova");

    // получаем по имени бина
    controller = (PostController) context.getBean("postController");

    // получаем по классу бина
    final var service = context.getBean(PostService.class);

    // по умолчанию создаётся лишь один объект на BeanDefinition
    final var isSame = service == context.getBean("postService");
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      // primitive routing
      if (method.equals(GET) && path.equals(PATH)) {
        controller.all(resp);
        return;
      }
      if (method.equals(GET) && path.matches(PATH + "\\d+")) {
        // easy way
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        controller.getById(id, resp);
        return;
      }
      if (method.equals(POST) && path.equals(PATH)) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals(DELETE) && path.matches(PATH + "\\d+")) {
        // easy way
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        controller.removeById(id, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}

