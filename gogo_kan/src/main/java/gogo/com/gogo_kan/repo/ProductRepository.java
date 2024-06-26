package gogo.com.gogo_kan.repo;

import gogo.com.gogo_kan.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
//public interface ProductRepository extends JpaRepository<Product, Integer> {
////    @Query(value = "SELECT p FROM Product p ORDER BY p.id")
////    List<Product> findProducts(int offset, int pageSize);
//
//    Page<Product> findByUserId(User user, Pageable);
//}
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByProductUserId(int userId, Pageable pageable);
    boolean existsByNameAndProductUser_Id(String productName, int userId);
//    Product findByProductId(int productId);
}
