package pl.haladyj.pawelhaladyjservice.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.repository.ProductRepository;
import pl.haladyj.pawelhaladyjservice.service.ProductServiceImpl;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductResource {

    private final ProductRepository repository;
    private final ProductServiceImpl service;

    public ProductResource(ProductRepository repository, ProductServiceImpl service) {
        this.repository = repository;
        this.service = service;
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        ProductDto productDto = service.findProductById(id);
        return ResponseEntity.ok().body(productDto);
    }

    @GetMapping("/product/{name}/names")
    public ResponseEntity<?> getProductByName(@PathVariable String name) {
        ProductDto productDto = service.findProductByName(name);
        return ResponseEntity.ok().body(productDto);
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = service.findAllProducts();
        return ResponseEntity.ok().body(productDtos);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createUser(@Valid @RequestBody ProductDto productDto) {
        Product product = service.createProduct(productDto);
        return ResponseEntity.ok().body(product);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable Long id) {
        Product product = service.updateProduct(productDto, id);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
