package io.demo.veterinary.entity;

import java.util.List;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity(name = "specialist")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Specialist extends AppUser {

    @Id
    @Column(name = "id_users")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_spec_type")
    private SpecialistType specialistType;

    @JsonIgnoreProperties({"specialist", "handler", "hibernateLazyInitializer"})
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "specialist")
    private List<Appointment> appointments;

    public Specialist() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SpecialistType getSpecialistType() { return specialistType; }
    public void setSpecialistType(SpecialistType specialistType) { this.specialistType = specialistType; }

    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
}
