package io.demo.veterinary.controller;

import java.time.LocalDate;
import java.util.*;

import io.demo.veterinary.entity.*;
import io.demo.veterinary.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("appointment")
@CrossOrigin({"*"})
public class AppointmentController extends BaseController<Appointment, AppointmentService> {

    @Autowired
    private PetService petService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    // ── Listar citas disponibles por tipo de servicio ──────────────────────────
    @PreAuthorize("@securityService.hasUser(#idUser)")
    @GetMapping("/dates")
    public ResponseEntity<?> listAppointDates(@RequestParam Long idUser,
                                               @RequestParam Long serviceTypeId) {
        AppUser user = this.myUserService.findById(idUser);
        if (user == null)
            return ResponseEntity.badRequest().body("Usuario no encontrado");

        ServiceType serviceType = this.serviceTypeService.findById(serviceTypeId);
        if (serviceType == null)
            return ResponseEntity.badRequest().body("Tipo de servicio no encontrado");

        List<Appointment> available = this.appointmentService.findAvailableByServiceType(serviceType);

        if (available.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No hay citas disponibles para este servicio");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(available);
    }

    @PreAuthorize("@securityService.hasUser(#idUser)")
    @GetMapping("/hours")
    public ResponseEntity<?> listAppointHours(@RequestParam Long idUser,
                                              @RequestParam Long serviceTypeId,
                                              @RequestParam String date) {
        ServiceType serviceType = this.serviceTypeService.findById(serviceTypeId);
        if (serviceType == null)
            return ResponseEntity.badRequest().body("Tipo de servicio no encontrado");

        LocalDate localDate = LocalDate.parse(date);
        List<Appointment> hours = this.appointmentService.findAvailableByServiceTypeAndDate(serviceType, localDate);

        if (hours.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No hay horas disponibles para esa fecha");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(hours);
    }


    // ── Reservar cita ──────────────────────────────────────────────────────────
    @PutMapping("/request")
    public ResponseEntity<?> requestAppointment(@RequestParam Long idPet,
                                                @RequestParam Long idAppointment) {
        Map<String, String> response = new HashMap<>();

        Pet pet = this.petService.findById(idPet);
        if (pet == null) {
            response.put("message", "La mascota no se encuentra en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Appointment appointment = this.appointmentService.findById(idAppointment);
        if (appointment == null) {
            response.put("message", "La cita no existe");
            return ResponseEntity.notFound().build();
        }

        if (!"DISPONIBLE".equals(appointment.getStatus())) {
            response.put("message", "La cita no está disponible");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        boolean duplicateService = pet.getAppointments().stream()
                .anyMatch(a -> a.getServiceType().getId().equals(appointment.getServiceType().getId()));

        if (duplicateService) {
            response.put("message", "La mascota ya tiene una cita de ese tipo de servicio");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        appointment.setPet(pet);
        appointment.setStatus("RESERVADA");

        return ResponseEntity.ok(this.appointmentService.save(appointment));
    }

    // ── Cancelar cita: usuario dueño de la mascota O admin ────────────────────
    @PreAuthorize("@securityService.hasUserOrAdmin(#idUser)")
    @PutMapping("/cancel")
    public ResponseEntity<?> cancelAppointment(@RequestParam Long idUser,
                                               @RequestParam Long idAppointment) {
        Map<String, String> response = new HashMap<>();

        Appointment appointment = this.appointmentService.findById(idAppointment);
        if (appointment == null)
            return ResponseEntity.notFound().build();

        if (!"RESERVADA".equals(appointment.getStatus())) {
            response.put("message", "Solo se pueden cancelar citas en estado RESERVADA");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        appointment.setPet(null);
        appointment.setStatus("DISPONIBLE");

        return ResponseEntity.ok(this.appointmentService.save(appointment));
    }

    // ── Marcar como COMPLETADA: solo admin ────────────────────────────────────
    @PreAuthorize("@securityService.isAdmin()")
    @PutMapping("/complete")
    public ResponseEntity<?> completeAppointment(@RequestParam Long idAppointment) {
        Map<String, String> response = new HashMap<>();

        Appointment appointment = this.appointmentService.findById(idAppointment);
        if (appointment == null)
            return ResponseEntity.notFound().build();

        if (!"RESERVADA".equals(appointment.getStatus())) {
            response.put("message", "Solo se pueden completar citas en estado RESERVADA");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        appointment.setStatus("COMPLETADA");

        return ResponseEntity.ok(this.appointmentService.save(appointment));
    }

    // ── Marcar como NO_ASISTIO: solo admin ────────────────────────────────────
    @PreAuthorize("@securityService.isAdmin()")
    @PutMapping("/no-asistio")
    public ResponseEntity<?> noAsistio(@RequestParam Long idAppointment) {
        Map<String, String> response = new HashMap<>();

        Appointment appointment = this.appointmentService.findById(idAppointment);
        if (appointment == null)
            return ResponseEntity.notFound().build();

        if (!"RESERVADA".equals(appointment.getStatus())) {
            response.put("message", "Solo se puede marcar NO_ASISTIO en citas RESERVADAS");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        appointment.setStatus("NO_ASISTIO");

        return ResponseEntity.ok(this.appointmentService.save(appointment));
    }

    // ── Eliminar cita: solo admin ──────────────────────────────────────────────
    @PreAuthorize("@securityService.isAdmin()")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteById(@RequestParam Long id) {
        return ResponseEntity.ok(this.appointmentService.deleteById(id));
    }
}
