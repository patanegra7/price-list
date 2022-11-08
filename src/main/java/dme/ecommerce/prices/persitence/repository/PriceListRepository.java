package dme.ecommerce.prices.persitence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dme.ecommerce.prices.persitence.entity.PriceListEntity;

/**
 * Repository interface to manage {@link PriceListEntity} persistence.
 */
@Repository
public interface PriceListRepository extends CrudRepository<PriceListEntity, Integer> {
	
	@Query("SELECT p FROM price_list p WHERE (:date BETWEEN p.startDate AND p.endDate) AND p.productId = :productId AND p.brandId = :brandId order by priority DESC")
	List<PriceListEntity> findPrice(LocalDateTime date, Integer productId, Integer brandId);
}
