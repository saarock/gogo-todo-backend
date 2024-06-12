//package gogo.com.gogo_kan.controllers;
//
//
//import gogo.com.gogo_kan.services.impl.kafka.KafkaServiceImplement;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/product")
//public class Test {
//    @Autowired
//    private KafkaServiceImplement kafkaServiceImplement;
//
//    public ResponseEntity<?> updateLocation() {
//        this.kafkaServiceImplement.createProduct("("+ Math.round(Math.random() * 100) +", " + Math.round(Math.random() * 100) +", " + "+" );
//        return new ResponseEntity<>(Map.of("Message ", "Product created"), HttpStatus.OK);
//    }
//}
