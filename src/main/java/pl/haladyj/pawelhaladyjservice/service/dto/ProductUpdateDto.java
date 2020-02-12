package pl.haladyj.pawelhaladyjservice.service.dto;

import pl.haladyj.pawelhaladyjservice.model.ProductType;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductUpdateDto {

    private Long id;
    private String name;
    private String description;
    private ProductType type;
    private BigDecimal initialPrice;

    public ProductUpdateDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductUpdateDto)) return false;
        ProductUpdateDto that = (ProductUpdateDto) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                getType() == that.getType() &&
                Objects.equals(getInitialPrice(), that.getInitialPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getType(), getInitialPrice());
    }

    @Override
    public String toString() {
        return "ProductUpdateDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", initialPrice=" + initialPrice +
                '}';
    }
}
