package pl.haladyj.pawelhaladyjservice.payload;

import org.springframework.stereotype.Component;
import pl.haladyj.pawelhaladyjservice.exception.ProductNotFoundException;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.model.ProductType;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

@Component
public class DiscountStrategy {

/*    private static final BigDecimal MALE_DISCOOUNT_VALUE = new BigDecimal(5);
    private static final BigDecimal FEMALE_DISCOOUNT_VALUE = new BigDecimal (5);
    private static final BigDecimal KID_DISCOOUNT_VALUE = new BigDecimal(10);*/



/*    static BigDecimal pickDiscount(ProductType productType){
        if(productType == ProductType.MALE){
            return MALE_DISCOOUNT_VALUE;
        } else if (productType == ProductType.FEMALE){
            return FEMALE_DISCOOUNT_VALUE;
        } else if(productType == ProductType.KID){
            return KID_DISCOOUNT_VALUE;
        } else{
            throw new ProductNotFoundException("indicated improper product type: out of range MALE, FEMALE, KID");
        }
    }*/

    private static final BigDecimal DISCOUNT_LEVEL_0 = BigDecimal.valueOf(0L);
    private static final BigDecimal DISCOUNT_LEVEL_1 = BigDecimal.valueOf(3L);
    private static final BigDecimal DISCOUNT_LEVEL_2 = BigDecimal.valueOf(5L);

    private static final BigDecimal VALUE_LEVEL_1 = BigDecimal.valueOf(500L);
    private static final BigDecimal VALUE_LEVEL_2 = BigDecimal.valueOf(2000L);



    static BigDecimal pickDiscount(Product product) {

        checkArgument(nonNull(product), "Expected non-null product");

        if (product.getPrice().compareTo(VALUE_LEVEL_1) >= 0
                && product.getPrice().compareTo(VALUE_LEVEL_2) < 0) {
            return DISCOUNT_LEVEL_1;
        }

        if (product.getPrice().compareTo(VALUE_LEVEL_2) >= 0) {
            return DISCOUNT_LEVEL_2;
        }

        return DISCOUNT_LEVEL_0;
    }



    public BigDecimal calculateDiscountedPrice(Product product) {
        return product.getPrice().multiply(
                BigDecimal.valueOf(100).subtract(DiscountStrategy.pickDiscount(product)))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
