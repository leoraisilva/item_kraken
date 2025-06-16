package br.com.kraken.item.java.controller;

import br.com.kraken.item.java.model.AcessoModel;
import br.com.kraken.item.java.modelDTO.AcessoDTO;
import br.com.kraken.item.java.modelDTO.ItemModelDTO;
import br.com.kraken.item.java.model.ItemModel;
import br.com.kraken.item.java.service.AcessoService;
import br.com.kraken.item.java.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping ("/itens")
public class ItemController {
    private final ItemService itemService;
    private final AcessoService acessoService;

    public ItemController (ItemService itemService, AcessoService acessoService) {
        this.itemService = itemService;
        this.acessoService = acessoService;
    }

    @PostMapping("/auth/registry")
    public ResponseEntity<Object> itemCliente(@RequestBody @Valid AcessoDTO acessoDTO) {
        AcessoModel acessoModel = new AcessoModel();
        BeanUtils.copyProperties(acessoDTO, acessoModel);
        acessoModel.setDataCadastro(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return ResponseEntity.status(HttpStatus.CREATED).body(acessoService.getRepository().save(acessoModel));
    }

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> cadastrarItem (@RequestBody @Valid ItemModelDTO itemModelDTO) {
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemModelDTO, itemModel);
        itemModel.setValorTotal(itemModel.getValorItem() * itemModel.getQuantidadeIten());
        return  ResponseEntity.status(HttpStatus.CREATED).body(itemService.getItemRepository().save(itemModel));
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> listarItens (){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItemRepository().findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> encontrarItem (@PathVariable (value = "id") UUID id) {
        Optional<ItemModel> itemModelOptional = itemService.getItemRepository().findById(id);
        return itemModelOptional.<ResponseEntity<Object>>map(
                        itemModel -> ResponseEntity.status(HttpStatus.OK).body(itemModel))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found Item")
                );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> deletarItem (@PathVariable (value = "id") UUID id) {
        Optional<ItemModel> itemModelOptional = itemService.getItemRepository().findById(id);
        if(!itemModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found Item");
        itemService.getItemRepository().delete(itemModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Delete Success!!!");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> alterarItem (@PathVariable (value = "id") UUID id, @RequestBody @Valid ItemModelDTO itemModelDTO) {
        Optional<ItemModel> itemModelOptional = itemService.getItemRepository().findById(id);
        if(!itemModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found Item");
        itemModelOptional.get().setQuantidadeIten(itemModelDTO.quantidadeIten());
        itemModelOptional.get().setStatusItem(itemModelDTO.statusItem());
        itemModelOptional.get().setValorTotal(itemModelDTO.valorItem() * itemModelDTO.quantidadeIten());
        itemModelOptional.get().setValorItem(itemModelDTO.valorItem());
        itemModelOptional.get().setProduto(itemModelDTO.produto());
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItemRepository().save(itemModelOptional.get()));
    }
    
}
