package io.demo.veterinary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.demo.veterinary.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long>{

}
