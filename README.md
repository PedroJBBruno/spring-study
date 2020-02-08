# Micro Serviço no spring

### Como subir este projeto
Configurar o docker do postgres realizando os seguintes comandos(necessario ter o docker configurado na maquina)
* docker pull postgres
* docker run --name spring-postgres -p 5432:5432 -e POSTGRES_PASSWORD=springpostgressenha -d postgres

* Importar o projeto eurekaServer no intellij como maven, e rodar o comando maven spring-boot:run
* Importar o projeto authService no intellij como maven, e rodar o comando maven spring-boot:run
* Importar o projeto zuulGateway no intellij como maven, e rodar o comando maven spring-boot:run
* Importar o projeto personService no intellij como maven, e rodar o comando maven spring-boot:run

Consultar a url localhost:8761 para ver se as instancias AUTH-SERVICE, PERSON-SERVICE e ZUUL-SERVER	estão de pé

Importar a collection "Micro Service Collection.postman_collection.json" e o enviroment "local.postman_environment.json" no postman,
selecionar o enviroment local e rodar a request "Criar autorização" para criar a variavel com o valor do authorization e depois rodar as outras requests.

