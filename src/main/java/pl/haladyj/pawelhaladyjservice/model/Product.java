package pl.haladyj.pawelhaladyjservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "type")
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column(name = "price_no_discount")
    @NotNull
    private BigDecimal price;

    @Embedded
    private ProductAdditions productAdditions;

    public Product(Long id, @NotNull String name,
                   @NotNull String description,
                   @NotNull ProductType type,
                   @NotNull BigDecimal price,
                   ProductAdditions productAdditions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.productAdditions = productAdditions;
    }

    public Product() {
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
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getDescription(), product.getDescription()) &&
                getType() == product.getType() &&
                Objects.equals(getPrice(), product.getPrice()) &&
                Objects.equals(getProductAdditions(), product.getProductAdditions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getType(), getPrice(), getProductAdditions());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", productAdditions=" + productAdditions +
                '}';
    }
}
