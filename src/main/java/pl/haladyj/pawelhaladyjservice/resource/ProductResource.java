package pl.haladyj.pawelhaladyjservice.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.haladyj.pawelhaladyjservice.service.ProductServiceImpl;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductResource {

    private final ProductServiceImpl service;

    public ProductResource(ProductServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Optional<ProductDto> productDto = service.findProductById(id);
        return productDto
                .map(response -> (ResponseEntity.ok().body(response)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
