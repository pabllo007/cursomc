package com.pabloandrade.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pabloandrade.cursomc.domain.ItemPedido;
import com.pabloandrade.cursomc.domain.PagamentoComBoleto;
import com.pabloandrade.cursomc.domain.Pedido;
import com.pabloandrade.cursomc.domain.enums.EstadoPagamento;
import com.pabloandrade.cursomc.repositories.ItemPedidoRepository;
import com.pabloandrade.cursomc.repositories.PagamentoRepository;
import com.pabloandrade.cursomc.repositories.PedidoRepository;
import com.pabloandrade.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	ItemPedidoRepository itemPedidoRepository;

	public Pedido find(Integer id) {

		Optional<Pedido> obj = pedidoRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado: " + id + ", Tipo: " + Pedido.class.getName()));

	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}

		obj = pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}

		itemPedidoRepository.saveAll(obj.getItens());
		return obj;

	}
}
