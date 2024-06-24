# README!!
### Esta aplicação Spring Boot implementa dois endpoints seguros usando OAuth2 do tipo Authentication password. A seguir estão detalhadas as funcionalidades e instruções para configurar e usar a aplicação.

**Funcionalidades**

- Endpoint /token:

Método HTTP: GET

Descrição: Retorna um token de acesso OAuth2 usando o fluxo de Authorization Code.

URL: /token

- Endpoint /validate:

Método HTTP: POST

Descrição: Valida se uma senha fornecida pelo usuário no corpo da requisição é válida, usando um token de acesso OAuth2.

URL: /validate

Configuração do OAuth2

A aplicação está configurada para usar OAuth2 com o fluxo de Authorization Code,com integração do Keycloak para geração da token utilizadas pelo OAuth2.

**Executando a Aplicação**

Clone este repositório para sua máquina local:
https://github.com/DouglasRodrigs/case-itau-backend.git

Execute a aplicação Spring Boot:
./mvnw spring-boot:run

**Testando os Endpoints**

Para obter um token de acesso, faça uma requisição GET para /token.

Exemplo usando cURL:
+ curl -X GET http://localhost:8081/token/

Para validar uma senha usando o token obtido, faça uma requisição POST para /validate. Inclua o token no cabeçalho Authorization no formato Bearer <token> e envie a senha a ser validada no corpo da requisição, como uma string simples.

Exemplo usando cURL:
+ curl -X POST -H "Authorization: Bearer <seu-token>" -d "senha-a-ser-validada" http://localhost:8081/validate/
