package gogo.com.gogo_kan.exception;

public class ProductNameExist extends RuntimeException {
    public ProductNameExist(String message) {
        super(message);
    }
}
