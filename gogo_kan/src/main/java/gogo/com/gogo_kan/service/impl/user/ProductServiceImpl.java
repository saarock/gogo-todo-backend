package gogo.com.gogo_kan.service.impl.user;

import gogo.com.gogo_kan.model.Product;
import gogo.com.gogo_kan.repo.ProductRepository;
import gogo.com.gogo_kan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createNewProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            System.out.println("Error while saving the product in the database: "+e.getMessage());
            return null;
        }
    }


}
