package com.pabloandrade.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pabloandrade.cursomc.domain.Estado;
import com.pabloandrade.cursomc.dto.EstadoDTO;
import com.pabloandrade.cursomc.repositories.EstadoRepository;
import com.pabloandrade.cursomc.services.exceptions.DataIntegrityException;
import com.pabloandrade.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;

	public Estado find(Integer id) {
		Optional<Estado> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado: " + id + ", Tipo: " + Estado.class.getName()));
	}

	@Transactional
	public Estado insert(Estado obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	@Transactional
	public Estado update(Estado obj) {
		Estado newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	@Transactional
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um Estado que possui relacionamentos");
		}
	}

	public List<Estado> findAll() {
		return repo.findAll();
	}

	public Page<Estado> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return repo.findAll(pageRequest);
	}

	public Estado fromDTO(EstadoDTO dto) {
		return new Estado(dto.getId(), dto.getNome());
	}

	private void updateData(Estado newObj, Estado obj) {
		newObj.setNome(obj.getNome());
	}
}
