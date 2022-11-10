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

/**
 * PriceList service
 */
@Service
@RequiredArgsConstructor
public class PriceListService {
	
	private final PriceListRepository priceListRepository;
	private final ModelMapper modelMapper;
	
	public Optional<Price> findPrice(final LocalDateTime date, final Integer productId, final Integer brandId) {
		
		final List<PriceListEntity> prices = this.priceListRepository.findPrice(date, productId, brandId);
		if(!prices.isEmpty()) {
			return Optional.of(this.modelMapper.map(prices.get(0), Price.class));
		} 
		return Optional.empty();
	}
	
}
