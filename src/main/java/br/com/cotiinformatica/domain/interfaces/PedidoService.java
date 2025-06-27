package br.com.cotiinformatica.domain.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cotiinformatica.domain.dtos.requests.PedidoRequest;
import br.com.cotiinformatica.domain.dtos.responses.PedidoResponse;

public interface PedidoService {

	PedidoResponse criar(PedidoRequest request);
	
	PedidoResponse alterar(UUID uuid, PedidoRequest request);
	
	PedidoResponse excluir(UUID uuid);
	
	Page<PedidoResponse> consultar(Pageable pageable);
	
	PedidoResponse obter(UUID uuid);
}
