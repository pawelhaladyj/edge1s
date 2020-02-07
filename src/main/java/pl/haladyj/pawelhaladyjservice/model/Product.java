package pl.haladyj.pawelhaladyjservice.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="type")
    private ProductType type;

    @Column(name="price_no_discount")
    private BigDecimal price;

    @Column(name="number_of_views")
    private Long numberOfViews;
}
