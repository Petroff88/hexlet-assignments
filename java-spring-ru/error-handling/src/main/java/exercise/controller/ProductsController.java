package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(path = "")
    public List<Product> index() {
        return productRepository.findAll();
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // BEGIN
//    GET /products/{id} — просмотр конкретного товара
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product showProduct(@PathVariable Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        return product;
    }

    //    PUT /products/{id} — обновление данных товара
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product changeProduct(@PathVariable Long id, @RequestBody Product newProduct) {
        var maybeProduct = productRepository.findById(id);
        if (maybeProduct.isPresent()) {
            var product = maybeProduct.get();
            product.setTitle(newProduct.getTitle());
            product.setPrice(newProduct.getPrice());

            return product;
        } else {
            throw new ResourceNotFoundException("Product with id " + id + " not found");
        }
    }
    // END

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        productRepository.deleteById(id);
    }
}
