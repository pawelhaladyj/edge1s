package pl.haladyj.pawelhaladyjservice.service;

import org.springframework.stereotype.Service;
import pl.haladyj.pawelhaladyjservice.exception.ProductDuplicateException;
import pl.haladyj.pawelhaladyjservice.exception.ProductNotFoundException;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.model.ProductAdditions;
import pl.haladyj.pawelhaladyjservice.model.converter.ProductClickConverter;
import pl.haladyj.pawelhaladyjservice.model.converter.ProductConverter;
import pl.haladyj.pawelhaladyjservice.payload.ClickCounter;
import pl.haladyj.pawelhaladyjservice.payload.DiscountStrategy;
import pl.haladyj.pawelhaladyjservice.repository.ProductRepository;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductClicksDto;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductConverter productConverter;
    private final ProductClickConverter productClickConverter;
    private final ClickCounter clickCounter;
    private final DiscountStrategy discountStrategy;

    public ProductServiceImpl(ProductRepository repository,
                              ProductConverter productConverter,
                              ProductClickConverter productClickConverter,
                              ClickCounter clickCounter,
                              DiscountStrategy discountStrategy) {
        this.repository = repository;
        this.productConverter = productConverter;
        this.productClickConverter = productClickConverter;
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

    @Override
    public ProductClicksDto findProductClicks(Long id) {
        Product product = repository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(String.format("id: %d does not exist", id)));

        ProductClicksDto productClicksDto = productClickConverter.toDto(product);
        return productClicksDto;
    }

    @Override
    public List<ProductDto> findAllProducts() {

        List<ProductDto> productsDto = new ArrayList<>();
        repository.findAll().forEach(product -> {
            product.setProductAdditions(clickCounter.updateCounter(product));
            repository.save(product);
            ProductDto productDto = productConverter.toDto(product);
            productDto.setDiscountedPrice(discountStrategy.calculateDiscountedPrice(product));
            productsDto.add(productDto);
        });

        return productsDto;
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        repository.findProductByName(productDto.getName()).ifPresent(product -> {
            throw new ProductDuplicateException(
                    String.format("Product named: %s already exists in database scope", productDto.getName()));
        });

        ProductAdditions productAdditions = new ProductAdditions(0L);


        Product product = productConverter.toEntity(productDto);
        product.setProductAdditions(productAdditions);
        return repository.save(product);
    }

    @Override
    public Product updateProduct(ProductDto productDto, Long id) {
        Product product = repository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(String.format("id: %d does not exist", id)));

        if (repository.existsByName(productDto.getName()) && !product.getName().equals(productDto.getName())) {
            throw new IllegalArgumentException(String.format("Duplicated name: %s", productDto.getName()));
        }

        Long clickCounter = product.getProductAdditions().getClickCounter();
        ProductAdditions productAdditions = new ProductAdditions(clickCounter);

        product = productConverter.toEntity(productDto);
        product.setProductAdditions(productAdditions);
        product.setId(id);

        return repository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        repository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(String.format("id: %d does not exist", id)));
        repository.deleteById(id);
    }

}
