package pl.haladyj.pawelhaladyjservice.service;

import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import java.util.List;

public interface ProductService {

    Product findProductById(Long id);
    Product findProductByName(String name);
    List<ProductDto> findAllProducts();
    Product createProduct (ProductDto productDto);
    Product updateProduct (ProductDto productDto, Long id);
    void deleteProduct (Long id);
}
