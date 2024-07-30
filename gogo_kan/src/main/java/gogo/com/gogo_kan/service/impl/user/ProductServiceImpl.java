package gogo.com.gogo_kan.service.impl.user;

import gogo.com.gogo_kan.exception.ProjectNameNotFoundException;
import gogo.com.gogo_kan.model.Product;
import gogo.com.gogo_kan.repo.ProductRepository;
import gogo.com.gogo_kan.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.ProviderNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createNewProduct(Product product) {
        try {
            product.setName(product.getName());
            return productRepository.save(product);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Product> getProducts(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return null;
    }

    @Override
    public Page<Product> getProductByUser(int userID, int page, int size, String sortBy, String direction) {
        try {
            Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            var pageable = PageRequest.of(page, size, sort);
            System.out.println("haha");
            return productRepository.findByProductUserId(userID, pageable);
        } catch (Exception e) {
            // have to give the proper name
            throw new ProjectNameNotFoundException("Can't fetch userProducts : " + e.getMessage());
        }
    }

    @Override
    public boolean findByProductNameAndUser(String productName, int userId) {
//        return productRepository.existsByProductName(name);
        return this.productRepository.existsByNameAndProductUser_Id(productName, userId);
    }

    @Override
    public Product findProductByProductId(int id) {
        return productRepository.findById(id).orElseThrow(() -> new ProviderNotFoundException("Product Not Found!"));
    }

    @Override
    public Product updateProduct(int productId, String productName) {
        try {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                Product getProduct = product.get();
                getProduct.setName(productName);
                productRepository.save(getProduct);
                return getProduct;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean deleteProduct(int productId) {
        try {
            this.productRepository.deleteById(productId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Product> searchProductByName(String productName, int userId) {
        try {
            return productRepository.findByProductUserIdAndNameStartingWith(userId, productName);
        } catch (Exception e) {
            return null;
        }
    }

}
