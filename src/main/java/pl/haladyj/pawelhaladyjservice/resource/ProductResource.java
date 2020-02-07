package pl.haladyj.pawelhaladyjservice.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.repository.ProductRepository;
import pl.haladyj.pawelhaladyjservice.service.ProductServiceImpl;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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
        Product product = service.getProductById(id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/product/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable String name) {
        Product product = service.getProductByName(name);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = service.getAllProducts();
        return ResponseEntity.ok().body(productDtos);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createUser(@Valid @RequestBody ProductDto productDto) throws URISyntaxException {
        Product product = service.createProduct(productDto);
        return ResponseEntity.created(new URI("/api/product/" + product.getName())).body(product);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable Long id) {
        Product product = service.updateProduct(productDto, id);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
