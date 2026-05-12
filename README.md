# E-Commerce Microservices Platform

Projeto pessoal desenvolvido com arquitetura baseada em microsserviços, utilizando o ecossistema Spring Cloud para simular uma plataforma moderna de e-commerce distribuído.

O objetivo do projeto é estudar e aplicar conceitos avançados de:

- Microsserviços
- Comunicação síncrona e assíncrona
- Service Discovery
- API Gateway
- Event Driven Architecture
- Mensageria com Kafka
- Observabilidade
- Containers com Docker
- Escalabilidade e desacoplamento

---

# Arquitetura

A aplicação é composta por múltiplos microsserviços independentes que se comunicam através de REST e eventos assíncronos.

## Componentes principais

| Serviço | Responsabilidade |
|---|---|
| customer-service | Gerenciamento de clientes |
| product-service | Gerenciamento de produtos e estoque |
| order-service | Criação e gerenciamento de pedidos |
| payment-service | Processamento de pagamentos |
| notification-service | Envio de notificações |
| api-gateway | Porta de entrada da aplicação |
| discovery-service | Service Discovery |
| Kafka | Comunicação assíncrona entre serviços |
| ElasticSearch | Indexação e observabilidade |
| Zipkin | Distributed Tracing |

---

# Fluxo da Aplicação

## Fluxo síncrono

O cliente realiza requisições através do API Gateway:

```text
/customers
/products
/orders
```

O Gateway encaminha as requisições para os microsserviços responsáveis utilizando o Discovery Service.

---

## Fluxo assíncrono

Após a criação de um pedido:

1. O `order-service` publica um evento no Kafka
2. O `payment-service` processa o pagamento
3. O `notification-service` consome eventos
4. O sistema envia notificações ao usuário

---

# Tecnologias Utilizadas

## Backend

- Java
- Spring Boot
- Spring Cloud
- Spring Validation
- Spring Data JPA
- Spring Data MongoDB
- Maven

## Microsserviços

- Netflix Eureka
- Spring Cloud Gateway
- OpenFeign

## Banco de Dados

### PostgreSQL

Utilizado nos microsserviços relacionais:

- product-service
- order-service
- payment-service
- notification-service

Estratégia utilizada:
- banco único
- múltiplos schemas por microsserviço
- Flyway para versionamento

### MongoDB

Utilizado no:
- customer-service

A modelagem orientada a documentos permite:
- alta flexibilidade
- documentos embutidos
- armazenamento de múltiplos endereços

## Mensageria

- Apache Kafka

Eventos:
- order-created
- payment-confirmed
- notification-created

## Infraestrutura

- Docker
- Docker Compose

## Observabilidade

- ElasticSearch
- Zipkin

---

# Estrutura do Projeto

```text
ecommerce-microservices
│
├── services
│   ├── customer-service
│   ├── product-service
│   ├── order-service
│   ├── payment-service
│   ├── notification-service
│   ├── api-gateway
│   └── discovery-service
│
├── infrastructure
│   ├── docker
│   ├── kafka
│   ├── elasticsearch
│   ├── zipkin
│   └── databases
│
├── diagrams
│   ├── architecture.drawio
│   └── architecture.png
│
├── resources
│   ├── postman
│   ├── payloads
│   └── collections
│
└── README.md
```

---

# Persistência

## PostgreSQL

Estratégia baseada em:
- banco único
- schemas separados por domínio

Schemas:

```text
product
orders
payment
notification
```

Cada microsserviço gerencia:
- suas migrations
- seu schema
- suas regras de domínio

---

## MongoDB

O `customer-service` utiliza modelagem orientada a documentos.

Exemplo:

```json
{
  "_id": "customer-id",
  "firstName": "Riquelme",
  "lastName": "Maia",
  "email": "riquelme@email.com",
  "addresses": [
    {
      "street": "Rua A",
      "number": "100",
      "city": "Sapiranga",
      "state": "RS"
    }
  ]
}
```

---

# Comunicação Entre Serviços

## REST

Utilizada para operações síncronas:

- Consulta de produtos
- Consulta de clientes
- Criação de pedidos
- Consulta de estoque

---

## Eventos com Kafka

Utilizada para operações assíncronas:

- Pedido criado
- Pagamento aprovado
- Envio de notificações

---

# Padrões Utilizados

- DTO Pattern
- Converter Pattern
- Exception Handler Pattern
- Service Layer Pattern
- Repository Pattern
- Event Driven Architecture
- API Gateway Pattern
- Database per Service (logical isolation)

---

# Objetivos do Projeto

- Estudar arquitetura de microsserviços
- Aplicar Event Driven Architecture
- Simular ambiente distribuído real
- Trabalhar com comunicação síncrona e assíncrona
- Utilizar observabilidade distribuída
- Trabalhar com containers
- Evoluir conhecimentos em sistemas escaláveis

---

# Possíveis Evoluções

- Keycloak
- Prometheus + Grafana
- Resilience4j
- Kubernetes
- Saga Pattern
- Redis
- Outbox Pattern
- CDC com Debezium
- GitHub Actions

---

# Como Executar

## Pré-requisitos

- Java 21+
- Docker
- Docker Compose
- Maven

---

## Executando a infraestrutura

```bash
docker compose up -d
```

---

## Executando os serviços

Execute os serviços na seguinte ordem:

1. discovery-service
2. api-gateway
3. microsserviços

---

# Serviços disponíveis

| Serviço | Porta |
|---|---|
| API Gateway | 8080 |
| Discovery Service | 8761 |
| PostgreSQL | 5432 |
| MongoDB | 27017 |
| Mongo Express | 8081 |
| pgAdmin | 5050 |
| MailDev | 1080 |
| Zipkin | 9411 |

---

# Diagramas

A pasta `diagrams` contém os diagramas arquiteturais do projeto.

---

# Status do Projeto

🚧 Em desenvolvimento

Projeto criado para fins de estudo, experimentação e evolução técnica em arquitetura distribuída.
