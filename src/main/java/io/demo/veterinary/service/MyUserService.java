package io.demo.veterinary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.demo.veterinary.entity.AppUser;
import io.demo.veterinary.repository.AppUserRepository;

@Service
public class MyUserService extends BaseService<AppUser, AppUserRepository>{
    @Autowired
    AppUserRepository userRepository;
    public AppUser findByEmail (String email) {
        return this.userRepository.findByEmail(email);
    }
}
