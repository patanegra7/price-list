package dme.ecommerce.prices.app.resource;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dme.ecommerce.prices.app.dto.PriceDto;
import dme.ecommerce.prices.app.dto.PriceSearchDto;
import dme.ecommerce.prices.app.exception.PriceNotFoundException;
import dme.ecommerce.prices.app.exception.RequiredArgumentsException;
import dme.ecommerce.prices.domain.entity.Price;
import dme.ecommerce.prices.domain.service.PriceListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Price API
 */
@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
@Slf4j
public class PriceResource {
	private final ModelMapper modelMapper;
	private final PriceListService priceListService;

	/**
	 * Search by brandId, productId and date.
	 */
	@GetMapping("/{brandId}")
	public PriceDto searchPrice(@PathVariable final Integer brandId, @RequestBody final PriceSearchDto searchParams) throws RequiredArgumentsException, PriceNotFoundException{
		if(brandId == null || searchParams == null || searchParams.getDate() == null || searchParams.getProductId() == null) {
			throw new RequiredArgumentsException();
		}
		PriceResource.log.info("Search Price for brandId: {} and {}", brandId, searchParams);
		final Optional<Price> price =  this.priceListService.findPrice(searchParams.getDate(), searchParams.getProductId(), brandId);
		
		if(price.isPresent()) {
			final var priceDto = this.modelMapper.map(price.get(),PriceDto.class);
			PriceResource.log.info("PriceDTO return: {}", priceDto);
			return priceDto;
		}
		throw new PriceNotFoundException();
	}
	
}
