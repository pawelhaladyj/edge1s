package pl.haladyj.pawelhaladyjservice.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.haladyj.pawelhaladyjservice.repository.ProductRepository;
import pl.haladyj.pawelhaladyjservice.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductResource {

    private final ProductRepository repository;
    private final ProductService service;

    public ProductResource(ProductRepository repository, ProductService service) {
        this.repository = repository;
        this.service = service;
    }
}
