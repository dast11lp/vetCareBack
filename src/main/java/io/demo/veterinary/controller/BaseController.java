package io.demo.veterinary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


import io.demo.veterinary.service.BaseService;


public abstract class BaseController <E, S extends BaseService<E, ?>>{

	@Autowired
	protected S service;
	
	@GetMapping("/details")
	public ResponseEntity<?> deteils(@RequestParam Long id){
		var service = this.service.findById(id);
		if (service == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(service);
	}
	
	@GetMapping("/list")
	@PreAuthorize("@securityService.hasUser(#idUser)")
	public ResponseEntity<List<E>> list(@RequestParam Long idUser) {
		return ResponseEntity.ok(this.service.findAll());
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> create (@RequestBody E entity) throws Exception {
		return  ResponseEntity.ok(this.service.save(entity));
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody E entity){
		return ResponseEntity.ok(null);
	}
		
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteById(@RequestAttribute Long id){
		return ResponseEntity.ok(this.service.deleteById(id));
	}

	
}
