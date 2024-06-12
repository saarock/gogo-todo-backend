//package gogo.com.gogo_kan.services.impl.kafka;
//
//import gogo.com.gogo_kan.config.AppConstants;
//import gogo.com.gogo_kan.models.Board;
//import gogo.com.gogo_kan.models.Product;
//import gogo.com.gogo_kan.models.Task;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaServiceImplement {
//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
//
//    public void sendProduct(Product product) {
//        kafkaTemplate.send("product-topic", product);
//    }
//
//    public void sendTask(Task task) {
//        kafkaTemplate.send("task-topic", task);
//    }
//
//    public void sendBoard(Board board) {
//        kafkaTemplate.send("board-topic", board);
//    }
//
//}
//
//
