package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.CriacaoPedidoDTO;
import com.pi.mafu_bakery_api.dto.ProdutosPedidoDTO;
import com.pi.mafu_bakery_api.enums.StatusPedido;
import com.pi.mafu_bakery_api.key.PedidoProdutoKey;
import com.pi.mafu_bakery_api.model.*;
import com.pi.mafu_bakery_api.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PedidoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoProdutoRepository pedidoProdutoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoService produtoService;

    @Transactional()
    public ResponseEntity<?> realizarPedido(CriacaoPedidoDTO dto) throws Exception {

        try{
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoEntrega())
                    .orElseThrow(() -> new NoSuchElementException("Endereço não encontrado."));

            Cliente cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado."));

            Pedido pedido = new Pedido();
            pedido.setEnderecoEnvio(endereco);
            pedido.setClienteId(cliente);
            pedido.setFormaPagamento(dto.getFormaPagamento());
            pedido.setFrete(dto.getFrete());
            pedido.setTotalPedido(dto.getTotalPedido());
            pedido.setSubtotal(dto.getSubtotal());
            pedido.setStatusPedido(StatusPedido.EM_ANDAMENTO);

            pedidoRepository.save(pedido);

            for(ProdutosPedidoDTO produtosPedidoDTO : dto.getProdutos()) {
                cadastraRelacionamentoPedidoEProduto(pedido, produtosPedidoDTO);
            }

            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (IllegalStateException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    public void cadastraRelacionamentoPedidoEProduto(Pedido pedido, ProdutosPedidoDTO dto) throws Exception {

        Produto produto = produtoRepository.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado."));

        PedidoProduto pedidoProduto = new PedidoProduto();
        PedidoProdutoKey pedidoProdutoKey = new PedidoProdutoKey(pedido, produto);
        pedidoProduto.setId(pedidoProdutoKey);
        pedidoProduto.setValorUnitario(dto.getValorUnitario());
        pedidoProduto.setTotal(dto.getTotal());
        pedidoProduto.setQuantidade(dto.getQuantidade());
        if(produtoService.consumirProduto(produto, dto.getQuantidade())) {
            pedidoProdutoRepository.save(pedidoProduto);
        } else {
            throw new IllegalStateException("Falha ao criar relacionamento por falta de produto em estoque.");
        }

    }
}
