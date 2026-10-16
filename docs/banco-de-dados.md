# Modelo de dados

Este documento descreve o modelo relacional representado no diagrama do banco de dados do Hub Operacional.

## Entidades e atributos

### `usuario`

| Campo | Tipo no diagrama | Descrição |
|---|---|---|
| `id` | int | Chave primária (PK). |
| `id_registro` | int | Chave estrangeira (FK) para `registro.id`. |
| `email` | String | E-mail do usuário. |
| `senha` | String | Senha armazenada pela aplicação. |
| `nome` | String | Nome do usuário. |
| `tipo` | String | Tipo ou perfil do usuário. |

### `registro`

| Campo | Tipo no diagrama | Descrição |
|---|---|---|
| `id` | int | Chave primária (PK). |
| `token` | String | Token usado no fluxo de registro. |
| `expiracao` | data | Data de expiração do registro/token. |
| `tipo` | String | Tipo associado ao registro. |

### `treinamento`

| Campo | Tipo no diagrama | Descrição |
|---|---|---|
| `id` | int | Chave primária (PK). |
| `id_usuario` | int | Chave estrangeira (FK) para `usuario.id`. |
| `dia_inicio` | data | Data de início. |
| `dia_termino` | data | Data de término. |
| `area` | String | Área do treinamento. |
| `descricao` | String | Descrição da atividade ou conteúdo. |
| `checklist` | boolean | Indica o estado marcado no checklist. |

### `movimentacoes_financeiras`

| Campo | Tipo no diagrama | Descrição |
|---|---|---|
| `id` | int | Chave primária (PK). |
| `tipo` | String | Tipo da movimentação. |
| `valor` | double | Valor financeiro. |
| `data` | data | Data da movimentação. |
| `categoria` | String | Categoria da movimentação. |
| `status` | String | Status da movimentação. |

### `documentos`

| Campo | Tipo no diagrama | Descrição |
|---|---|---|
| `id` | int | Chave primária (PK). |
| `titulo` | String | Título do documento. |
| `tipo` | String | Tipo do documento. |
| `documento` | BYTEA | Conteúdo binário do documento. |
| `data` | timestamp | Data e horário associados ao documento. |

## Relacionamentos

- **Usuário e Registro:** `usuario.id_registro` referencia `registro.id`.
- **Usuário e Treinamento:** `treinamento.id_usuario` referencia `usuario.id`. Um usuário pode estar associado a registros de treinamento.
- No diagrama fornecido, não há chaves estrangeiras desenhadas para `movimentacoes_financeiras` ou `documentos`.

## Observações

- Os tipos apresentados são os que aparecem no diagrama conceitual. Os tipos físicos e nomes efetivamente usados pelo PostgreSQL devem ser conferidos no mapeamento das entidades e no schema aplicado.
- `PK` significa chave primária e `FK` significa chave estrangeira.
- Este documento registra o modelo representado no diagrama, sem presumir relacionamentos que não estejam indicados nele.
