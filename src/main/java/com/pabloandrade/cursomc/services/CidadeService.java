package com.pabloandrade.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pabloandrade.cursomc.domain.Cidade;
import com.pabloandrade.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repo;

	public List<Cidade> findCidade(Integer estadoID) {
		return repo.findCidades(estadoID);
	}

	
}
