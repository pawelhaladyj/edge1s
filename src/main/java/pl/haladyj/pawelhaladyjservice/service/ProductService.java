package pl.haladyj.pawelhaladyjservice.service;

import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductClicksDto;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto findProductById(Long id);
    ProductClicksDto findProductClicks(Long id);
    List<ProductDto> findAllProducts();
    Product createProduct (ProductDto productDto);
    Product updateProduct (ProductDto productDto, Long id);
    void deleteProduct (Long id);
}
