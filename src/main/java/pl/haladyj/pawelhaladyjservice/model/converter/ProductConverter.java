package pl.haladyj.pawelhaladyjservice.model.converter;

import org.springframework.stereotype.Component;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

@Component
public class ProductConverter implements Converter<Product, ProductDto> {

    @Override
    public Product toEntity(ProductDto productDto) {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setType(productDto.getType());
        product.setProductAdditions(productDto.getProductAdditions());

        return product;
    }

    @Override
    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();

        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setType(product.getType());
        productDto.setProductAdditions(product.getProductAdditions());

        return productDto;
    }
}
