# 📌 Projeto: CRUD de Clientes com Spring Boot e JPA

Um sistema completo para gerenciamento de clientes implementando as melhores práticas de desenvolvimento Spring.

## 🏗️ Arquitetura
Controller → Service → Repository

Padrão em camadas com separação clara de responsabilidades.

## 🛠️ Tecnologias Principais
- **Spring Boot 3** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Hibernate** - Implementação JPA
- **H2 Database** - Banco de dados em memória (para desenvolvimento)
- **Bean Validation** - Validação de dados
- **Lombok** - Redução de boilerplate (opcional)

## 🌟 Features Implementadas

### ✅ Operações CRUD Completo
- Create (`POST /customers`)
- Read (`GET /customers` e `GET /customers/{id}`)
- Update (`PUT /customers/{id}`)
- Delete (`DELETE /customers/{id}`)

### 🔍 Consultas Avançadas
```java
// Exemplo de métodos no Repository
Page<CustomerEntity> findByCpf(String cpf, Pageable pageable);
Page<CustomerEntity> findByEmailAndCpf(String email, String cpf, Pageable pageable);

```

### 📊 Paginação e Ordenação
Parâmetros suportados:

page - Número da página

pageSize - Itens por página

orderBy - Ordenação (asc/desc)

### 🛡️ Validações
Campos únicos: CPF e Email

Timestamps automáticos:
```JAVA
@CreationTimestamp
private LocalDateTime createdAt;

@UpdateTimestamp
private LocalDateTime updateAt;
```

### 🔗 Endpoints
POST	/customers	Cria novo cliente

GET	/customers	Lista paginada

GET	/customers/{id}	Busca por ID

PUT	/customers/{id}	Atualiza cliente

DELETE	/customers/{id}	Remove cliente

