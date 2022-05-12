package com.usermanagement.usermanagement.repositories;

import com.usermanagement.usermanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
