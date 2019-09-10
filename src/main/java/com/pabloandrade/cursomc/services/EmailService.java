package com.pabloandrade.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.pabloandrade.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
