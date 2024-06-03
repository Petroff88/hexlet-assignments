package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import exercise.model.Product;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findByPrice(int price);

//    @Query(value = "SELECT * FROM PRODUCTS WHERE price>=?1 AND price<=?2", nativeQuery = true)
//    List<Product> findMinMaxPrices(int min, int max);

    List<Product> findByPriceGreaterThan(int min, Sort sort);

    List<Product> findByPriceLessThan(int max, Sort sort);

    List<Product> findByPriceGreaterThanAndPriceLessThan(int min, int max, Sort sort);
    // END
}
