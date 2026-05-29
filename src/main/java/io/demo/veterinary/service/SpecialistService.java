package io.demo.veterinary.service;

import io.demo.veterinary.entity.Specialist;
import io.demo.veterinary.repository.SpecialistRepository;
import org.springframework.stereotype.Service;

@Service
public class SpecialistService extends BaseService<Specialist, SpecialistRepository> {
}
