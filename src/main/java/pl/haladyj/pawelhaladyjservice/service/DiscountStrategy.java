package pl.haladyj.pawelhaladyjservice.service;

import org.springframework.stereotype.Component;
import pl.haladyj.pawelhaladyjservice.exception.ProductNotFoundException;
import pl.haladyj.pawelhaladyjservice.model.ProductType;

import java.math.BigDecimal;

@Component
public class DiscountStrategy {

    private final BigDecimal MALE_DISCOOUNT_VALUE = new BigDecimal(5);
    private final BigDecimal FEMALE_DISCOOUNT_VALUE = new BigDecimal (5);
    private final BigDecimal KID_DISCOOUNT_VALUE = new BigDecimal(10);

    BigDecimal pickDiscount(ProductType productType){
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
}
