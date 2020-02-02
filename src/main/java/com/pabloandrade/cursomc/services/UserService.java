package com.pabloandrade.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.pabloandrade.cursomc.security.UserSS;
import com.pabloandrade.cursomc.services.exceptions.AuthorizationException;

public class UserService {
	
	public static UserSS authenticated() {
		try {
		return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new AuthorizationException("Acesso negado!");
		}
	}

}
