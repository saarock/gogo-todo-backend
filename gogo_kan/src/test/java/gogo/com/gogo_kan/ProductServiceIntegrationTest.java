package gogo.com.gogo_kan;

import gogo.com.gogo_kan.model.Product;
import gogo.com.gogo_kan.repo.ProductRepository;
import gogo.com.gogo_kan.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @Test
    public void createNewProduct_ShouldReturnProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setIndex(1);
        Product createdProduct = productService.createNewProduct(product);
        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
    }

    @Test
    public void findByProductNameAndUser_ShouldReturnTrueIfProductExists() {
        boolean exists = productService.findByProductNameAndUser("Hello World", 1);
        assertTrue(exists);
    }

    @Test
    public void findByProductNameAndUser_ShouldReturnFalseIfProductDoesNotExist() {
        boolean exists = productService.findByProductNameAndUser("Non-Existing Product", 1);
        assertFalse(exists);
    }

    @Test
    public void findProductByProductId_ShouldReturnProductIfExists() {
        Product product = new Product();
        product.setName("Product by ID");
        product.setIndex(1);
        Product createdProduct = productService.createNewProduct(product);

        Product foundProduct = productService.findProductByProductId(createdProduct.getId());
        assertNotNull(foundProduct);
        assertEquals(createdProduct.getId(), foundProduct.getId());
    }
    @Test
    public void findProductByProductId_ShouldReturnNullIfProductDoesNotExist() {
        Product foundProduct = productService.findProductByProductId(9999); // Assuming this ID does not exist
        assertNull(foundProduct);
    }

    @Test
    public void getProductByUser_ShouldReturnPageOfProducts() {
        Page<Product> productPage = productService.getProductByUser(1, 0, 3, "name", "asc");
        assertNotNull(productPage);
        assertTrue(productPage.hasContent());
    }

    @Test
    public void updateProduct_ShouldReturnUpdatedProduct() {
        Product product = new Product();
        product.setName("Old Product");
        product.setIndex(1);
        Product createdProduct = productService.createNewProduct(product);

        Product updatedProduct = productService.updateProduct(createdProduct.getId(), "Updated Product");
        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
    }

    @Test
    public void deleteProduct_ShouldRemoveProduct() {
        Product product = new Product();
        product.setName("Product to delete");
        product.setIndex(1);
        Product createdProduct = productService.createNewProduct(product);

        boolean isDeleted = productService.deleteProduct(createdProduct.getId());
        assertTrue(isDeleted);
        assertFalse(productRepository.existsById(createdProduct.getId()));
    }

    @Test
    public void searchProductByName_ShouldReturnListOfProducts() {
        Product product1 = new Product();
        product1.setName("Search Product1234");
        product1.setIndex(1);
        productService.createNewProduct(product1);

        Product product2 = new Product();
        product2.setName("Another Product2344");
        product2.setIndex(2);
        productService.createNewProduct(product2);

        List<Product> results = productService.searchProductByName("Search Product123", 1);
        assertNotNull(results);
    }
}
