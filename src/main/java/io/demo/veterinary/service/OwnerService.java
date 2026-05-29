package io.demo.veterinary.service;

import io.demo.veterinary.entity.AppUser;
import io.demo.veterinary.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.demo.veterinary.entity.Owner;
import io.demo.veterinary.repository.OwnerRepository;

@Service
public class OwnerService extends BaseService<Owner, OwnerRepository>{
}
