package pl.haladyj.pawelhaladyjservice;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import pl.haladyj.pawelhaladyjservice.exception.ProductNotFoundException;
import pl.haladyj.pawelhaladyjservice.model.Product;
import pl.haladyj.pawelhaladyjservice.model.ProductAdditions;
import pl.haladyj.pawelhaladyjservice.model.converter.ProductClickConverter;
import pl.haladyj.pawelhaladyjservice.model.converter.ProductConverter;
import pl.haladyj.pawelhaladyjservice.payload.ClickCounter;
import pl.haladyj.pawelhaladyjservice.payload.DiscountStrategy;
import pl.haladyj.pawelhaladyjservice.repository.ProductRepository;
import pl.haladyj.pawelhaladyjservice.service.ProductServiceImpl;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductClicksDto;
import pl.haladyj.pawelhaladyjservice.service.dto.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class PawelHaladyjServiceApplicationTests {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private ProductClickConverter productClickConverter;

	@Mock
	private ProductConverter productConverter;

	@Mock
	private DiscountStrategy discountStrategy;

	@Mock
	private ClickCounter clickCounter;

	@InjectMocks
	private ProductServiceImpl productService;

/*	@Test
	void contextLoads() {
	}*/

	@Test
	void testDeleteProduct() {
		//given
		Long mockId = Mockito.anyLong();
		Optional<Product> productOptional = Optional.of(new Product());
		Mockito.when(productRepository.findById(mockId)).thenReturn(productOptional);
		//when
		productService.deleteProduct(mockId);
		//then
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(mockId);
	}

	@Test
	void testDeleteProduct_NoProductThrowsException() {
		//given
		Long mockId = Mockito.anyLong();
		Optional<Product> emptyOptional = Optional.empty();
		Mockito.when(productRepository.findById(mockId)).thenReturn(emptyOptional);
		//when
		Exception exception = Assertions.assertThrows(
				ProductNotFoundException.class, () -> productService.deleteProduct(mockId));
		//then
		Assert.assertEquals("id: " + mockId + " does not exist", exception.getMessage());
	}

	@Test
	void testFindProductClicks() {
		//given
		Long mockId = Mockito.anyLong();

		Product product = new Product();
		product.setId(mockId);

		ProductClicksDto actual = new ProductClicksDto();
		actual.setId(mockId);

		Optional<Product> productOptional = Optional.of(product);
		Mockito.when(productRepository.findById(mockId)).thenReturn(productOptional);
		Mockito.when(productClickConverter.toDto(productOptional.get())).thenReturn(actual);

		//when
		ProductClicksDto result = productService.findProductClicks(mockId);

		//then
		Assert.assertEquals(actual, result);
	}

	@Test
	void testFindProductClicks_NoProductThrowsException() {
		//given
		Long mockId = Mockito.anyLong();
		Optional<Product> emptyOptional = Optional.empty();
		Mockito.when(productRepository.findById(mockId)).thenReturn(emptyOptional);

		//when
		Exception exception = Assertions.assertThrows(
				ProductNotFoundException.class, () -> productService.findProductClicks(mockId));
		//then
		Assert.assertEquals("id: " + mockId + " does not exist", exception.getMessage());
	}

	@Test
	void testFindProductById() {
		//given
		Long mockId = Mockito.anyLong();

		BigDecimal actualDiscountedPrice = new BigDecimal("2.22");

		Product product = new Product();
		product.setId(mockId);

		//expected
		ProductDto actual = new ProductDto();

		ProductAdditions productAdditions = new ProductAdditions();

		Optional<Product> productOptional = Optional.of(product);
		Mockito.when(productRepository.findById(mockId)).thenReturn(productOptional);
		Mockito.when(productConverter.toDto(productOptional.get())).thenReturn(actual);
		Mockito.when(discountStrategy.calculateDiscountedPrice(productOptional.get())).thenReturn(actualDiscountedPrice);
		Mockito.when(clickCounter.updateCounter(productOptional.get())).thenReturn(productAdditions);

		//when
		//actual
		ProductDto result = productService.findProductById(mockId);

		//then
		Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
		Assert.assertEquals(actual, result);
		Assert.assertEquals(actualDiscountedPrice, result.getDiscountedPrice());
		Assert.assertEquals(productAdditions, productOptional.get().getProductAdditions());
	}

	@Test
	void testFindProductById_NoProductThrowsException() {
		//given
		Long mockId = Mockito.anyLong();
		Optional<Product> emptyOptional = Optional.empty();
		Mockito.when(productRepository.findById(mockId)).thenReturn(emptyOptional);

		//when
		Exception exception = Assertions.assertThrows(
				ProductNotFoundException.class, () -> productService.findProductById(mockId));

		//then
		Assert.assertEquals("id: " + mockId + " does not exist", exception.getMessage());
	}

	@Test
	void testFindAllProducts_TwoProducts() {
		//given
		Product productOne = new Product();
		productOne.setId(1l);

		Product productTwo = new Product();
		productOne.setId(2l);

		List<Product> products = new ArrayList<>();
		products.add(productOne);
		products.add(productTwo);

		int count = products.size();

		ProductAdditions productAdditions = new ProductAdditions();

		BigDecimal priceOne = new BigDecimal("1.11");
		BigDecimal priceTwo = new BigDecimal("2.22");

		Mockito.when(productRepository.findAll()).thenReturn(products);
		Mockito.when(productConverter.toDto(productOne)).thenReturn(new ProductDto());
		Mockito.when(productConverter.toDto(productTwo)).thenReturn(new ProductDto());
		Mockito.when(discountStrategy.calculateDiscountedPrice(productOne)).thenReturn(priceOne);
		Mockito.when(discountStrategy.calculateDiscountedPrice(productTwo)).thenReturn(priceTwo);
		Mockito.when(clickCounter.updateCounter(productOne)).thenReturn(productAdditions);
		Mockito.when(clickCounter.updateCounter(productTwo)).thenReturn(productAdditions);

		//when
		List<ProductDto> productDtos = productService.findAllProducts();

		//then
		Assert.assertNotNull(productDtos);
		Assert.assertFalse(productDtos.isEmpty());
		Assert.assertEquals(count, productDtos.size());
		Mockito.verify(productRepository, Mockito.times(count)).save(Mockito.any(Product.class));
		Assert.assertEquals(productAdditions, productOne.getProductAdditions());
		Assert.assertEquals(productAdditions, productTwo.getProductAdditions());
		Assert.assertEquals(priceOne, productDtos.get(0).getDiscountedPrice());
		Assert.assertEquals(priceTwo, productDtos.get(1).getDiscountedPrice());
	}

	@Test
	void testFindAllProducts_NoProducts() {
		//given
		Mockito.when(productRepository.findAll()).thenReturn(new ArrayList<>());

		//when
		List<ProductDto> products = productService.findAllProducts();

		//then
		Assert.assertNotNull(products);
		Assert.assertTrue(products.isEmpty());
	}





}
