package pl.haladyj.pawelhaladyjservice.service;

import org.springframework.stereotype.Service;
import pl.haladyj.pawelhaladyjservice.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

}
