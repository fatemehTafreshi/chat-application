package com.spring.chatApp.data.repository;

import com.spring.chatApp.data.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Authorities, UUID> {


    Authorities findAuthoritiesByUserId(UUID username);

}
