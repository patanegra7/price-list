package dme.ecommerce.prices.app.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto with parameters of prices search.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceSearchDto {
	private LocalDateTime date;
	private Integer productId;
}
