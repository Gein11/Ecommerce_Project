package com.project.shopapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.shopapp.models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
    //SELECT * FROM users WHERE phoneNumber=?

    @Query("SELECT u FROM User u JOIN u.role o WHERE u.active = true AND (:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(u.fullName) LIKE %:keyword% OR LOWER(u.address) LIKE %:keyword% OR LOWER(u.phoneNumber) LIKE %:keyword%) AND LOWER(o.name) = 'user'")
    Page<User> findAll(@Param("keyword") String keyword, Pageable pageable) ;
}

