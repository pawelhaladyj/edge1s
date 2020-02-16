package pl.haladyj.pawelhaladyjservice.service;

import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> findProductById(Long id);

}
