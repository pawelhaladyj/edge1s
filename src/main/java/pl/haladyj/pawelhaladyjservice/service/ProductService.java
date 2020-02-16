package pl.haladyj.pawelhaladyjservice.service;

import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto findProductById(Long id);

}
