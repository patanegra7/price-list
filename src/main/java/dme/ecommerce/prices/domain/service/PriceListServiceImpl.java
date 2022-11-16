package dme.ecommerce.prices.domain.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import dme.ecommerce.prices.domain.entity.Price;
import dme.ecommerce.prices.persitence.repository.PriceListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PriceList service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PriceListServiceImpl implements PriceListService {
	
	private final PriceListRepository priceListRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public Optional<Price> findPrice(final LocalDateTime date, final Integer productId, final Integer brandId) {
		PriceListServiceImpl.log.info("Find Price for brandId: {}, productId: {} and date: {}", brandId, productId, date);
		final var price = this.priceListRepository.findPrice(date, productId, brandId)
				.stream().findFirst()
				.map(p -> this.modelMapper.map(p, Price.class));
		PriceListServiceImpl.log.info("Price found: {}", price);
		return price;
	}
	
}
