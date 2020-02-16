package pl.haladyj.pawelhaladyjservice.payload;

import org.springframework.stereotype.Component;
import pl.haladyj.pawelhaladyjservice.model.Product;

@Component
public class ClickCounter {

    public Long updateCounter(Product product){

        Long currentClickCounter = product.getClickCounter();
        Long updatedClickCounter = currentClickCounter + 1;
        product.setClickCounter(updatedClickCounter);

        return updatedClickCounter;
    }
}
