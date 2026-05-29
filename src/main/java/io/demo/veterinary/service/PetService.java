package io.demo.veterinary.service;

import org.springframework.stereotype.Service;

import io.demo.veterinary.entity.Pet;
import io.demo.veterinary.repository.PetRepository;

@Service
public class PetService extends BaseService<Pet, PetRepository>{

}
