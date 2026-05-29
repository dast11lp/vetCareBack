package io.demo.veterinary.service;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.demo.veterinary.entity.AppUser;
import io.demo.veterinary.entity.Role;
import io.demo.veterinary.repository.AppUserRepository;
@Component
public class MyUserDetailService implements UserDetailsService {
	
	
	@Autowired
	private AppUserRepository userSer;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		AppUser user = this.userSer.findByEmail(email);
		
		if (user == null)
			throw new UsernameNotFoundException("the user with the email: " + email + "was not found");
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for (Role role: user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		
		return new User(email, user.getPassword(), authorities);
	}

}
