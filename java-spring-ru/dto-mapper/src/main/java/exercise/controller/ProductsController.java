package exercise.controller;

import exercise.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

import exercise.repository.ProductRepository;
import exercise.dto.ProductDTO;
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @Autowired
    private ProductMapper productMapper;


//    GET /products – вывод списка всех товаров
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> index(){
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = productMapper.toDTOS(products);

        return productDTOS;
    }

//    GET /products/{id} – просмотр конкретного товара
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO show(@PathVariable Long id){
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id "+id+" not found"));
        return productMapper.toDTO(product);
    }

//    POST /products – создание нового товара
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody ProductCreateDTO pData){
        var product = productMapper.toEntity(pData);
        productRepository.save(product);

        var pDTO = productMapper.toDTO(product);

        return pDTO;
    }
//    PUT /products/{id} – редактирование товара
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO update(@RequestBody ProductUpdateDTO pData, @PathVariable Long id ){
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id "+id+" not found"));
        productMapper.update(pData,product);
        productRepository.save(product);
        var productDto = productMapper.toDTO(product);
        return productDto;
    }
    // END
}
