package io.demo.veterinary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.demo.veterinary.entity.AppUser;
import io.demo.veterinary.entity.Owner;
import io.demo.veterinary.entity.Pet;
import io.demo.veterinary.entity.Role;
import io.demo.veterinary.jwt.JwtUtil;
import io.demo.veterinary.service.MyUserService;
import io.demo.veterinary.service.OwnerService;
import io.demo.veterinary.service.RoleService;

@RestController
@RequestMapping("owner")
@CrossOrigin("*")
public class OwnerController extends BaseController<Owner, OwnerService>{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired 
	private OwnerService ownerService;
	
	@Autowired
	private MyUserService appUserService;
	


	@PostMapping("register")
	public ResponseEntity<?> create(@RequestBody Owner owner) throws Exception {

		AppUser user = this.appUserService.findByEmail(owner.getEmail());

		if(user != null)
			return ResponseEntity.badRequest().body("The user already exists");

		if(owner.getPassword() == null)
			return ResponseEntity.badRequest().body("missing password");
		
		owner.setPassword(passwordEncoder.encode(owner.getPassword()));
		
		this.ownerService.save(owner);
		
		Role role = new Role();
		
		role.setRole("ROLE_USER");
		
		role.getUsers().add(owner);

		this.roleService.save(role);
		
		String token = this.jwtUtil.jwtCreator(owner.getEmail(), owner.getId());
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.set("Authorization", token);
		
		Map<String, String> tokens = new HashMap<>();
		tokens.put("Authorization", token);
		
		return  ResponseEntity.ok().headers(headers).body(tokens);
	}


	@GetMapping("/pet-list")
	@PreAuthorize("@securityService.hasUser(#idUser)")
	public ResponseEntity<List<Pet>> petList(@RequestParam Long idUser) {
		return ResponseEntity.ok(this.ownerService.findById(idUser).getPets());
	}
	
	@GetMapping("listOwner")
	public ResponseEntity<List<Owner>> ownerList() {
		return ResponseEntity.ok(this.ownerService.findAll());
	}
	
	@GetMapping("listUsers")
	public ResponseEntity<List<AppUser>> appList() {
		return ResponseEntity.ok(this.appUserService.findAll());
	}
	
}
