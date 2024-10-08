package com.spring.chatApp.data.repository;

import com.spring.chatApp.data.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional
public interface AdminRepository extends JpaRepository<Authorities, UUID> {


    Authorities findAuthoritiesByUserId(UUID username);

}
