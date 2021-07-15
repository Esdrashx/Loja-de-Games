package com.gameStore.minhaGameStore.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gameStore.minhaGameStore.model.Usuario;
import com.gameStore.minhaGameStore.repository.UsuarioRepository;
import com.gameStore.minhaGameStore.security.UserDetailsImplements;

@Service
public class UserDetailsServiceImplements implements UserDetailsService {

	@Autowired
	private UsuarioRepository repositoryU;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> user = repositoryU.findByEmail(email);
		user.orElseThrow(() -> new UsernameNotFoundException (email + "not found."));
		
		return user.map(UserDetailsImplements::new).get();
		
	}

	
	
}
