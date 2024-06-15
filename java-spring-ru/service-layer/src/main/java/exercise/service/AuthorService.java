package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    AuthorMapper authorMapper;


    // BEGIN
    public List<AuthorDTO> getAll() {
        var authors = authorRepository.findAll();
        var result = authors.stream()
                .map(authorMapper::map)
                .toList();

        return result;
    }


    public AuthorDTO create(AuthorCreateDTO data) {
        var auhor = authorMapper.map(data);
        authorRepository.save(auhor);

        var authorDto = authorMapper.map(auhor);
        return authorDto;
    }

    public AuthorDTO findByID(Long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        var authorDto = authorMapper.map(author);
        return authorDto;
    }

    public AuthorDTO update(AuthorUpdateDTO data, Long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        authorMapper.update(data, author);
        authorRepository.save(author);
        var authorDTO = authorMapper.map(author);
        return authorDTO;
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
    // END
}
