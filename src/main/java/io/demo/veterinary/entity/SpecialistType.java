package io.demo.veterinary.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity(name = "specialist_type")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpecialistType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_spec_type")
    private Long id;

    @Column(name = "nombre_spec_type")
    private String name;

    @Column(name = "descripcion_spec_type")
    private String description;

    public SpecialistType() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
