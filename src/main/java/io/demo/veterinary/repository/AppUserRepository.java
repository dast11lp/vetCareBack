package io.demo.veterinary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.demo.veterinary.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
		AppUser findByEmail(String email);
}
