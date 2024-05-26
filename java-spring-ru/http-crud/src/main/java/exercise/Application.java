package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
//    GET /posts — список всех постов
    @GetMapping("/posts")
    public List<Post> showall(@RequestParam(defaultValue = "10") Integer limit) {
        return posts.stream().limit(limit).toList();
    }

//    GET /posts/{id} — просмотр конкретного поста
@GetMapping("/posts/{id}") // Вывод страницы
public Optional<Post> show(@PathVariable String id) {
    var post = posts.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst();
    return post;
}

//    POST /posts – создание нового поста
@PostMapping("/posts") // Создание страницы
public Post create(@RequestBody Post post) {
    posts.add(post);
    return post;
}

//    PUT /posts/{id} – обновление поста
@PutMapping("/posts/{id}") // Обновление страницы
public Post update(@PathVariable String id, @RequestBody Post data) {
    var maybePost = posts.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst();
    if (maybePost.isPresent()) {
        var post = maybePost.get();
        post.setId(data.getId());
        post.setTitle(data.getId());
        post.setBody(data.getBody());
    }
    return data;
}

//    DELETE /posts/{id} – удаление поста
@DeleteMapping("/posts/{id}")
public void delete(@PathVariable String id) {
    posts.removeIf(p -> p.getId().equals(id));
}
    // END
}
