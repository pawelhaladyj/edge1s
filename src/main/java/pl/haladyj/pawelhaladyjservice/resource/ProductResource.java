package pl.haladyj.pawelhaladyjservice.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.service.ProductServiceImpl;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductClicksDto;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductUpdateDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductResource {

    private final ProductServiceImpl service;

    public ProductResource(ProductServiceImpl service) {
        this.service = service;
    }


/*    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        ProductDto productDto = service.findProductById(id);
        return ResponseEntity.ok().body(productDto);
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = service.findAllProducts();
        return ResponseEntity.ok().body(productDtos);
    }

    @GetMapping("/product/{id}/clicks")
    public ResponseEntity<?> getProductClicks(@PathVariable Long id) {
        ProductClicksDto productClicksDto = service.findProductClicks(id);
        return ResponseEntity.ok().body(productClicksDto);
    }*/

    @GetMapping({"/product", "/product/{id}"})
    public ResponseEntity<?> getProducts(
            @PathVariable(required = false) Long id,
            @RequestParam(value = "showClicks", required = false) Boolean isClickCounterVisible) {
        if (id == null) {
            List<ProductDto> productDtos = service.findAllProducts();
            return ResponseEntity.ok().body(productDtos);
        } else if (id instanceof Long) {
            if (isClickCounterVisible == null) {
                ProductDto productDto = service.findProductById(id);
                return ResponseEntity.ok().body(productDto);
            } else if (isClickCounterVisible) {
                ProductClicksDto productClicksDto = service.findProductClicks(id);
                return ResponseEntity.ok().body(productClicksDto);
            } else {
                ProductDto productDto = service.findProductById(id);
                return ResponseEntity.ok().body(productDto);
            }
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createUser(@Valid @RequestBody ProductDto productDto) {
        Product product = service.createProduct(productDto);
        return ResponseEntity.ok().body(product);
    }

    @PutMapping("/product")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductUpdateDto productUpdateDto) {
        Product productRepository = service.updateProduct(productUpdateDto);
        return ResponseEntity.ok().body(productRepository);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
