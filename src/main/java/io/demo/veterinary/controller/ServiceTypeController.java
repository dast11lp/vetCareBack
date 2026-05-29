package io.demo.veterinary.controller;

import io.demo.veterinary.entity.ServiceType;
import io.demo.veterinary.service.ServiceTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("service-type")
@CrossOrigin({"*"})
public class ServiceTypeController extends BaseController<ServiceType, ServiceTypeService> {

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(this.service.findAll());
    }
}