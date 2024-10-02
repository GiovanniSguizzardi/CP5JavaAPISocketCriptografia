# Projeto RSA Client-Server com Criptografia RSA

Este projeto implementa um sistema de comunicação **Client-Server** em Java, utilizando o protocolo **TCP/IP** para o envio de mensagens criptografadas e descriptografadas com o algoritmo **RSA (Rivest-Shamir-Adleman)**. O projeto foi desenvolvido na **IDE IntelliJ** e utiliza sockets para estabelecer a conexão entre o cliente e o servidor.

## Funcionalidades
- Geração dinâmica de chaves RSA (incluindo números primos \( P \) e \( Q \)).
- Troca de mensagens criptografadas entre o cliente e o servidor.
- Descriptografia das mensagens no servidor e reenvio criptografado ao cliente.
- Validação do processo de criptografia e descriptografia utilizando o **RSA Express Encryption/Decryption Calculator** da Drexel University.

## Tecnologias Utilizadas
- **Java**: Linguagem de programação.
- **Sockets TCP/IP**: Para comunicação entre Cliente e Servidor.
- **RSA**: Algoritmo de criptografia assimétrica.
- **IntelliJ IDEA**: IDE utilizada para desenvolvimento.

## Como Funciona
O projeto consiste em duas partes principais:

1. **Cliente**:
   - O cliente gera chaves públicas \( N \) e \( E \) dinamicamente com base nos números primos \( P \) e \( Q \).
   - Envia as chaves públicas e a mensagem criptografada ao servidor.
   - Recebe a mensagem descriptografada e criptografada de volta do servidor.

2. **Servidor**:
   - O servidor recebe as chaves públicas e a mensagem criptografada do cliente.
   - Descriptografa a mensagem recebida e exibe a mensagem original.
   - Criptografa novamente a mensagem com a chave pública do cliente e a envia de volta.

### Geração de Chaves RSA
As chaves \( P \), \( Q \), \( N \), \( E \), e \( D \) são geradas dinamicamente na execução do cliente, permitindo que as mensagens sejam criptografadas de forma segura.

## Exemplo de Execução
### Cliente
- Abaixo está um exemplo de execução do cliente, onde ele gera as chaves, envia uma mensagem criptografada ao servidor, e recebe a mensagem de volta.

![CP5JAVACliente](https://github.com/user-attachments/assets/66616815-522c-4f74-9b50-a4c9a75f0162)
![CP5JAVAServer](https://github.com/user-attachments/assets/a76ed84f-d468-426d-a041-44438f3f814b)
![RSACALCULATOR](https://github.com/user-attachments/assets/d84b2bac-f5b0-41d5-b88b-9f1c0d2e1518)
