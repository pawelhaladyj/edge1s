package pl.haladyj.pawelhaladyjservice.service;

import org.springframework.stereotype.Component;
import pl.haladyj.pawelhaladyjservice.exception.ProductNotFoundException;
import pl.haladyj.pawelhaladyjservice.model.ProductType;

import java.math.BigDecimal;

@Component
public class DiscountStrategy {

    BigDecimal pickDiscount(ProductType productType){
        if(productType == ProductType.MALE){
            return new BigDecimal(5);
        } else if (productType == ProductType.FEMALE){
            return new BigDecimal(5);
        } else if(productType == ProductType.KID){
            return new BigDecimal(10);
        } else{
            throw new ProductNotFoundException("indicated improper product type: out of range MALE, FEMALE, KID");
        }
    }
}
