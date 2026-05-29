package io.demo.veterinary.service;

import io.demo.veterinary.entity.Appointment;
import io.demo.veterinary.entity.ServiceType;
import io.demo.veterinary.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService extends BaseService<Appointment, AppointmentRepository> {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> findAvailableByServiceType(ServiceType serviceType) {
        return this.appointmentRepository.findByServiceTypeAndPetIsNull(serviceType);
    }

    public List<Appointment> findAvailableByServiceTypeAndDate(ServiceType serviceType, LocalDate date) {
        return this.appointmentRepository.findByServiceTypeAndDateAndPetIsNull(serviceType, date);
    }
}
