## 📋 Sumário
- [📦 Projeto Inbound – Simulação de um Sistema WMS](#projeto-inbound--simulação-de-um-sistema-wms)
  - [📌 Appointment](#-appointment-agendamento-das-mercadorias)
  - [🚚 Receiving](#-receiving-recebimento-da-mercadorias-na-doca)
  - [📦 Checking](#-checking-conferência-do-itens)
- [🏛️ Arquitetura](#-arquitetura)
---
# Projeto Inbound – Simulação de um Sistema WMS

O Projeto Inbound tem como objetivo simular um Sistema WMS (Warehouse Management System) responsáveis pela gestão e automação dos processos operacionais dentro de um armazém logistico.

O fluxo de Inbound (entrada de mercadorias), que será automatizado pelo sistema WMS, envolve algumas das seguintes etapas:

### 📌 Appointment (Agendamento das mercadorias)

### 🚚 Receiving (Recebimento da mercadorias na doca)

### 📦 Checking (Conferência do itens)

---
## 🏛️ Arquitetura

Cada aplicação do Projeto Inbound está organizada em três camadas distintas, seguindo os princípios de **Arquitetura Hexagonal** (Ports and Adapters) e **Clean Architecture**, garantindo separação de responsabilidades, testabilidade e independência de frameworks.

Nesse modelo de arquitetura as dependências apontam para dentro, em direção ao núcleo da aplicação, preservando o domínio das regras de negócio e evitando acoplamento com detalhes externos como frameworks, banco de dados ou interfaces.
```
               🧑‍💻 Mundo Externo
                        │
                        ▼
╔═══════════════════════════════════════════════╗
║                  ENTRYPOINT                   ║
║-----------------------------------------------║
║ 🔌 Adaptadores de Entrada (Driving):          ║
║   • Controller (REST)                         ║
║   • DTOs → Conversão → Domain                 ║
╚═══════════════════════▲═══════════════════════╝
                        │
                        │ chama casos de uso
                        ▼
╔═══════════════════════════════════════════════╗
║              ⚙️ CORE / DOMÍNIO                ║
║-----------------------------------------------║
║  🧠 Regras de Negócio                         ║
║   • Domain                                    ║
║                                               ║
║  🚪 Porta de Entrada                          ║
║   • Casos de uso                              ║
║                                               ║
║  🚪 Porta de Saída - Interface                ║
║   • Gateway                                   ║
╚═══════════════════════▲═══════════════════════╝
                        │
                        │ implementação real
                        ▼ 
╔═══════════════════════════════════════════════╗
║                  INFRA                        ║
║-----------------------------------------------║
║ 🔌 Adaptadores de Saída (Driven):             ║
║   • GatewayImp → Repository → DB              ║
╚═══════════════════════════════════════════════╝

```
---