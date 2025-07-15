# item_kraken
A API do Sistema Kraken responsável pelo gerenciamento do Pedido

## Serviço tem como a função:
Cria, alterar, buscar e deletar os itens cadastrado no banco de dado.

## Ferramentas usado:
Foi criado em Spring Boot e conteirizado com o Docker

## O que depende?
A sua utilização depende do cadastro no serviço cliente_kraken como Comprador que dará a permissão ao uso

## Como é feito a permissão?
Permissão vem de um token JWT gerado da API cliente_kraken que dará permissão ao serviço do item_kraken
