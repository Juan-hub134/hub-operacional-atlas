# Atlas T.E.C. | Hub Operacional

Sistema web em desenvolvimento para centralizar rotinas operacionais da Atlas T.E.C., incluindo organização de documentos, movimentações financeiras e gerenciamento de usuários.

> **Status:** projeto em desenvolvimento. Esta documentação descreve os recursos identificados no código atual e será atualizada conforme novas funcionalidades forem implementadas.

## Tecnologias

- **Java 21**
- **Spring Boot 4.1.1**
- **Spring Web MVC** para os endpoints HTTP
- **Spring Data JPA / Hibernate** para persistência
- **PostgreSQL** como banco de dados
- **Spring Security** para segurança da aplicação
- **JWT com algoritmo RSA (RS256)** para emissão e validação de tokens
- **Maven** para dependências e build
- **Docker** para empacotamento e deploy

## Funcionalidades identificadas

- Cadastro de usuários associado a um registro/token de convite.
- Autenticação por e-mail e senha, com senha armazenada usando hash via `PasswordEncoder`.
- Emissão de JWT após autenticação bem-sucedida.
- Envio, listagem, download e exclusão de documentos.
- Registro, consulta, atualização e exclusão de movimentações financeiras.
- Consultas de totais de entradas, saídas, saldo do período e saldo pendente.

Os endpoints atuais estão descritos em [Documentação da API](docs/api.md).

## Arquitetura

O backend segue uma separação por responsabilidades:

- **Controller:** recebe requisições HTTP e define respostas.
- **Service:** concentra as regras e operações da aplicação.
- **Repository:** abstrai o acesso ao banco por meio do Spring Data.
- **Model/Entity:** representa os dados persistidos.
- **DTO:** define os dados recebidos ou devolvidos pela API.
- **Configuration:** reúne configurações transversais, incluindo segurança.

Consulte [Arquitetura](docs/arquitetura.md) e [Autenticação e segurança](docs/autenticacao.md).

## Estrutura do projeto

O projeto Spring Boot está dentro da pasta `huboperacional/`:

```text
hub-operacional-atlas/
├── README.md
├── docs/
│   ├── api.md
│   ├── arquitetura.md
│   └── autenticacao.md
└── huboperacional/
    ├── Dockerfile
    ├── pom.xml
    └── src/
        └── main/
            ├── java/br/com/atlastec/huboperacional/
            └── resources/
```

A estrutura acima é resumida; os pacotes e arquivos podem evoluir durante o desenvolvimento.

## Requisitos

- JDK 21
- Maven (ou uso do Maven Wrapper, caso esteja disponível no checkout)
- Instância PostgreSQL acessível pela aplicação

## Configuração local

A aplicação precisa de uma conexão PostgreSQL e das chaves RSA usadas no fluxo JWT. Configure esses valores no ambiente local ou no mecanismo de configuração adotado pelo projeto.

Não coloque senhas, tokens, chaves privadas ou outros segredos no repositório. Em ambientes de deploy, configure-os como variáveis de ambiente ou arquivos secretos fornecidos pela plataforma.

As propriedades efetivamente necessárias devem corresponder às usadas pela configuração da aplicação. Não existe uma configuração universal que substitua a conferência do código e do ambiente.

## Build

Na pasta `huboperacional/`, execute:

```bash
mvn clean package
```

Para compilar sem executar os testes:

```bash
mvn clean package -DskipTests
```

## Deploy

O projeto possui um Dockerfile em `huboperacional/Dockerfile`. O container utiliza Java 21 e considera a variável de ambiente `PORT` fornecida pela plataforma de hospedagem, com fallback para a porta `8080`.

O serviço também depende de variáveis de configuração e segredos do banco e da autenticação. Configure-os diretamente na plataforma de deploy, nunca em arquivos versionados.

## Documentação

- [Endpoints da API](docs/api.md)
- [Arquitetura do backend](docs/arquitetura.md)
- [Autenticação e segurança](docs/autenticacao.md)

A documentação do modelo relacional será complementada com o diagrama atualizado do banco de dados.
