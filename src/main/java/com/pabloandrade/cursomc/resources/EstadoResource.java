package com.pabloandrade.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pabloandrade.cursomc.domain.Cidade;
import com.pabloandrade.cursomc.domain.Estado;
import com.pabloandrade.cursomc.dto.CidadeDTO;
import com.pabloandrade.cursomc.dto.EstadoDTO;
import com.pabloandrade.cursomc.services.CidadeService;
import com.pabloandrade.cursomc.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> list = service.findAll();
		List<EstadoDTO> dtos = list.stream().map(f -> new EstadoDTO(f)).collect(Collectors.toList());
		return ResponseEntity.ok().body(dtos);
	}

	@RequestMapping(value = "/{estadoId}/cidades", method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable("estadoId") Integer estadoId) {
		List<Cidade> list = cidadeService.findCidade(estadoId);
		List<CidadeDTO> dtos = list.stream().map(f -> new CidadeDTO(f)).collect(Collectors.toList());
		return ResponseEntity.ok().body(dtos);
	}
	
}
