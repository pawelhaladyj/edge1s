package pl.haladyj.pawelhaladyjservice.payload;

import org.springframework.stereotype.Component;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.model.ProductAdditions;

@Component
public class ClickCounter {

    public ProductAdditions updateCounter(Product product){
        ProductAdditions productAdditions = product.getProductAdditions();
        Long currentClicks = productAdditions.getClickCounter();
        productAdditions.setClickCounter(currentClicks+1);
        return productAdditions;
    }
}
