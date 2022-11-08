package dme.ecommerce.prices.persistence.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import dme.ecommerce.prices.persitence.entity.PriceListEntity;
import dme.ecommerce.prices.persitence.repository.PriceListRepository;
import lombok.extern.slf4j.Slf4j;

@SpringJUnitConfig
@ActiveProfiles({"test"})
@DataJpaTest
@Slf4j
public class PriceListRepositoryTest {
	
	@Autowired
	private PriceListRepository repository;
	
	@Test
	void shouldReturnCompletePriceList_toCheck_databaseAndEntity_work() {
		// When
		final Iterable<PriceListEntity> actualList = this.repository.findAll();
		
		actualList.forEach(price -> {
			PriceListRepositoryTest.log.info("{}", price);
		});
		
		// Then
		Assertions.assertEquals(4, StreamSupport.stream(actualList.spliterator(), false).count());
	}

	@Test
	void shouldReturnPrice3550_whenOnlyOnePriceMatch() {
		// Given
		final Integer branchId = 1;
		final Integer productId = 35455;
		final LocalDateTime date = LocalDateTime.of(2020, 6, 14, 19, 05);
		final PriceListEntity expectedPrice = PriceListEntity.builder()
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
		final List<PriceListEntity> actualList = this.repository.findPrice(date, productId, branchId);
		
		// Then
		Assertions.assertFalse(actualList.isEmpty());
		Assertions.assertEquals(expectedPrice, actualList.get(0));
	}
	
	@Test
	void shouldReturnPriorizedPrice2545_whenTwoPricesMatch() {
		// Given
		final Integer branchId = 1;
		final Integer productId = 35455;
		final LocalDateTime date = LocalDateTime.of(2020, 6, 14, 18, 05);
		final PriceListEntity expectedPrice = PriceListEntity.builder()
				.id(2)
				.brandId(1)
				.productId(35455)
				.priceList(2)
				.startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
				.endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
				.priority(1)
				.price(new BigDecimal("25.45"))
				.curr("EUR")
				.build();
		// When
		final List<PriceListEntity> actualList = this.repository.findPrice(date, productId, branchId);
		
		// Then
		Assertions.assertFalse(actualList.isEmpty());
		Assertions.assertEquals(expectedPrice, actualList.get(0));
	}
	
}
