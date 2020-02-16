package pl.haladyj.pawelhaladyjservice.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import pl.haladyj.pawelhaladyjservice.model.ProductType;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductCounterDto;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Sql("/createProduct.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductServiceImplTestIT {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    void shouldFindProductDetailsById() {
        // given
        Long givenId = 11L;

        ProductDto expectedSavedDtoProduct = new ProductDto();
        expectedSavedDtoProduct.setName("name");
        expectedSavedDtoProduct.setDescription("desc");
        expectedSavedDtoProduct.setType(ProductType.MALE);
        expectedSavedDtoProduct.setInitialPrice(BigDecimal.valueOf(80000, 2));
        expectedSavedDtoProduct.setDiscountedPrice(BigDecimal.valueOf(77600, 2));
        expectedSavedDtoProduct.setClickCounter(5L);

        // when
        Optional<ProductDto> actualOptionalProductDto = productService.findProductById(givenId);

        // then
        Assert.assertTrue(actualOptionalProductDto.isPresent());
        ProductDto actualProductDto = actualOptionalProductDto.get();
        assertAll(
                () -> assertEquals(expectedSavedDtoProduct.getName(), actualProductDto.getName()),
                () -> assertEquals(expectedSavedDtoProduct.getDescription(), actualProductDto.getDescription()),
                () -> assertEquals(expectedSavedDtoProduct.getType(), actualProductDto.getType()),
                () -> assertEquals(expectedSavedDtoProduct.getInitialPrice(), actualProductDto.getInitialPrice()),
                () -> assertEquals(expectedSavedDtoProduct.getDiscountedPrice(), actualProductDto.getDiscountedPrice()),
                () -> assertEquals(expectedSavedDtoProduct.getClickCounter(), actualProductDto.getClickCounter()));
    }

    @Test
    void shouldGetAnEmptyOptionalWhenGetProductDetailsWithNotExistingId() {
        // given
        Long givenId = 99L;

        // when
        Optional<ProductDto> actualOptionalProductDto = productService.findProductById(givenId);

        // then
        Assert.assertFalse(actualOptionalProductDto.isPresent());
    }

    @Test
    void shouldFindProductClicksById() {
        // given
        Long givenId = 11L;

        ProductCounterDto expProductCounterDto = new ProductCounterDto();
        expProductCounterDto.setClickCounter(4L);

        // when
        Optional<ProductCounterDto> actualProductCounterDto =
                productService.findProductCountsById(givenId);

        // then
        Assert.assertTrue(actualProductCounterDto.isPresent());
        ProductCounterDto actProductRatingDto = actualProductCounterDto.get();
        assertEquals(expProductCounterDto.getClickCounter(), actProductRatingDto.getClickCounter());
    }

    @Test
    void shouldGetAnEmptyOptionalWhenGetProdoctClicksWithNotExistingId() {
        // given
        Long givenId = 99L;

        // when
        Optional<ProductCounterDto> actualProductCounterDto =
                productService.findProductCountsById(givenId);

        // then
        Assert.assertFalse(actualProductCounterDto.isPresent());
    }
}
