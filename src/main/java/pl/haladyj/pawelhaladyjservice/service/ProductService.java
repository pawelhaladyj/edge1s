package pl.haladyj.pawelhaladyjservice.service;

import pl.haladyj.pawelhaladyjservice.service.dto.ProductCounterDto;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> findProductById(Long id);
    Optional<ProductCounterDto> findProductCountsById(Long id);

}
