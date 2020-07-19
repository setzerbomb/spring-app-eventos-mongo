# Spring App Eventos Mongo API

Este projeto é uma demo que contém as principais características de uma aplicação RESTful, usando Spring e MongoDB.

## Getting Started

Para baixar e analisar o código serão necessários comprir alguns requisitos, segue a lista.

### Pré Requisitos

```
* Java JRE 1.8
* MongoDB Latest
```

### Configuração e execução

Para utilizar o projeto como base é necessário trocar as informações de conexão do OAuth Server e mudar o arquivo de propriedades para apontar para um banco de dados local ou hospedado

## Uso da API

A API permite o cadastro e autenticação de usuários e permite que um usuário cadastre eventos informando o nome do evento e a data que o mesmo acontecerá

### Registro

Registro de usuários

### /register ⇒ <code>Object</code>

<p> Adiciona um usuário </p>

**Method**: POST </br>
**Returns**: <code>String</code> - Uma mensagem de sucesso/fracasso da operação

| Param             | Type                | Description                                      |
| ------------------| ------------------- | -------------------------------------------------|
| nome              | <code>String</code> | Nome do evento                                   |
| email             | <code>Date</code>   | Data do evento                                   |
| senha             | <code>Date</code>   | Data do evento                                   |
| confirmacaoSenha  | <code>Date</code>   | Data do evento                                   |

### Eventos

Todos os eventos são associados ao contexto de sessão do usuário.

### /events ⇒ <code>Array</code>

<p> Retorna todos os eventos armazenados associados ao usuário </p>

**Method**: GET </br>
**Returns**: <code>Array</code> - Uma lista de eventos
<a name="module_api../events/_id"></a>

### /events/:id ⇒ <code>Object</code>

<p> Procura por um evento específico </p>

**Method**: GET </br>
**Returns**: <code>Object</code> - Se o evento existe, retorna o evento buscado, senão retorna vazio
<a name="module_api../events"></a>

| Param | Type                | Description                                                  |
| ----- | ------------------- | ------------------------------------------------------------ |
|  id   | <code>String</code> | Id do evento                                                 |

### /events ⇒ <code>Object</code>

<p> Adiciona um evento </p>

**Method**: POST </br>
**Returns**: <code>String</code> - Uma mensagem de sucesso/fracasso da operação

| Param | Type                | Description                                                  |
| ----- | ------------------- | ------------------------------------------------------------ |
| nome  | <code>String</code> | Nome do evento                                               |
| data  | <code>Date</code>   | Data do evento                                               |

<a name="module_api../events/_id"></a>

### /events/:id ⇒ <code>Object</code>

<p> Atualiza um evento armazenado </p>

**Method**: PUT </br>
**Returns**: <code>String</code> - Se o evento existe, atualiza o evento especificado, senão, retorna vazio

| Param | Type                | Description                                                  |
| ----- | ------------------- | ------------------------------------------------------------ |
|  id   | <code>String</code> | Id do evento                                                 |
| nome  | <code>String</code> | Nome do evento                                               |
| data  | <code>Date</code>   | Data do evento                                               |


<a name="module_api../events/_id"></a>

### /events/:id ⇒ <code>Object</code>

<p> Remove um evento </p>

**Method**: DELETE </br>
**Returns**: <code>Void</code> - Se o evento existe, o evento é removido do sistema, senão retorna evento não encontrado

| Param | Type                | Description                                                  |
| ----- | ------------------- | ------------------------------------------------------------ |
|  id   | <code>String</code> | Id do evento                                                 |

## Authors

* **Set** - *All work until now* - [setzerbomb](https://github.com/setzerbomb)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
