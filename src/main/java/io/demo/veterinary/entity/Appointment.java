package io.demo.veterinary.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity(name = "cita")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cit")
    private Long id;

    @Column(name = "fecha_cit")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "hora_cit")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime hour;

    @Column(name = "consultorio_cit")
    private Integer office;

    @Column(name = "monto_cit")
    private Double amount;

    @Column(name = "procedimiento_cit")
    private String procedure;

    @Column(name = "descripcion_cit")
    private String description;

    @Column(name = "receta_cit")
    private String prescription;

    @Column(name = "estado_cit")
    private String status; // DISPONIBLE, RESERVADA, COMPLETADA, CANCELADA

    @JsonIgnoreProperties({"appointments", "handler", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_specialist")
    private Specialist specialist;

    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_serv_type")
    private ServiceType serviceType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mas")
    private Pet pet;

    public Appointment() {}

    public Appointment(Long id, LocalDate date, LocalTime hour, Integer office, Double amount,
                       String procedure, String description, String prescription,
                       String status, Specialist specialist, ServiceType serviceType, Pet pet) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.office = office;
        this.amount = amount;
        this.procedure = procedure;
        this.description = description;
        this.prescription = prescription;
        this.status = status;
        this.specialist = specialist;
        this.serviceType = serviceType;
        this.pet = pet;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getHour() { return hour; }
    public void setHour(LocalTime hour) { this.hour = hour; }

    public Integer getOffice() { return office; }
    public void setOffice(Integer office) { this.office = office; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getProcedure() { return procedure; }
    public void setProcedure(String procedure) { this.procedure = procedure; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Specialist getSpecialist() { return specialist; }
    public void setSpecialist(Specialist specialist) { this.specialist = specialist; }

    public ServiceType getServiceType() { return serviceType; }
    public void setServiceType(ServiceType serviceType) { this.serviceType = serviceType; }

    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }
}
