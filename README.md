# 🏛 API Bancária - Bootcamp Santander 2024

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)


# 🎯 Objetivo Principal

O objetivo principal era colocar em prática todo o conhecimento adquirido durante o curso, criando uma API Bancária seguindo as melhores condutas dos padrões de projeto. Além de ser uma API segura, com consultas externas e testes para garantir a escalabilidade do projeto.


# 🎨 Diagrama do Projeto

```mermaid
classDiagram
  class Account {
    +id: UUID
    +number: Long
    +agency: string
    +balance: dobule
    +limit: double
  }
  
  class Card {
    +id: UUID
    +number: number
    +type:CardType
    +limit: double
  }
  
  class Client {
    +cpf: string
    +name: string
    +password: string
    +role: UserRole
    +birthdate: Date
    +complement: string
    +number: string
  }

  
  class Address {
    +id: string
    +cep: string
    +street: string
    +district: string
    +city: string
    +uf: string
  }
  class CardType{
    +id: int
    +description: string
  }
  
  class PaymentMethod {
    +id: string
    +icon: string
    +description: string
  }
  
  Account <--  Client
  Account <-- Card
Client <-- Address
Card <-- CardType

```



