package io.demo.veterinary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.demo.veterinary.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long>{

}
