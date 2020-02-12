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
import pl.haladyj.pawelhaladyjservice.model.converter.ProductClickConverter;
import pl.haladyj.pawelhaladyjservice.model.converter.ProductConverter;
import pl.haladyj.pawelhaladyjservice.payload.ClickCounter;
import pl.haladyj.pawelhaladyjservice.payload.DiscountStrategy;
import pl.haladyj.pawelhaladyjservice.repository.ProductRepository;
import pl.haladyj.pawelhaladyjservice.service.ProductServiceImpl;

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

	@Test
	void contextLoads() {
	}

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

}
