# NeoStore - Processo Seletivo Interno

## Back-end
API REST para devolver os dados de fornecedores, e ainda executar operações de criação, delecã́o e atualização dos dados. Possibilita também a importação de fornecedores em lotes a partir de um JSON.

### Tecnologias Utilizadas

* Java 17 (JAX-RS)
	* Utilizei Jersey como implementação da especificação JAX-RS.
* Camada de persistência (JPA + Hibernate);
* Maven
* Banco de dados MySQL
* Grizzly (embedded server)


## Front-end
Interface para apresentar os dados de fornecedores, requisitando os mesmo a partir da API REST criada anteriormente. A interface permite realizar operações de criação, edição e exclusão de fornecedores.

### Tecnologias Utilizadas

* AngularJS (versão 1.8)
* Bootstrap
* Bibliotecas:
	* Sweetalert.js para exibir alertas
	* Cleave.js para aplicar mascára ao campo de cnpj.
	
## Endpoints

### Criar Fornecedor:

* POST /supplier
	* Cria um novo fornecedor.
	* Retorna status 201 (Created) em sucesso, 400 (Bad Request) ou 500 (Internal Server Error) em falha.

### Criar Fornecedores em Lote:

* POST /supplier/batch
	* Cria vários fornecedores em lote.
	* Retorna status 201 (Created) se todos forem criados, 400 (Bad Request) com detalhes em falha.

### Obter Todos os Fornecedores:

* GET /supplier
	* Retorna todos os fornecedores (status 200 - OK).

### Obter Fornecedores Paginados:

* GET /supplier/paginated
	* Parâmetros: itemsPerPage, page.
	* Retorna uma resposta paginada de fornecedores (status 200 - OK).

### Atualizar Fornecedor:

* PUT /supplier/{id}
	* Atualiza um fornecedor com ID específico.
	* Retorna status 200 (OK) se bem-sucedido, 404 (Not Found) se não encontrado, 500 (Internal Server Error) em falha.

### Excluir Fornecedor:

* DELETE /supplier/{id}
	* Exclui um fornecedor com ID específico.
	* Retorna status 200 (OK) se bem-sucedido, 404 (Not Found) se não encontrado, 500 (Internal Server Error) em falha.
