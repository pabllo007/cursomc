package com.pabloandrade.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pabloandrade.cursomc.domain.Cliente;
import com.pabloandrade.cursomc.domain.enums.TipoCliente;
import com.pabloandrade.cursomc.dto.ClienteNewDTO;
import com.pabloandrade.cursomc.repositories.ClienteRepository;
import com.pabloandrade.cursomc.resources.exceptions.FieldMessage;
import com.pabloandrade.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository clienteRepository; 
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		 if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && ! BR.isValidCPF(objDto.getCpfOuCnpj())) {
			 list.add(new FieldMessage("cpfOuCnpj","CPF inválido"));
		 }

		 if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && ! BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			 list.add(new FieldMessage("cpfOuCnpj","CNPJ inválido"));
		 }
		 
		 Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
		 if (aux != null) {
			 list.add(new FieldMessage("email", "E-mail já cadastrado"));
		 }

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}