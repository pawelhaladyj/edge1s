package pl.haladyj.pawelhaladyjservice.service.dto;

import pl.haladyj.pawelhaladyjservice.model.ProductAdditions;
import pl.haladyj.pawelhaladyjservice.model.ProductType;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductDto {

    private String name;
    private String description;
    private ProductType type;
    private BigDecimal price;
    private ProductAdditions productAdditions;

    public ProductDto() {
    }

    public ProductDto(String name, String description, ProductType type, BigDecimal price, ProductAdditions productAdditions) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.productAdditions = productAdditions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductAdditions getProductAdditions() {
        return productAdditions;
    }

    public void setProductAdditions(ProductAdditions productAdditions) {
        this.productAdditions = productAdditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDto)) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                getType() == that.getType() &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getProductAdditions(), that.getProductAdditions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getType(), getPrice(), getProductAdditions());
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", productAdditions=" + productAdditions +
                '}';
    }
}
