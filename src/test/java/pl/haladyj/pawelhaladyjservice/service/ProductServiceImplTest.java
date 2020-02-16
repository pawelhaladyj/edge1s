package pl.haladyj.pawelhaladyjservice.service;


import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.model.ProductType;
import pl.haladyj.pawelhaladyjservice.model.converter.ProductConverter;
import pl.haladyj.pawelhaladyjservice.payload.ClickCounter;
import pl.haladyj.pawelhaladyjservice.payload.DiscountStrategy;
import pl.haladyj.pawelhaladyjservice.repository.ProductRepository;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductCounterDto;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;
    @Mock
    private ProductConverter productConverter;
    @Mock
    private ClickCounter clickCounter;
    @Mock
    private DiscountStrategy discountStrategy;

    private ProductServiceImpl productService;

    @BeforeEach()
    void setUp() {
        initMocks(this);
        productService = new ProductServiceImpl(repository, productConverter, clickCounter, discountStrategy);
    }

    @Test
    void shouldThrowExceptionWhenProductIdIsNull() {
        //given
        Long givenProductId = null;
        String expectedExceptionMessage = "Expected non-null id";

        //when
        Throwable exception =
                assertThrows(IllegalArgumentException.class,
                        () -> productService.findProductById(givenProductId));

        //then
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    void shouldFindProductDetailsById() {
        //given
        Long givenId = anyLong();

        Product product = new Product();
        product.setId(givenId);
        product.setName("name");
        product.setDescription("description");
        product.setType(ProductType.MALE);
        product.setPrice(BigDecimal.valueOf(800L));
        product.setClickCounter(9L);
        Optional<Product> productOptional = Optional.of(product);

        Long givenClickCounter = 10L;

        Product savedProduct = new Product();
        savedProduct.setId(givenId);
        savedProduct.setName("name");
        savedProduct.setDescription("description");
        savedProduct.setType(ProductType.MALE);
        savedProduct.setPrice(BigDecimal.valueOf(800L));
        savedProduct.setClickCounter(10L);

        ProductDto savedDtoProduct = new ProductDto();
        savedDtoProduct.setName("name");
        savedDtoProduct.setDescription("description");
        savedDtoProduct.setType(ProductType.MALE);
        savedDtoProduct.setInitialPrice(BigDecimal.valueOf(800L));
        savedDtoProduct.setClickCounter(10L);

        BigDecimal discountedPrice = BigDecimal.valueOf(776L);

        ProductDto expectedSavedDtoProduct = new ProductDto();
        expectedSavedDtoProduct.setName("name");
        expectedSavedDtoProduct.setDescription("description");
        expectedSavedDtoProduct.setType(ProductType.MALE);
        expectedSavedDtoProduct.setInitialPrice(BigDecimal.valueOf(800L));
        expectedSavedDtoProduct.setDiscountedPrice(BigDecimal.valueOf(776L));
        expectedSavedDtoProduct.setClickCounter(10L);

        when(repository.findById(givenId)).thenReturn(productOptional);
        when(clickCounter.updateCounter(product)).thenReturn(givenClickCounter);
        when(repository.save(product)).thenReturn(savedProduct);
        when(productConverter.toDto(savedProduct)).thenReturn(savedDtoProduct);
        when(discountStrategy.calculateDiscountedPrice(savedProduct)).thenReturn(discountedPrice);

        //when
        Optional<ProductDto> actualOptionalProductDto = productService.findProductById(givenId);

        //then
        verify(repository, times(1)).save(Matchers.any(Product.class));

        Assert.assertTrue(actualOptionalProductDto.isPresent());
        ProductDto actualProductDto = actualOptionalProductDto.get();
        assertAll(
                () -> assertEquals(actualProductDto.getName(), expectedSavedDtoProduct.getName()),
                () -> assertEquals(actualProductDto.getDescription(), expectedSavedDtoProduct.getDescription()),
                () -> assertEquals(actualProductDto.getType(), expectedSavedDtoProduct.getType()),
                () -> assertEquals(actualProductDto.getInitialPrice(), expectedSavedDtoProduct.getInitialPrice()),
                () -> assertEquals(actualProductDto.getDiscountedPrice(), expectedSavedDtoProduct.getDiscountedPrice()),
                () -> assertEquals(actualProductDto.getClickCounter(), expectedSavedDtoProduct.getClickCounter())
        );

    }

    @Test
    void shouldGetEmptyOptionalWhenFindProductByNotExistingId() {
        //given
        Long givenId = anyLong();
        when(repository.findById(givenId)).thenReturn(Optional.empty());

        //when
        Optional<ProductDto> actualOptionalProductDto = productService.findProductById(givenId);

        //then
        verify(repository,times(0)).save(any(Product.class));
        assertFalse(actualOptionalProductDto.isPresent());
    }

    @Test
    void shouldFindProductClicksById(){
        //given
        Long givenId = anyLong();

        Product product = new Product();
        product.setId(givenId);
        product.setName("name");
        product.setDescription("description");
        product.setType(ProductType.FEMALE);
        product.setPrice(BigDecimal.valueOf(800));
        product.setClickCounter(10L);
        Optional<Product> optionalProduct = Optional.of(product);

        ProductCounterDto productCounterDto = new ProductCounterDto();
        productCounterDto.setClickCounter(10L);

        when(repository.findById(givenId)).thenReturn(optionalProduct);
        when(productConverter.toCounterDto(product)).thenReturn(productCounterDto);

        //when
        Optional<ProductCounterDto> actualProductCounterDto =
                productService.findProductCountsById(givenId);

        //then
        Assert.assertTrue(actualProductCounterDto.isPresent());
        ProductCounterDto actProductCounterDto = actualProductCounterDto.get();
        assertEquals(productCounterDto.getClickCounter(), actProductCounterDto.getClickCounter());
    }

    @Test
    void shouldGetAnEmptyOptionalWhenFindProductClicksByNotExistingId() {
        // given
        Long givenId = anyLong();

        when(repository.findById(givenId)).thenReturn(Optional.empty());

        // when
        Optional<ProductCounterDto> actualProductCounterDto =
                productService.findProductCountsById(givenId);

        // then
        verify(repository, times(0)).save(any(Product.class));
        Assert.assertFalse(actualProductCounterDto.isPresent());
    }
}