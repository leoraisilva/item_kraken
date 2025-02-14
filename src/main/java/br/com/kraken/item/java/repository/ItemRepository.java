package br.com.kraken.item.java.repository;

import br.com.kraken.item.java.model.ItemModel;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ItemRepository extends CrudRepository <ItemModel, UUID> {
}
