package dme.ecommerce.prices.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import dme.ecommerce.prices.domain.entity.Price;
import dme.ecommerce.prices.persitence.entity.PriceListEntity;
import dme.ecommerce.prices.persitence.repository.PriceListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PriceList service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PriceListService {
	
	private final PriceListRepository priceListRepository;
	private final ModelMapper modelMapper;
	
	public Optional<Price> findPrice(final LocalDateTime date, final Integer productId, final Integer brandId) {
		PriceListService.log.info("Find Price for brandId: {}, productId: {} and date: {}", brandId, productId, date);
		final List<PriceListEntity> prices = this.priceListRepository.findPrice(date, productId, brandId);
		if(!prices.isEmpty()) {
			PriceListService.log.info("Price found: {}", prices.get(0));
			return Optional.of(this.modelMapper.map(prices.get(0), Price.class));
		}
		PriceListService.log.info("Price not found!");
		return Optional.empty();
	}
	
}
