package pl.haladyj.pawelhaladyjservice.model;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductAdditions {

    private Long clickCounter;

    public ProductAdditions(Long clickCounter) {
        this.clickCounter = clickCounter;
    }

    public ProductAdditions() {
    }

    public Long getClickCounter() {
        return clickCounter;
    }

    public void setClickCounter(Long clickCounter) {
        this.clickCounter = clickCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductAdditions)) return false;
        ProductAdditions that = (ProductAdditions) o;
        return Objects.equals(getClickCounter(), that.getClickCounter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClickCounter());
    }

    @Override
    public String toString() {
        return "ProductAdditions{" +
                "clickCounter=" + clickCounter +
                '}';
    }
}
