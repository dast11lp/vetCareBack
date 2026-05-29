package io.demo.veterinary.repository;

import io.demo.veterinary.entity.SpecialistType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialistTypeRepository extends JpaRepository<SpecialistType, Long> {
}
