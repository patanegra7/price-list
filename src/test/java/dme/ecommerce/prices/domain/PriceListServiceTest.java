package dme.ecommerce.prices.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;

import dme.ecommerce.prices.domain.entity.Price;
import dme.ecommerce.prices.domain.service.PriceListServiceImpl;
import dme.ecommerce.prices.persitence.entity.PriceListEntity;
import dme.ecommerce.prices.persitence.repository.PriceListRepository;

/**
 * Unit Test {@link PriceListServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PriceListServiceTest {
	
	@Mock
	private PriceListRepository priceListRepository;
	
	@Spy
	private final ModelMapper modelMapper = new ModelMapper();
	
	@InjectMocks
	private PriceListServiceImpl priceListService;
	
	@Test
	void shouldReturnOptionalEmpty_whenAnyPriceIsFound() {
		// Given
		final Integer brandId = 1;
		final Integer productId = 35455;
		final LocalDateTime date = LocalDateTime.of(2020, 6, 14, 19, 05);
		Mockito.when(this.priceListRepository.findPrice(date, productId, brandId)).thenReturn(Collections.emptyList());
		
		// When
		final Optional<Price> actualPrice = this.priceListService.findPrice(date, productId, brandId);
		
		// Then
		Assertions.assertFalse(actualPrice.isPresent());
	}
	
	@Test
	void shouldReturnPrice_whenValidBrandId_productId_and_Date_isProvided() {
		// Given
		final Integer brandId = 1;
		final Integer productId = 35455;
		final LocalDateTime date = LocalDateTime.of(2020, 6, 14, 19, 05);
		final PriceListEntity priceList = PriceListEntity.builder()
				.id(1)
				.brandId(1)
				.productId(35455)
				.priceList(1)
				.startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
				.endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
				.priority(0)
				.price(new BigDecimal("35.50"))
				.curr("EUR")
				.build();
		Mockito.when(this.priceListRepository.findPrice(date, productId, brandId)).thenReturn(List.of(priceList));
		
		final Price expectedPrice = Price.builder()
				.id(1)
				.brandId(1)
				.productId(35455)
				.priceList(1)
				.startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
				.endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
				.priority(0)
				.price(new BigDecimal("35.50"))
				.curr("EUR")
				.build();
		
		// When
		final Optional<Price> actualPrice = this.priceListService.findPrice(date, productId, brandId);
		
		// Then
		Assertions.assertTrue(actualPrice.isPresent());
		Assertions.assertEquals(expectedPrice, actualPrice.get());
	}
}
