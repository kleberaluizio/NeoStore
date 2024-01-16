# NeoStore - Processo Seletivo Interno
Sistema de gerenciamento de fornecedores. Permite ao um usuário executar operações de criação, delecã́o e atualização dos dados por meio de uma aplicação web. A API Rest possibilita também a importação de fornecedores em lotes a partir de um JSON.

## Executando a Aplicação
Siga os passos abaixo para configurar e executar a aplicação:

**Pré-requisitos**

* Java Development Kit (JDK)
* MySQL
* Apache Maven

### Passos de Configuração

**Clone o repositório:**

git clone https://github.com/kleberaluizio/NeoStore.git

**Acesse o diretório do projeto:**

cd NeoStore

**Crie um banco de dados**

Crie um banco de dados, no MySQL, chamado **neostore**.

**Build do Projeto:**

mvn clean install

**Executando a Aplicação**

mvn exec:java<br /> 
O Grizzly HTTP server está embutido na aplicação.<br /> 
A aplicação estará disponível em http://localhost:8080. 

**Executar o Frontend:**

Abra o arquivo index.html(frontend/index.html) no navegador.

**Configurações Adicionais**

Para alterar a porta padrão, edite a classe Main e modifique a variável BASE_URI.

**Testando a Aplicação**

Use ferramentas como Postman ou curl para testar os endpoints. O arquivo **Neostore.postman_collection** pode ser utilizado para realizar os testes de endpoints no postman.

**Encerrando a Aplicação**

Para encerrar a aplicação, pressione Ctrl + C no terminal.


## Tecnologias Utilizadas
### Back-end
* Java 17 (JAX-RS)
	* Utilizei Jersey como implementação da especificação JAX-RS.
* Camada de persistência (JPA + Hibernate);
* Maven
* Banco de dados MySQL
* Grizzly (embedded server)

### Front-end
* AngularJS (versão 1.8)
* Bootstrap
* Bibliotecas:
	* Sweetalert.js para exibir alertas
	* Cleave.js para aplicar mascára ao campo de cnpj.
	
## Endpoints

* **POST /supplier -** Cria um novo fornecedor.
	* Retorna status 201 (Created) em sucesso, 400 (Bad Request) ou 500 (Internal Server Error) em falha.

* **POST /supplier/batch -** Cria vários fornecedores em lote.
	* Retorna status 201 (Created) se todos forem criados, 400 (Bad Request) com detalhes em falha.

* **GET /supplier -** Retorna todos os fornecedores
	* Retorna todos os fornecedores (status 200 - OK).

* **GET /supplier/paginated -** Retorna todos os fornecedores da pagina solicitada
	* Parâmetros: itemsPerPage, page.
	* Retorna uma resposta paginada de fornecedores (status 200 - OK).

* **PUT /supplier/{id} -** Atualiza um fornecedor com ID específico.
	* Retorna status 200 (OK) se bem-sucedido, 404 (Not Found) se não encontrado, 500 (Internal Server Error) em falha.

* **DELETE /supplier/{id} -** Exclui um fornecedor com ID específico.
	* Retorna status 200 (OK) se bem-sucedido, 404 (Not Found) se não encontrado, 500 (Internal Server Error) em falha.
