package com.pi.mafu_bakery_api.controller;

import com.pi.mafu_bakery_api.dto.CriacaoPedidoDTO;
import com.pi.mafu_bakery_api.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/realizarPedido")
    public ResponseEntity<?> realizarPedido(@RequestBody @Valid CriacaoPedidoDTO dto) throws Exception {
        return pedidoService.realizarPedido(dto);
    }

}
