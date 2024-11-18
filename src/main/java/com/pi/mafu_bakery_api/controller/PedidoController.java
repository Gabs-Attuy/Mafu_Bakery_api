package com.pi.mafu_bakery_api.controller;

import com.pi.mafu_bakery_api.dto.CriacaoPedidoDTO;
import com.pi.mafu_bakery_api.dto.DetalhesPedidoDTO;
import com.pi.mafu_bakery_api.dto.PedidoStatusDTO;
import com.pi.mafu_bakery_api.model.Pedido;
import com.pi.mafu_bakery_api.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/realizarPedido")
    public ResponseEntity<Pedido> realizarPedido(@RequestBody @Valid CriacaoPedidoDTO dto) throws Exception {
        return pedidoService.realizarPedido(dto);
    }

    @GetMapping("/listarPedidos")
    public ResponseEntity<List<DetalhesPedidoDTO>> listarPedidosCliente(@RequestParam ("id") Long id) {
        return pedidoService.listarPedidosCliente(id);
    }

    @PatchMapping("/atualizarStatus")
    public ResponseEntity<PedidoStatusDTO> atualizarStatus(@RequestParam("id") Long id, @RequestParam("status") String status) {
        return pedidoService.atualizarStatus(id, status);
    }
}
