package dme.ecommerce.prices.domain.service;

import java.time.LocalDateTime;
import java.util.Optional;

import dme.ecommerce.prices.domain.entity.Price;

/**
 * PriceListService interface
 */
public interface PriceListService {

	Optional<Price> findPrice(LocalDateTime date, Integer productId, Integer brandId);

}