package io.demo.veterinary.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.demo.veterinary.entity.Pet;
import io.demo.veterinary.service.PetService;

@RestController
@RequestMapping("pet")
@CrossOrigin("*")
public class PetController extends BaseController<Pet, PetService> {
	
	@Autowired
	private PetService petService; 

	@PutMapping("/cancel")
	public void cancelAppointment(@RequestParam Long idPet, @RequestParam Long idAppointment, HttpServletResponse response) throws IOException {
		response.sendRedirect("/appointment/cancel/"+idPet+"/"+ idAppointment);
	}
	
	
	@GetMapping("/my-appointments/{idPet}")
	public ResponseEntity<?> myAppointments(@PathVariable Long idPet) throws IOException {
		return ResponseEntity.ok(this.petService.findById(idPet).getAppointments());
	}
	
}
