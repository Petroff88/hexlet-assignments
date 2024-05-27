package exercise.controller.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {
    private List<Post> posts = Data.getPosts();


    @GetMapping("/api/users/{id}/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> showPostsByUserId(@PathVariable int userId) {
        return posts.stream()
                .filter(p -> p.getUserId() == userId)
                .toList();

    }

    @PostMapping("/api/users/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post, @PathVariable int userId) {
        post.setUserId(userId);
        posts.add(post);
        return post;
    }
}
// END
