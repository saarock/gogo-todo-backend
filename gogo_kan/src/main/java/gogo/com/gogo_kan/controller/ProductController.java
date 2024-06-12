package gogo.com.gogo_kan.controller;


import gogo.com.gogo_kan.dto.GlobalSuccessResponse;
import gogo.com.gogo_kan.dto.request.CreateProductRequest;
import gogo.com.gogo_kan.dto.request.DeleteProductRequest;
import gogo.com.gogo_kan.dto.request.UpdateProductRequest;
import gogo.com.gogo_kan.dto.response.ErrorResponse;
import gogo.com.gogo_kan.model.Product;
import gogo.com.gogo_kan.model.User;
import gogo.com.gogo_kan.service.ProductService;
import gogo.com.gogo_kan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;




    @PostMapping("/create-new-product")
    public Object createNewProduct(@RequestBody CreateProductRequest productRequest) {
        try {
            if (productRequest == null || productRequest.getName().isEmpty() || productRequest.getUserId() <= -1) {
                throw new IllegalArgumentException("Invalid product request data");
            }

            User user = userService.findById(productRequest.getUserId());

            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }

            Product product = new Product();
            product.setProductName(productRequest.getName());
//            product.setIndex(productRequest.getProductIndex());
            product.setProductUser(user);
            Product savedProduct = productService.createNewProduct(product);
            if (savedProduct == null) {
                return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", "Product Created UnSuccessFUll");

            }
            return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "Product created successfully", savedProduct);
        } catch (IllegalArgumentException e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", e.getMessage());
        } catch (Exception e) {
            return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error", "An unexpected error occurred");
        }
    }



    @PutMapping("/update-product/{id}")
    public Object updateProductName(@PathVariable long id ,@RequestBody UpdateProductRequest updateProductRequest) {
        return  null;
    }

    @DeleteMapping("/delete-product/{id}")
    public Object deleteProduct(@PathVariable long id,  @RequestBody DeleteProductRequest deleteProductRequest) {
        return null;
    }

    @GetMapping("get-products")
    public Object getProducts() {

    }



}
