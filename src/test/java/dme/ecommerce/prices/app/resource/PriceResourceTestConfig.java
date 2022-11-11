package dme.ecommerce.prices.app.resource;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures beans needed in {@link PriceResourceTest}
 */
@Configuration
public class PriceResourceTestConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
