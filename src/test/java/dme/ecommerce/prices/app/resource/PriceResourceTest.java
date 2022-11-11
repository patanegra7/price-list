package dme.ecommerce.prices.app.resource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dme.ecommerce.prices.app.dto.PriceSearchDto;
import dme.ecommerce.prices.domain.entity.Price;
import dme.ecommerce.prices.domain.service.PriceListService;

/**
 * Price resource test
 */
@ExtendWith(SpringExtension.class)
@Import(PriceResourceTestConfig.class)
@WebMvcTest(PriceResource.class)
//@ActiveProfiles("test")
public class PriceResourceTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PriceListService priceListService;
	
	@Test
	public void shouldReturnPriceDTO_whenPriceIsFound() throws Exception {
		// Given
		final Integer brandId = 1;
		final var searchParams = PriceSearchDto.builder()
				.date(LocalDateTime.of(2020, 6, 14, 19, 05))
				.productId(35455)
				.build();
		final var givenPrice = Price.builder()
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
		Mockito.when(this.priceListService.findPrice(searchParams.getDate(), searchParams.getProductId(), brandId))
		.thenReturn(Optional.of(givenPrice));
		
		// When_Then
		this.mockMvc.perform(MockMvcRequestBuilders.get("/prices/{brandId}", brandId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.asJsonString(searchParams)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.comparesEqualTo(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("brandId", Matchers.comparesEqualTo(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("productId", Matchers.comparesEqualTo(35455)))
		.andExpect(MockMvcResultMatchers.jsonPath("priceList", Matchers.comparesEqualTo(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("startDate", Matchers.comparesEqualTo("2020-06-14T00:00:00")))
		.andExpect(MockMvcResultMatchers.jsonPath("endDate", Matchers.comparesEqualTo("2020-12-31T23:59:59")))
		.andExpect(MockMvcResultMatchers.jsonPath("priority", Matchers.comparesEqualTo(0)))
		.andExpect(MockMvcResultMatchers.jsonPath("price", Matchers.comparesEqualTo(35.50)))
		.andExpect(MockMvcResultMatchers.jsonPath("curr", Matchers.comparesEqualTo("EUR")));
	}
	
	@Test
	public void shouldReturnStatus400_whenSearchParametersNoSent() throws Exception {
		// Given
		final Integer brandId = 2;
		final var searchParams = PriceSearchDto.builder()
				.date(LocalDateTime.of(2020, 6, 14, 19, 05))
				.productId(null)
				.build();
		Mockito.when(this.priceListService.findPrice(searchParams.getDate(), searchParams.getProductId(), brandId))
		.thenReturn(Optional.empty());
		
		// When_Then
		this.mockMvc.perform(MockMvcRequestBuilders.get("/prices/{brandId}", brandId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.asJsonString(searchParams)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Required params not found."));
	}
	
	@Test
	public void shouldReturnStatus404_whenPriceIsNotFound() throws Exception {
		// Given
		final Integer brandId = 2;
		final var searchParams = PriceSearchDto.builder()
				.date(LocalDateTime.of(2020, 6, 14, 19, 05))
				.productId(35455)
				.build();
		Mockito.when(this.priceListService.findPrice(searchParams.getDate(), searchParams.getProductId(), brandId))
		.thenReturn(Optional.empty());
		
		// When_Then
		this.mockMvc.perform(MockMvcRequestBuilders.get("/prices/{brandId}", brandId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.asJsonString(searchParams)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().string("Price not found."));
	}
	
	@Test
	public void shouldReturnStatus500_whenUnexpextedError() throws Exception {
		// Given
		final Integer brandId = 2;
		final var searchParams = PriceSearchDto.builder()
				.date(LocalDateTime.of(2020, 6, 14, 19, 05))
				.productId(35455)
				.build();
		Mockito.when(this.priceListService.findPrice(searchParams.getDate(), searchParams.getProductId(), brandId))
		.thenThrow(new NullPointerException());
		
		// When_Then
		this.mockMvc.perform(MockMvcRequestBuilders.get("/prices/{brandId}", brandId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.asJsonString(searchParams)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isInternalServerError())
		.andExpect(MockMvcResultMatchers.content().string("Unexpected API error."));
	}
	
	/**
	 * Convert an object in JSON as String.
	 * @param obj The original object.
	 * @return The JSON representation of the object as String.
	 * */
	 
	private String asJsonString(final Object obj) {
		try {
			final var objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			return objectMapper.writeValueAsString(obj);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
