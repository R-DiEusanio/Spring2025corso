package com.example.corso.repository.authRepository;

import com.example.corso.entitySecurity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

   Optional<Users> findByUsername(String username);
   Boolean existsByUsername(String username);
}
