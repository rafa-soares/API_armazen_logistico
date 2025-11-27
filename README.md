## 📋 Sumário
- [🎯 Objetivo da Aplicação](#-objetivo-da-aplicação)
    - [📌 Agendamento](#-agendamento)
    - [🚚 Receiving (Recebimento na Doca)](#-receiving-recebimento-na-doca)
    - [📦 Checking (Conferência)](#-checking-conferência)
    - [🏁 Finalização](#-finalização)
- [✅ O sistema garante](#-o-sistema-garante)
- [🎯 Arquitetura](#-arquitetura)
- [🎯 Benefícios Alcançados](#-benefícios-alcançados)
    - [Testabilidade](#-testabilidade)
    - [Independência de Frameworks](#-independência-de-frameworks)
    - [Independência de UI](#-independência-de-ui)
    - [Independência de Banco de Dados](#-independência-de-banco-de-dados)
    - [Regra de Negócio Isolada](#-regra-de-negócio-isolada)
- [📊 Esquema Visual da Arquitetura Hexagonal](#-esquema-visual-da-arquitetura-hexagonal)

---

# 🎯 Objetivo da Aplicação

Esta aplicação tem como objetivo gerenciar o processo de recebimento de produtos no armazém, desde o agendamento do inbound (realizado pelo seller), passando pelo recebimento na doca e pela conferência item a item, até a finalização automática quando todos os itens forem conferidos corretamente.

## 📌 Agendamento

- Registra novos inbounds (lotes de mercadorias) e seus respectivos itens
- Cada item inicia com status `open`
- Define a data prevista de entrega e o SLA de recebimento no armazém

## 🚚 Receiving (Recebimento na Doca)

- O operador logístico inicia o recebimento do inbound quando o caminhão chega
- Priorização do atendimento por ordem de cadastro (**FIFO**)

## 📦 Checking (Conferência)

- Conferência individual dos itens por bipagem
- Itens são atualizados para status `confirmed`
- Geração de eventos a cada item conferido para rastreabilidade

## 🏁 Finalização

- Ao atingir 100% dos itens com status `confirmed`
- O inbound é automaticamente atualizado para status `finished`
- Libera o fluxo para integração com putaway/armazenagem

---

## ✅ O sistema garante

- Organização dos lotes recebidos
- Rastreabilidade completa por inbound e item
- Cumprimento de SLA definido no agendamento
- Automação baseada em eventos e estados

---

## 🏛️ Arquitetura

A aplicação está organizada em três camadas distintas, seguindo os princípios de **Arquitetura Hexagonal** (Ports and Adapters) e **Clean Architecture**, garantindo separação de responsabilidades, testabilidade e independência de frameworks.

A aplicação **está seguindo Arquitetura Hexagonal e Clean Architecture** com:

✅ **Separação clara de camadas** (core, entrypoint, infra)  
✅ **Inversão de dependências** (interfaces de gateway)  
✅ **Portas e Adaptadores** bem definidos  
✅ **Núcleo independente** de frameworks  
✅ **Fluxo de dependência correto** (aponta para dentro)
✅ **Separação de entidades**, cada uma com seu propósito:
- **InboundDomain** (core/domain) → Entidade de negócio pura
- **InboundRequestDTO/ResponseDTO** (entrypoint) → Contratos externos
- **Inbound** (infra/model) → Entidade de persistência (JPA)

---

## ✨ Benefícios Alcançados

### ✅ Testabilidade
- Use Cases podem ser testados isoladamente
- Mocks podem substituir gateways facilmente
- Testes unitários não precisam de Spring Context

### ✅ Independência de Frameworks
- Core não depende de Spring ou JPA
- Possível trocar Spring por outro framework sem alterar lógica
- Regras de negócio portáveis

### ✅ Independência de UI
- Lógica não conhece HTTP/REST
- Fácil adicionar GraphQL, gRPC, CLI, etc.
- Múltiplos adaptadores de entrada possíveis

### ✅ Independência de Banco de Dados
- Use Cases trabalham com InboundDomain
- Possível trocar JPA por outro ORM ou NoSQL
- Repositórios podem ser mockados

### ✅ Regra de Negócio Isolada
- Toda lógica está em `core/`
- Fácil de entender e manter
- Mudanças em frameworks não afetam o negócio

---

## 📊 Esquema Visual da Arquitetura

Um dos princípios fundamentais da Clean Architecture é a **Regra de Dependência**: as dependências apontam sempre para dentro, em direção ao núcleo.
```
               🧑‍💻 Mundo Externo
                        │
                        ▼
╔═══════════════════════════════════════════════╗
║                  ENTRYPOINT                   ║
║-----------------------------------------------║
║ 🔌 Adaptadores de Entrada (Driving):          ║
║   • InboundController (REST)                  ║
║   • DTOs → Conversão → Domain                 ║
╚═══════════════════════▲═══════════════════════╝
                        │
                        │ chama casos de uso
                        ▼
╔═══════════════════════════════════════════════╗
║              ⚙️ CORE / DOMÍNIO                ║
║-----------------------------------------------║
║  🧠 Regras de Negócio                         ║
║   • InboundDomain                             ║
║                                               ║
║  🚪 Porta de Entrada - Casos de Uso           ║
║   • CreateInbound                             ║
║   • FindInboundByCode                         ║
║                                               ║
║  🚪 Porta de Saída - Interface                ║
║   • InboundGateway                            ║
╚═══════════════════════▲═══════════════════════╝
                        │
                        │ implementação real
                        ▼ 
╔═══════════════════════════════════════════════╗
║                  INFRA                        ║
║-----------------------------------------------║
║ 🔌 Adaptadores de Saída (Driven):             ║
║   • InboundGatewayImp → Repository → DB       ║
╚═══════════════════════════════════════════════╝

```
