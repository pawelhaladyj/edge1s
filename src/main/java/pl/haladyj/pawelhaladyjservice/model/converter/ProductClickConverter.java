package pl.haladyj.pawelhaladyjservice.model.converter;

import org.springframework.stereotype.Component;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductClicksDto;

@Component
public class ProductClickConverter implements Converter<Product, ProductClicksDto>{

    @Override
    public Product toEntity(ProductClicksDto productClicksDto) {
        Product product = new Product();

        product.setId(productClicksDto.getId());
        product.setProductAdditions(productClicksDto.getProductAdditions());

        return null;
    }

    @Override
    public ProductClicksDto toDto(Product product) {
        ProductClicksDto productClicksDto = new ProductClicksDto();

        productClicksDto.setId(product.getId());
        productClicksDto.setProductAdditions(product.getProductAdditions());

        return productClicksDto;
    }
}
