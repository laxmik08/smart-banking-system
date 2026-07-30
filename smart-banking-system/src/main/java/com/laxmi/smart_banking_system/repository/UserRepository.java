package com.laxmi.smart_banking_system.repository;

import com.laxmi.smart_banking_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByAccountNumber(String accountNumber);


}
