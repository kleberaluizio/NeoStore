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
