package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping(path = "")
    public List minMaxIndex(@RequestParam(name = "min", required = false) Integer min, @RequestParam(name = "max", required = false) Integer max) {
        List<Product> products;
        if (max == null && min == null) {
            products = productRepository.findAll(Sort.by(Sort.Order.asc("price")));
        } else if (min == null) {
            products = productRepository.findByPriceLessThan(max, Sort.by(Sort.Order.asc("price")));
        } else if (max == null) {
            products = productRepository.findByPriceGreaterThan(min,Sort.by(Sort.Order.asc("price")));
        } else {
            products = productRepository.findByPriceGreaterThanAndPriceLessThan(min, max,Sort.by(Sort.Order.asc("price")));
        }


        return products;
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
