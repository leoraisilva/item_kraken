package br.com.kraken.item.java.controller;

import br.com.kraken.item.java.modelDTO.ItemModelDTO;
import br.com.kraken.item.java.model.ItemModel;
import br.com.kraken.item.java.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping ("/itens")
public class ItemController {
    private final ItemService itemService;

    public ItemController (ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping()
    public ResponseEntity<Object> cadastrarItem (@RequestBody @Valid ItemModelDTO itemModelDTO) {
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemModelDTO, itemModel);
        itemModel.setValorTotal(itemModel.getValorItem() * itemModel.getQuantidadeIten());
        return  ResponseEntity.status(HttpStatus.CREATED).body(itemService.getItemRepository().save(itemModel));
    }

    @GetMapping()
    public ResponseEntity<Object> listarItens (){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItemRepository().findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> encontrarItem (@PathVariable (value = "id") UUID id) {
        Optional<ItemModel> itemModelOptional = itemService.getItemRepository().findById(id);
        return itemModelOptional.<ResponseEntity<Object>>map(
                        itemModel -> ResponseEntity.status(HttpStatus.OK).body(itemModel))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found Item")
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarItem (@PathVariable (value = "id") UUID id) {
        Optional<ItemModel> itemModelOptional = itemService.getItemRepository().findById(id);
        if(!itemModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found Item");
        itemService.getItemRepository().delete(itemModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Delete Success!!!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterarItem (@PathVariable (value = "id") UUID id, @RequestBody @Valid ItemModelDTO itemModelDTO) {
        Optional<ItemModel> itemModelOptional = itemService.getItemRepository().findById(id);
        if(!itemModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found Item");
        itemModelOptional.get().setQuantidadeIten(itemModelDTO.quantidadeIten());
        itemModelOptional.get().setValorTotal(itemModelDTO.valorItem() * itemModelDTO.quantidadeIten());
        itemModelOptional.get().setValorItem(itemModelDTO.valorItem());
        itemModelOptional.get().setProduto(itemModelDTO.produto());
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItemRepository().save(itemModelOptional.get()));
    }

}
