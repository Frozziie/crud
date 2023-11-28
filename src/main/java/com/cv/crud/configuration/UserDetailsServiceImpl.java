package com.cv.crud.configuration;

import com.cv.crud.entity.Privilege;
import com.cv.crud.entity.Role;
import com.cv.crud.entity.UserEntity;
import com.cv.crud.repository.RoleRepository;
import com.cv.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository rolRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity user = userRepository.findByEmail(email);

		if (user == null) {
			return new org.springframework.security.core.userdetails.User(" ", " ",
					true, true, true, true,
					getAuthorities(Collections.singletonList(rolRepository.findByName("ROLE_USER"))));
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				true, true, true, true,
				getAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {

		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(Collection<Role> roles) {

		List<String> privileges = new ArrayList<>();
		List<Privilege> rolPrivileges = new ArrayList<>();

		roles.forEach(role -> {
			privileges.add(role.getName());
			rolPrivileges.addAll(role.getPrivileges());
		});

		rolPrivileges.forEach(rolPrivilege -> privileges.add(rolPrivilege.getName()));

		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {

		List<GrantedAuthority> authorities = new ArrayList<>();
		privileges.forEach(privilege -> authorities.add(new SimpleGrantedAuthority(privilege)));
		return authorities;
	}

}
