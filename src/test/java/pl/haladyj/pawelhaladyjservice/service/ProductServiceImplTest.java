package pl.haladyj.pawelhaladyjservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.model.ProductAdditions;
import pl.haladyj.pawelhaladyjservice.model.ProductType;
import pl.haladyj.pawelhaladyjservice.model.converter.ProductClickConverter;
import pl.haladyj.pawelhaladyjservice.model.converter.ProductConverter;
import pl.haladyj.pawelhaladyjservice.payload.ClickCounter;
import pl.haladyj.pawelhaladyjservice.payload.DiscountStrategy;
import pl.haladyj.pawelhaladyjservice.repository.ProductRepository;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;
    @Mock
    private ProductConverter productConverter;
    @Mock
    private ProductClickConverter productClickConverter;
    @Mock
    private ClickCounter clickCounter;
    @Mock
    private DiscountStrategy discountStrategy;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        this.productService = new ProductServiceImpl(
                repository,
                productConverter,
                productClickConverter,
                clickCounter,
                discountStrategy
        );
    }

    @DisplayName("Should get product with existing id and:")
    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("provideExistingProductsById")
    void shouldGetProductsDetailsWithExistingProductIds(
            //given
            String caseName,
            Long id,
            Product productFoundById,
            Product productSaved,
            ProductDto productDtoSaved,
            BigDecimal discount,
            ProductDto productDtoExpected
    ) {
        //when
        when(repository.findById(id)).thenReturn(Optional.of(productFoundById));

        ProductDto actualProductDto = productService.findProductById(id);

        //then
        assertAll(()-> verify(repository, times(1)).save(productFoundById),
                ()->assertEquals(productDtoExpected,actualProductDto));
    }


    @Test
    void findProductById() {

    }

    @Test
    void findProductClicks() {
    }

    @Test
    void findAllProducts() {
    }

    @Test
    void createProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }

    private static Stream<Arguments> provideExistingProductsById() {
        ProductAdditions productAdditions = new ProductAdditions();
        productAdditions.setClickCounter(4L);

        Product p1FoundById = new Product();
        p1FoundById.setId(1L);
        p1FoundById.setName("name1");
        p1FoundById.setDescription("description1");
        p1FoundById.setType(ProductType.MALE);
        p1FoundById.setPrice(BigDecimal.valueOf(100L));
        p1FoundById.setProductAdditions(productAdditions);

        Product p1Saved = new Product();
        p1Saved.setId(1L);
        p1Saved.setName("name1");
        p1Saved.setDescription("description1");
        p1Saved.setType(ProductType.MALE);
        p1Saved.setPrice(BigDecimal.valueOf(100L));
        p1Saved.setProductAdditions(productAdditions);

        ProductDto pd1 = new ProductDto();
        pd1.setName("name1");
        pd1.setDescription("description1");
        pd1.setType(ProductType.MALE);
        pd1.setInitialPrice(BigDecimal.valueOf(100L));
        pd1.setDiscountedPrice(BigDecimal.valueOf(95L));

        ProductDto pdExpected1 = new ProductDto();
        pdExpected1.setName("name1");
        pdExpected1.setDescription("description1");
        pdExpected1.setType(ProductType.MALE);
        pdExpected1.setInitialPrice(BigDecimal.valueOf(100L));
        pdExpected1.setDiscountedPrice(BigDecimal.valueOf(95L));

        Product p2FoundById = new Product();
        p2FoundById.setId(2L);
        p2FoundById.setName("name2");
        p2FoundById.setDescription("description2");
        p2FoundById.setType(ProductType.FEMALE);
        p2FoundById.setPrice(BigDecimal.valueOf(1000L));
        p2FoundById.setProductAdditions(productAdditions);

        Product p2Saved = new Product();
        p2Saved.setId(2L);
        p2Saved.setName("name2");
        p2Saved.setDescription("description2");
        p2Saved.setType(ProductType.FEMALE);
        p2Saved.setPrice(BigDecimal.valueOf(1000L));
        p2Saved.setProductAdditions(productAdditions);

        ProductDto pd2 = new ProductDto();
        pd2.setName("name2");
        pd2.setDescription("description2");
        pd2.setType(ProductType.FEMALE);
        pd2.setInitialPrice(BigDecimal.valueOf(1000L));
        pd2.setDiscountedPrice(BigDecimal.valueOf(950L));

        ProductDto pdExpected2 = new ProductDto();
        pdExpected2.setName("name2");
        pdExpected2.setDescription("description2");
        pdExpected2.setType(ProductType.FEMALE);
        pdExpected2.setInitialPrice(BigDecimal.valueOf(1000L));
        pdExpected2.setDiscountedPrice(BigDecimal.valueOf(950L));

        Product p3FoundById = new Product();
        p3FoundById.setId(3L);
        p3FoundById.setName("name3");
        p3FoundById.setDescription("description3");
        p3FoundById.setType(ProductType.KID);
        p3FoundById.setPrice(BigDecimal.valueOf(10000L));
        p3FoundById.setProductAdditions(productAdditions);

        Product p3Saved = new Product();
        p3Saved.setId(3L);
        p3Saved.setName("name3");
        p3Saved.setDescription("description3");
        p3Saved.setType(ProductType.KID);
        p3Saved.setPrice(BigDecimal.valueOf(10000L));
        p3Saved.setProductAdditions(productAdditions);

        ProductDto pd3 = new ProductDto();
        pd3.setName("name3");
        pd3.setDescription("description3");
        pd3.setType(ProductType.KID);
        pd3.setInitialPrice(BigDecimal.valueOf(10000L));
        pd3.setDiscountedPrice(BigDecimal.valueOf(9000L));

        ProductDto pdExpected3 = new ProductDto();
        pdExpected3.setName("name3");
        pdExpected3.setDescription("description3");
        pdExpected3.setType(ProductType.KID);
        pdExpected3.setInitialPrice(BigDecimal.valueOf(10000L));
        pdExpected3.setDiscountedPrice(BigDecimal.valueOf(9000L));

        return Stream.of(
                Arguments.of("MALE strategy", 1L, p1FoundById, p1Saved, pd1, BigDecimal.valueOf(5L), pdExpected1),
                Arguments.of("FEMALE strategy", 2L, p2FoundById, p2Saved, pd2, BigDecimal.valueOf(5L), pdExpected2),
                Arguments.of("KID strategy", 3L, p3FoundById, p3Saved, pd3, BigDecimal.valueOf(10L), pdExpected3)

        );
    }
}