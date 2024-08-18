package gogo.com.gogo_kan.service;


import gogo.com.gogo_kan.model.Product;
import gogo.com.gogo_kan.model.User;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ProductService {
    public Product createNewProduct(Product product);
    public Page<Product> getProductByUser(int userId, int page, int size, String sortBy, String direction);
    public boolean  findByProductNameAndUser(String name, int id);
    public Product findProductByProductId(int id);
    public Product updateProduct(int productId,  String productName);
    public boolean deleteProduct(int productId);
    public List<Product> searchProductByName(String productName, int userId);

}
