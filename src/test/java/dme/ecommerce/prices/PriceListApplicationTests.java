package dme.ecommerce.prices;

import java.time.LocalDateTime;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dme.ecommerce.prices.app.dto.PriceSearchDto;

/**
 * Integration tests
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PriceListApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;

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
	
	@Test
	@DisplayName("Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
	void test1_productId35455_14_06_2020_10h() throws Exception {
		// Given
		final Integer brandId = 1;
		final var searchParams = PriceSearchDto.builder()
				.date(LocalDateTime.of(2020, 6, 14, 10, 00))
				.productId(35455)
				.build();
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
	@DisplayName("Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
	void test2_productId35455_14_06_2020_16h() throws Exception {
		// Given
		final Integer brandId = 1;
		final var searchParams = PriceSearchDto.builder()
				.date(LocalDateTime.of(2020, 6, 14, 16, 00))
				.productId(35455)
				.build();
		// When_Then
		this.mockMvc.perform(MockMvcRequestBuilders.get("/prices/{brandId}", brandId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.asJsonString(searchParams)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.comparesEqualTo(2)))
		.andExpect(MockMvcResultMatchers.jsonPath("brandId", Matchers.comparesEqualTo(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("productId", Matchers.comparesEqualTo(35455)))
		.andExpect(MockMvcResultMatchers.jsonPath("priceList", Matchers.comparesEqualTo(2)))
		.andExpect(MockMvcResultMatchers.jsonPath("startDate", Matchers.comparesEqualTo("2020-06-14T15:00:00")))
		.andExpect(MockMvcResultMatchers.jsonPath("endDate", Matchers.comparesEqualTo("2020-06-14T18:30:00")))
		.andExpect(MockMvcResultMatchers.jsonPath("priority", Matchers.comparesEqualTo(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("price", Matchers.comparesEqualTo(25.45)))
		.andExpect(MockMvcResultMatchers.jsonPath("curr", Matchers.comparesEqualTo("EUR")));
	}
		
	@Test
	@DisplayName("Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
	void test3_productId35455_14_06_2020_21h() throws Exception {
		// Given
		final Integer brandId = 1;
		final var searchParams = PriceSearchDto.builder()
				.date(LocalDateTime.of(2020, 6, 14, 21, 00))
				.productId(35455)
				.build();
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
	@DisplayName("Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)")
	void test4_productId35455_15_06_2020_10h() throws Exception {
		// Given
		final Integer brandId = 1;
		final var searchParams = PriceSearchDto.builder()
				.date(LocalDateTime.of(2020, 6, 15, 10, 00))
				.productId(35455)
				.build();
		// When_Then
		this.mockMvc.perform(MockMvcRequestBuilders.get("/prices/{brandId}", brandId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.asJsonString(searchParams)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.comparesEqualTo(3)))
		.andExpect(MockMvcResultMatchers.jsonPath("brandId", Matchers.comparesEqualTo(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("productId", Matchers.comparesEqualTo(35455)))
		.andExpect(MockMvcResultMatchers.jsonPath("priceList", Matchers.comparesEqualTo(3)))
		.andExpect(MockMvcResultMatchers.jsonPath("startDate", Matchers.comparesEqualTo("2020-06-15T00:00:00")))
		.andExpect(MockMvcResultMatchers.jsonPath("endDate", Matchers.comparesEqualTo("2020-06-15T11:00:00")))
		.andExpect(MockMvcResultMatchers.jsonPath("priority", Matchers.comparesEqualTo(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("price", Matchers.comparesEqualTo(30.50)))
		.andExpect(MockMvcResultMatchers.jsonPath("curr", Matchers.comparesEqualTo("EUR")));
	}
	
	@Test
	@DisplayName("Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)")
	void test5_productId35455_16_06_2020_21h() throws Exception {
		// Given
		final Integer brandId = 1;
		final var searchParams = PriceSearchDto.builder()
				.date(LocalDateTime.of(2020, 6, 16, 21, 00))
				.productId(35455)
				.build();
		// When_Then
		this.mockMvc.perform(MockMvcRequestBuilders.get("/prices/{brandId}", brandId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.asJsonString(searchParams)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.comparesEqualTo(4)))
		.andExpect(MockMvcResultMatchers.jsonPath("brandId", Matchers.comparesEqualTo(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("productId", Matchers.comparesEqualTo(35455)))
		.andExpect(MockMvcResultMatchers.jsonPath("priceList", Matchers.comparesEqualTo(4)))
		.andExpect(MockMvcResultMatchers.jsonPath("startDate", Matchers.comparesEqualTo("2020-06-15T16:00:00")))
		.andExpect(MockMvcResultMatchers.jsonPath("endDate", Matchers.comparesEqualTo("2020-12-31T23:59:59")))
		.andExpect(MockMvcResultMatchers.jsonPath("priority", Matchers.comparesEqualTo(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("price", Matchers.comparesEqualTo(38.95)))
		.andExpect(MockMvcResultMatchers.jsonPath("curr", Matchers.comparesEqualTo("EUR")));
	}
}
