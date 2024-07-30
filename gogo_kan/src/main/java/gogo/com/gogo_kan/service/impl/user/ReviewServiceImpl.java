package gogo.com.gogo_kan.service.impl.user;

import gogo.com.gogo_kan.model.Review;
import gogo.com.gogo_kan.repo.ReviewRepository;
import gogo.com.gogo_kan.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService  {
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public boolean saveReview(Review review) {
       try {
              this.reviewRepository.save(review);
           return true;
       } catch (Exception e) {
           return false;
       }
    }
}
