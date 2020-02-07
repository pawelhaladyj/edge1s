package pl.haladyj.pawelhaladyjservice.service;

import org.springframework.stereotype.Service;
import pl.haladyj.pawelhaladyjservice.exception.ProductDuplicateException;
import pl.haladyj.pawelhaladyjservice.exception.ProductNotFoundException;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.model.ProductAdditions;
import pl.haladyj.pawelhaladyjservice.model.ProductType;
import pl.haladyj.pawelhaladyjservice.model.converter.ProductConverter;
import pl.haladyj.pawelhaladyjservice.repository.ProductRepository;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductConverter productConverter;

    public ProductServiceImpl(ProductRepository repository, ProductConverter productConverter) {
        this.repository = repository;
        this.productConverter = productConverter;
    }

    @Override
    public Product findProductById(Long id) {
        Product product = repository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(String.format("id: %d does not exist", id)));
        clickCounter(product);
        calulateDiscountedPrice(product);
        return product;
    }

    @Override
    public Product findProductByName(String name) {
        Product product = repository.findProductByName(name).orElseThrow(() ->
                new ProductNotFoundException(String.format("name: %s does not exist", name)));
        clickCounter(product);
        calulateDiscountedPrice(product);
        return product;
    }

    @Override
    public List<ProductDto> findAllProducts() {

        List<ProductDto> productsDto = new ArrayList<>();
        repository.findAll().forEach(product -> {
            clickCounter(product);
            calulateDiscountedPrice(product);
            productsDto.add(productConverter.toDto(product));
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
        productDto.setProductAdditions(productAdditions);

        Product product = productConverter.toEntity(productDto);
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
        productDto.setProductAdditions(productAdditions);
        product = productConverter.toEntity(productDto);
        product.setId(id);

        return repository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        repository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(String.format("id: %d does not exist", id)));
        repository.deleteById(id);
    }

    public void clickCounter(Product product){
        ProductAdditions productAdditions = product.getProductAdditions();
        productAdditions.setClickCounter(productAdditions.getClickCounter()+1);
        product.setProductAdditions(productAdditions);
        repository.save(product);
    }

    public Product calulateDiscountedPrice(Product product){
        Product prod = product;

        BigDecimal price = product.getPrice();
        ProductType productType = product.getType();
        String discount = productType.getDiscount();
        BigDecimal discountBG = new BigDecimal(discount);
        BigDecimal discountedValue = price
                .multiply((new BigDecimal(100).subtract(discountBG)))
                .divide(new BigDecimal(100));
        prod.setPrice(discountedValue);

        return prod;
    }
}
