package io.demo.veterinary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.demo.veterinary.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
