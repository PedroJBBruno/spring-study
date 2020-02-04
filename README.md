# Micro Serviço no spring

### Como subir este projeto
Configurar o docker do postgres realizando os seguintes comandos(necessario ter o docker configurado na maquina)
* docker pull postgres
* docker run --name spring-postgres -p 5432:5432 -e POSTGRES_PASSWORD=springpostgressenha -d postgres

Importar o projeto no intellij como maven, e rodar o comando maven spring-boot:run
