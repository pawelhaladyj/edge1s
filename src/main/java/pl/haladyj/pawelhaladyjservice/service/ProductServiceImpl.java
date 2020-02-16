package pl.haladyj.pawelhaladyjservice.service;

import org.springframework.stereotype.Service;
import pl.haladyj.pawelhaladyjservice.exception.ProductNotFoundException;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.model.converter.ProductConverter;
import pl.haladyj.pawelhaladyjservice.payload.ClickCounter;
import pl.haladyj.pawelhaladyjservice.payload.DiscountStrategy;
import pl.haladyj.pawelhaladyjservice.repository.ProductRepository;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductConverter productConverter;
    private final ClickCounter clickCounter;
    private final DiscountStrategy discountStrategy;

    public ProductServiceImpl(ProductRepository repository,
                              ProductConverter productConverter,
                              ClickCounter clickCounter,
                              DiscountStrategy discountStrategy) {
        this.repository = repository;
        this.productConverter = productConverter;
        this.clickCounter = clickCounter;
        this.discountStrategy = discountStrategy;
    }

    @Override
    public ProductDto findProductById(Long id) {
        Product product = repository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(String.format("id: %d does not exist", id)));
        product.setProductAdditions(clickCounter.updateCounter(product));
        repository.save(product);
        ProductDto productDto = productConverter.toDto(product);
        productDto.setDiscountedPrice(discountStrategy.calculateDiscountedPrice(product));
        return productDto;
    }

}
