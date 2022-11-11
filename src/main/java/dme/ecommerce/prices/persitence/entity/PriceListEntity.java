package dme.ecommerce.prices.persitence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Database ORM price_list entity.
 */
@Entity(name="price_list")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListEntity {
	@Id
	private Integer id;
	private Integer brandId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Integer priceList;
	private Integer productId;
	private Integer priority;
	private BigDecimal price;
	private String curr;
}
