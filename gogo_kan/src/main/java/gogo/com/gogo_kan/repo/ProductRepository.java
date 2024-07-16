package gogo.com.gogo_kan.repo;

import gogo.com.gogo_kan.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByProductUserId(int userId, Pageable pageable);
    boolean existsByNameAndProductUser_Id(String productName, int userId);
    @Query("SELECT p FROM Product p WHERE p.productUser.id = :userId AND p.name LIKE CONCAT(:productName, '%')")
    List<Product> findByProductUserIdAndNameStartingWith(@Param("userId") int userId, @Param("productName") String productName);
//    Product findByProductId(int productId);
}
