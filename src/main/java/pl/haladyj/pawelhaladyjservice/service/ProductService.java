package pl.haladyj.pawelhaladyjservice.service;

import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import java.util.List;

public interface ProductService {

    Product getProductById(Long id);
    Product getProductByName(String name);
    List<ProductDto> getAllProducts();
    Product createProduct (ProductDto productDto);
    Product updateProduct (ProductDto productDto, Long id);
    void deleteProduct (Long id);
}
