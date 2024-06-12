package exercise.controller;

import java.util.List;

import exercise.dto.CategoryDTO;
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceBadRequestException;
import exercise.mapper.ProductMapper;
import exercise.model.Category;
import exercise.model.Product;
import exercise.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    // BEGIN
//    GET /products – просмотр списка всех товаров
    @GetMapping(path = "")
    public List<ProductDTO> index() {
        var products = productRepository.findAll();
        return products.stream()
                .map(productMapper::map)
                .toList();
    }


    @GetMapping(path = "/{id}")
    public ProductDTO show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        var productDTO = productMapper.map(product);
        return productDTO;
    }

    //    POST /products – добавление нового товара. При указании id несуществующей категории должен вернуться ответ с кодом 400 Bad request
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody ProductCreateDTO data) {
        var product = productMapper.map(data);
        var cat = getCategory(data.getCategoryId());

        product.setCategory(cat);
        productRepository.save(product);

        return (productMapper.map(product));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO update(@RequestBody @Valid ProductUpdateDTO data, @PathVariable Long id) {
        var product = getProduct(id);
        productMapper.update(data, product);

        productMapper.update(data, product);
        product.setCategory(getCategory(data.getCategoryId().get()));
        productRepository.save(product);

        return productMapper.map(product);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        productRepository.deleteById(id);
    }


    private Category getCategory(Long categoryID) {
        return categoryRepository.findById(categoryID).orElseThrow(
                () -> new ResourceBadRequestException("Category not found")
        );
    }

    private Product getProduct(Long productID) {
        return productRepository.findById(productID).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Product id %d is not found", productID))
        );
    }
    // END
}
