package io.demo.veterinary.controller;

import io.demo.veterinary.entity.Specialist;
import io.demo.veterinary.service.SpecialistService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("specialist")
@CrossOrigin({"*"})
public class SpecialistController extends BaseController<Specialist, SpecialistService> {
}
