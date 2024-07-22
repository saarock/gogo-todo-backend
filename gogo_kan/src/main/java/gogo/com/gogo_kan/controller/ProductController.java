package gogo.com.gogo_kan.controller;


import gogo.com.gogo_kan.dto.GlobalSuccessResponse;
import gogo.com.gogo_kan.dto.request.CreateProductRequest;
import gogo.com.gogo_kan.dto.request.DeleteProductRequest;
import gogo.com.gogo_kan.dto.request.UpdateProductRequest;
import gogo.com.gogo_kan.dto.response.ErrorResponse;
import gogo.com.gogo_kan.dto.response.ProductResponse;
import gogo.com.gogo_kan.exception.*;
import gogo.com.gogo_kan.model.Product;
import gogo.com.gogo_kan.model.User;
import gogo.com.gogo_kan.service.ProductService;
import gogo.com.gogo_kan.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;



    @PostMapping("/create-new-product")
    public Object createNewProduct(@RequestBody CreateProductRequest productRequest) {

        try {
            System.out.println("Creating new Product");

            if (productRequest == null || productRequest.getName().isEmpty() || productRequest.getUserId() <= -1) {
                throw new InvalidException("Invalid product request data");
            }

            User user = userService.findById(productRequest.getUserId());

            if (user == null) {
                throw new UserNotFoundException("User not found");
            }

            String productName = productRequest.getName();
            int userId = productRequest.getUserId();
            if (productName.isEmpty()) {
                throw new ProductException("ProductName required");
            }

            int productIndex= productRequest.getIndex();

            //Improve code here more;
            boolean isExist = productService.findByProductNameAndUser(productName, userId);
            if (isExist) {
                System.out.println("hahaahhaha product already exist");
                throw new ProductException("Product Already exist");
            }

            Product product = new Product();
            product.setName(productName);
            product.setProductUser(user);
            product.setIndex(productIndex);

            Product savedProduct = productService.createNewProduct(product);
            if (savedProduct == null) {
                throw new ProductException("Cannot created Product");
            }

            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(String.valueOf(product.getId()));
            productResponse.setName(product.getName());
            if (product.getBoards() == null)  {
                productResponse.setBoards(new ArrayList<>());
            } else {
                productResponse.setBoards(product.getBoards()); // Set boards accordingly
            }
            productResponse.setIndex(product.getIndex());
            productResponse.setCreatedAt(String.valueOf(product.getCreatedDate()));
            productResponse.setUpdatedAt(String.valueOf(product.getUpdatedAt()));
            productResponse.setUserId(String.valueOf(product.getProductUser().getId()));



            return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "Product created successfully", productResponse);
        } catch (Exception e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", e.getMessage());

        }
    }


    @PutMapping("/update-product/{id}")
    public Object updateProductName(@PathVariable int id, @RequestBody UpdateProductRequest updateProductRequest) {
        try {
            String productName = updateProductRequest.getProductName();
            if (productName.isEmpty()) {
                throw new ProductNameRequired("Product Name Required");
            }



            Product product = productService.updateProduct(id, productName);

            if (product == null) {
                throw new ProductException("Product Not update Exception");
            }


            return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "Product updated Successfully", product);

        } catch (Exception e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", e.getMessage());
        }
    }

    @DeleteMapping("/delete-product/{id}")
    public Object deleteProduct(@PathVariable int id) {
        System.out.println(id + "test test ***");
        boolean isDeleted = this.productService.deleteProduct(id);
        if (isDeleted) {
            return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "Product Deleted Successfullt", null);
        } else {
            throw new ProductException("Can't deleted the product");
        }
    }


    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class UserIdRequest {
        private int userId;
    }



    @GetMapping("/get-products")
    public Object getProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "0") int size, @RequestParam(value = "sortBy",
            defaultValue = "updatedAt")String sortBy,
                              @RequestParam(value = "userId")int userId,  @RequestParam("direction") String direction) {
        try {
            if (userId <= -1) {
                throw new UserDetailsRequriedException("User id is less than 1");
            }

            Page<Product> productPage = productService.getProductByUser(userId, page, size, sortBy, direction);
            return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "Product created successfully", productPage);
        } catch (Exception e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", e.getMessage());

        }
    }


    @GetMapping("/search")
    public Object searchProduct(@RequestParam(value = "userId") int userId, @RequestParam(value = "productName") String productName) {
        try {
            System.out.println("***************************** ");
            System.out.println(userId);
            System.out.println(productName);
            List<Product> products = productService.searchProductByName(productName, userId);
            if (!products.isEmpty()) {
                System.out.println("this are the procuits ***************");
                for (Product p : products) {
                    System.out.println(p.getName());
                }
                return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "Search product", products);
            } else {
                return new ErrorResponse(HttpStatus.NOT_FOUND, "error", "Product Not found");

            }

        } catch (Exception e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", e.getMessage());

        }
    }

    @PutMapping("/modified-product/{id}")
    public Object modifiedProduct(@PathVariable int id)  {
        Product newProduct = productService.findProductByProductId(id);
        if (newProduct == null) {
            throw new ProductException("Product Not found");
        }

        Product newProductJustSaved = productService.createNewProduct(newProduct);
        if (newProductJustSaved == null) {
            throw new ProductException("Product Already exist");
        }
        return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "Product updated", newProductJustSaved);

    }
}





