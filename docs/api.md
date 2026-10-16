# API HTTP

Esta página lista as rotas identificadas nos controllers atuais. O prefixo comum é `/atlashub`. Confirme a configuração de segurança antes de chamar as rotas: a existência de uma rota no controller não significa que ela esteja liberada para acesso anônimo.

## Documentos

Controller: `DocumentoController`

| Método | Caminho | Finalidade |
|---|---|---|
| `POST` | `/atlashub/api/admin/upload` | Envia um documento. Recebe dados por multipart/form-data, incluindo o campo `file`. |
| `GET` | `/atlashub/api/admin/arquivo/lista` | Lista documentos. |
| `GET` | `/atlashub/api/{id}/admin/arquivo/download` | Busca o arquivo pelo identificador informado. |
| `GET` | `/atlashub/api/{id}/admin/arquivo/remover` | Remove um documento pelo identificador. |

Observações:

- O upload recebe um DTO por `@ModelAttribute` e um arquivo pelo campo `file`.
- Download e remoção retornam `404` quando o documento não é encontrado.
- A rota de remoção atualmente usa `GET`. Para uma API REST, considere futuramente migrá-la para `DELETE`, avaliando o impacto no frontend.

## Movimentações financeiras

Controller: `MovimentacoesFinanceirasController`

| Método | Caminho | Finalidade |
|---|---|---|
| `POST` | `/atlashub/api/admin/registrar/movimentacao/financeira` | Registra uma movimentação. |
| `GET` | `/atlashub/api/admin/listar/movimentacao/financeira` | Lista movimentações. |
| `POST` | `/atlashub/api/admin/{id}/atualizar/movimentacao/financeira` | Atualiza a movimentação indicada. |
| `POST` | `/atlashub/api/admin/{id}/excluir/movimentacao/financeira` | Exclui a movimentação indicada. |
| `GET` | `/atlashub/api/admin/{id}/buscar/movimentacao/financeira` | Busca uma movimentação específica. |
| `GET` | `/atlashub/api/admin/soma/entradas/financeiras` | Consulta a soma das entradas. |
| `GET` | `/atlashub/api/admin/soma/saidas/financeiras` | Consulta a soma das saídas. |
| `GET` | `/atlashub/api/admin/saldo/periodo` | Consulta o saldo do período. |
| `GET` | `/atlashub/api/admin/soma/saldo/pendente` | Consulta o saldo pendente. |

Os endpoints de atualização e exclusão retornam `404` quando o identificador não é encontrado, conforme o tratamento presente no controller.

## Usuários e registro

O controller de registro também contém o endpoint de login `POST /atlashub/api/login`. O login recebe um objeto JSON correspondente a `LoginDto` e retorna o token JWT quando as credenciais são válidas. Credenciais inválidas produzem `401 Unauthorized`.

O formato completo dos DTOs, os campos obrigatórios e as respostas de cada operação devem ser conferidos nas classes de DTO correspondentes.

## Exemplos

### Login

O corpo deve corresponder aos campos definidos em `LoginDto`. Não envie senhas reais em exemplos salvos no repositório.

```http
POST /atlashub/api/login
Content-Type: application/json
```

### Upload de documento

Use `multipart/form-data`, enviando o campo de arquivo `file` e os campos exigidos por `DocumentosDto`.

## Segurança

- Não coloque tokens reais em exemplos de documentação.
- Não presuma que um endpoint está público apenas pelo seu caminho.
- Em produção, use HTTPS e configure os segredos no ambiente de deploy.
- Consulte [Autenticação e segurança](autenticacao.md) para o fluxo de login e JWT.
