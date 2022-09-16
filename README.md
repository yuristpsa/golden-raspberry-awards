# API RESTful para possibilitar a leitura da lista de indicados e vencedores da categoria Pior Filme do Golden Raspberry Awards

Projeto desenvolvido com Spring que implementa a seguintes funcionalidades:

- Permite realizar upload de um arquivo CSV contendo concorrentes e vencedores do Golden Raspberry Awards
- Exibir produtores com menor e maior intervalo entre as premiaçoes

## Executando aplicação 

Navegar até a raiz do projeto via linha de comando e executar o seguinte comando: mvn spring-boot:run

## Executando Testes

Navegar até raiz do projeto via linha de comando e executar o seguinte comando: mvn test

## Exemplos de Chamadas

Nesta seção são expostas as instruções para realização de cada uma das possíveis chamadas:

#### Upload arquivo .csv

POST /api/csv/upload
Content-Disposition: form-data; name="file"; filename="movielist.csv"
Content-Type: text/csv

#### Obter resumo produtores com menor e maior intervalo entre contemplaçoes

GET /api/csv/awards



