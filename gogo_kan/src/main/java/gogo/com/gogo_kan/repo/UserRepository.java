package gogo.com.gogo_kan.repo;

import gogo.com.gogo_kan.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);


    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET email = :userEmail WHERE id = :userId", nativeQuery = true)
    User updateUserEmail(@Param("userId") long userId, @Param("userEmail") String userEmail);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET full_name = :userFullName WHERE email = :userEmail", nativeQuery = true)
    int updateUserFullName(@Param("userFullName") String userFullName, @Param("userEmail") String userEmail);
}
