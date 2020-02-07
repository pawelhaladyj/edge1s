package pl.haladyj.pawelhaladyjservice.service;

import org.springframework.stereotype.Service;
import pl.haladyj.pawelhaladyjservice.exception.ProductDuplicateException;
import pl.haladyj.pawelhaladyjservice.exception.ProductNotFoundException;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.model.ProductAdditions;
import pl.haladyj.pawelhaladyjservice.model.converter.ProductConverter;
import pl.haladyj.pawelhaladyjservice.repository.ProductRepository;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

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
    public Product getProductById(Long id) {
        Product product = repository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(String.format("id: %d does not exist", id)));
        return product;
    }

    @Override
    public Product getProductByName(String name) {
        Product product = repository.findProductByName(name).orElseThrow(() ->
                new ProductNotFoundException(String.format("name: %s does not exist", name)));
        return product;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductDto> productsDto = new ArrayList<>();
        repository.findAll().forEach(product -> productsDto.add(productConverter.toDto(product)));
        return productsDto;
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        repository.findProductByName(productDto.getName()).ifPresent(item -> {
            throw new ProductDuplicateException(String.format("Product named: %s already exists in database scope"));
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

        if(repository.existsByName(productDto.getName())&&!product.getName().equals(productDto.getName())){
            throw new IllegalArgumentException(String.format("Duplicated name: %s", productDto.getName()));
        }

        Long clickCounter = product.getProductAdditions().getClickCounter();
        ProductAdditions productAdditions = new ProductAdditions(clickCounter);
        productDto.setProductAdditions(productAdditions);

        return repository.save(productConverter.toEntity(productDto));
    }

    @Override
    public void deleteProduct(Long id) {
        repository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(String.format("id: %d does not exist", id)));
        repository.deleteById(id);
    }


}
