package pl.haladyj.pawelhaladyjservice.payload;

import org.springframework.stereotype.Component;
import pl.haladyj.pawelhaladyjservice.exception.ProductNotFoundException;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.model.ProductType;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DiscountStrategy {

    private static final BigDecimal MALE_DISCOOUNT_VALUE = new BigDecimal(5);
    private static final BigDecimal FEMALE_DISCOOUNT_VALUE = new BigDecimal (5);
    private static final BigDecimal KID_DISCOOUNT_VALUE = new BigDecimal(10);

    static BigDecimal pickDiscount(ProductType productType){
        if(productType == ProductType.MALE){
            return MALE_DISCOOUNT_VALUE;
        } else if (productType == ProductType.FEMALE){
            return FEMALE_DISCOOUNT_VALUE;
        } else if(productType == ProductType.KID){
            return KID_DISCOOUNT_VALUE;
        } else{
            throw new ProductNotFoundException("indicated improper product type: out of range MALE, FEMALE, KID");
        }
    }

    public BigDecimal calulateDiscountedPrice(Product product){
        return product.getPrice().multiply(
                BigDecimal.valueOf(100).subtract(DiscountStrategy.pickDiscount(product.getType())))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
