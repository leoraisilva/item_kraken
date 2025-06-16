package br.com.kraken.item.java.modelDTO;

import br.com.kraken.item.java.model.Rules;

import java.time.LocalDateTime;

public record AcessoDTO (String usuarioId, String usuario, LocalDateTime dataCadastro, Rules rules) {}
