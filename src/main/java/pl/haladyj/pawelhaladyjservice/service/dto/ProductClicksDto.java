package pl.haladyj.pawelhaladyjservice.service.dto;

import pl.haladyj.pawelhaladyjservice.model.ProductAdditions;

import java.util.Objects;

public class ProductClicksDto {

    private Long id;
    private ProductAdditions productAdditions;

    public ProductClicksDto() {
    }

    public ProductClicksDto(Long id, ProductAdditions productAdditions) {
        this.id = id;
        this.productAdditions = productAdditions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
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
        if (!(o instanceof ProductClicksDto)) return false;
        ProductClicksDto that = (ProductClicksDto) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getProductAdditions(), that.getProductAdditions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductAdditions());
    }

    @Override
    public String toString() {
        return "ProductCliksDto{" +
                "id=" + id +
                ", productAdditions=" + productAdditions +
                '}';
    }
}
