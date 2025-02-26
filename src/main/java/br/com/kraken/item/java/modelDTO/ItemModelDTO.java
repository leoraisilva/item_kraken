package br.com.kraken.item.java.modelDTO;

import java.util.List;
import java.util.UUID;

public record ItemModelDTO(int quantidadeIten, double valorItem, String statusItem, UUID produto, double valorTotal) {
}
