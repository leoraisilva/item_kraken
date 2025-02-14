package br.com.kraken.item.java.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table (name = "itens_pedidos")
public class ItemModel {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    @Column (nullable = false)
    private UUID idItem;
    @Column (nullable = false, name = "quantidade_item")
    private int quantidadeIten;
    @Column (nullable = false, name = "valor_item")
    private double valorItem;
    @Column (nullable = false, name = "produto")
    private UUID produto;
    @Column (nullable = false, name = "valor_total")
    private double valorTotal;

    public ItemModel(int quantidadeIten, double valorItem, UUID produto, double valorTotal) {
        this.quantidadeIten = quantidadeIten;
        this.valorItem = valorItem;
        this.produto = produto;
        this.valorTotal = valorTotal;
    }
    public ItemModel () {}

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public UUID getProduto() {
        return produto;
    }

    public void setProduto(UUID produto) {
        this.produto = produto;
    }

    public double getValorItem() {
        return valorItem;
    }

    public void setValorItem(double valorItem) {
        this.valorItem = valorItem;
    }

    public int getQuantidadeIten() {
        return quantidadeIten;
    }

    public void setQuantidadeIten(int quantidadeIten) {
        this.quantidadeIten = quantidadeIten;
    }

    public UUID getIdItem() {
        return idItem;
    }

}
