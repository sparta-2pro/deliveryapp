package com.twopro.deliveryapp.user.repository;

import com.twopro.deliveryapp.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
