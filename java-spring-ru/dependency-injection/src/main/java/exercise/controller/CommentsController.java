package exercise.controller;

import exercise.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping(path = "/comments")
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

//    GET /comments — список всех комментариев
@GetMapping(path = "")
@ResponseStatus(HttpStatus.OK)
public List<Comment> index() {
    return commentRepository.findAll();
}

//    GET /comments/{id} – просмотр конкретного комментария
@GetMapping(path = "/{id}")
@ResponseStatus(HttpStatus.OK)
public Comment show(@PathVariable long id) {
    var comment = commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));

    return comment;
}

//    POST /comments – создание нового комментария. При успешном создании возвращается статус 201
@PostMapping(path = "")
@ResponseStatus(HttpStatus.CREATED)
public Comment create(@RequestBody Comment commentData) {
    var comment = new Comment();

    comment.setBody(commentData.getBody());
    comment.setPostId(commentData.getPostId());


    commentRepository.save(comment);

    return comment;
}

//    PUT /comments/{id} – обновление комментария
@PutMapping(path = "/{id}")
@ResponseStatus(HttpStatus.OK)
public Comment update(@PathVariable long id, @RequestBody Comment commentData) {
    var comment = commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));

    comment.setBody(commentData.getBody());
    commentRepository.save(comment);

    return comment;
}

//    DELETE /comments/{id} – удаление комментария
@DeleteMapping(path = "{id}")
@ResponseStatus(HttpStatus.OK)
public void delete(@PathVariable long id) {
    commentRepository.deleteById(id);
}
}

// END
