package io.demo.veterinary.controller;

import io.demo.veterinary.entity.SpecialistType;
import io.demo.veterinary.service.SpecialistTypeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("specialist-type")
@CrossOrigin({"*"})
public class SpecialistTypeController extends BaseController<SpecialistType, SpecialistTypeService> {
}
