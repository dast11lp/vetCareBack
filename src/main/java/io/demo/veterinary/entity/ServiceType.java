package io.demo.veterinary.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity(name = "service_type")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_serv_type")
    private Long id;

    @Column(name = "nombre_serv_type")
    private String name;

    @Column(name = "descripcion_serv_type")
    private String description;

    @Column(name = "duracion_min_serv_type")
    private Integer durationMinutes;

    @Column(name = "precio_base_serv_type")
    private Double basePrice;

    // Qué tipo de especialista atiende este servicio (ej: groomer, veterinario)
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_spec_type")
    private SpecialistType requiredSpecialistType;

    public ServiceType() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public Double getBasePrice() { return basePrice; }
    public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }

    public SpecialistType getRequiredSpecialistType() { return requiredSpecialistType; }
    public void setRequiredSpecialistType(SpecialistType requiredSpecialistType) { this.requiredSpecialistType = requiredSpecialistType; }
}
