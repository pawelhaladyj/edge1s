package pl.haladyj.pawelhaladyjservice.model.converter;

import org.springframework.stereotype.Component;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductUpdateDto;

@Component
public class ProductUpdateConverter implements Converter<Product, ProductUpdateDto> {
    @Override
    public Product toEntity(ProductUpdateDto productUpdateDto) {
        Product product = new Product();

        product.setId(productUpdateDto.getId());
        product.setName(productUpdateDto.getName());
        product.setDescription(productUpdateDto.getDescription());
        product.setPrice(productUpdateDto.getInitialPrice());
        product.setType(productUpdateDto.getType());

        return product;
    }

    @Override
    public ProductUpdateDto toDto(Product product) {

        ProductUpdateDto productUpdateDto = new ProductUpdateDto();

        productUpdateDto.setId(product.getId());
        productUpdateDto.setName(product.getName());
        productUpdateDto.setDescription(product.getDescription());
        productUpdateDto.setInitialPrice(product.getPrice());
        productUpdateDto.setType(product.getType());

        return productUpdateDto;
    }
}
