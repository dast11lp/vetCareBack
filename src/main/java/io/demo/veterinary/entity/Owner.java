package io.demo.veterinary.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity(name = "propietario")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Owner extends AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_users")
	private Long id;
	
	@JsonIgnoreProperties({"owner", "handler", "hibernateLazyInitializer"})
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "owner")
	private java.util.List<Pet> pets;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.util.List<Pet> getPets() {
		return pets;
	}

	public void setPets(java.util.List<Pet> pets) {
		this.pets = pets;
	}
}
