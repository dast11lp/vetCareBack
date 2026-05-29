package io.demo.veterinary.repository;

import io.demo.veterinary.entity.Appointment;
import io.demo.veterinary.entity.Pet;
import io.demo.veterinary.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByPet(Pet pet);
    List<Appointment> findByServiceTypeAndPetIsNull(ServiceType serviceType);

    List<Appointment> findByServiceTypeAndDateAndPetIsNull(ServiceType serviceType, LocalDate date);
}
