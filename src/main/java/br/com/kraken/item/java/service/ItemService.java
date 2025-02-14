package br.com.kraken.item.java.service;
import br.com.kraken.item.java.repository.ItemRepository;
import org.springframework.stereotype.Service;


@Service
public class ItemService {
    private final ItemRepository itemRepository;
    public ItemService (ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemRepository getItemRepository() {
        return itemRepository;
    }

}
