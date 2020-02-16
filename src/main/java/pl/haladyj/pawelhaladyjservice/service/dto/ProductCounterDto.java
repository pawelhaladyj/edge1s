package pl.haladyj.pawelhaladyjservice.service.dto;

import java.util.Objects;

public class ProductCounterDto {
    private Long clickCounter;

    public ProductCounterDto() {
    }

    public ProductCounterDto(Long clickCounter) {
        this.clickCounter = clickCounter;
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
        if (!(o instanceof ProductCounterDto)) return false;
        ProductCounterDto that = (ProductCounterDto) o;
        return Objects.equals(getClickCounter(), that.getClickCounter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClickCounter());
    }

    @Override
    public String toString() {
        return "ProductCounterDto{" +
                "clickCounter=" + clickCounter +
                '}';
    }
}
