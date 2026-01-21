# Agenda de Contatos - JavaFX

![Tela Principal](/img/editado.png)

> Sistema desktop para gerenciamento de contatos desenvolvido em JavaFX, utilizando arquitetura MVC e padrão DAO para persistência de dados.

![Status](https://img.shields.io/badge/Status-Concluído-green)
![Java](https://img.shields.io/badge/Java-21-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-UI-blue)

## Sobre o Projeto

Este projeto foi desenvolvido com o objetivo principal de testar, aprender e aprimorar minhas **skills em Java**.

Embora seja um sistema funcional, o foco foi o desafio técnico e a aplicação de conceitos de Engenharia de Software em uma aplicação Desktop.

---

## Arquitetura e Decisões de Projeto

O padrão arquitetural escolhido foi o **MVC (Model-View-Controller)**, devido à sua popularidade e eficiência na separação de responsabilidades.

### Por que essa estrutura de navegação?

Sobre a modularização do FXML e do Dashboard, tomei uma decisão consciente de design voltada para o aprendizado:

> "Seria perfeitamente possível utilizar uma única tela (`ListaContatos`) e apenas alternar a lógica do Controller para exibir 'Favoritos' ou 'Deletados'. Contudo, como este foi um desafio que propus a mim mesmo, **optei por criar Scenes específicas e modularizar o FXML**. Meu objetivo foi treinar e desenvolver habilidades de **transição de cenas** e **reutilização de componentes**, saindo da zona de conforto".

---

## Tecnologias Utilizadas

Como meu foco principal é o **Backend**, utilizei ferramentas para auxiliar na construção da interface visual:

* **Java 21 & JavaFX:** Core da aplicação.
* **Scene Builder:** Integrado ao **IntelliJ IDEA Community** para a construção visual das telas FXML.
* **Google Stitch:** Utilizado para gerar a paleta de cores e o design inicial da interface (IA).
* **MySQL:** Banco de dados relacional.
* **JDBC:** Para conexão e manipulação dos dados (DAO).

---

## Como executar

1.  Clone o repositório.
2.  Configure o banco de dados MySQL (script disponível no projeto).
3.  Execute a classe `Main.java` através da sua IDE.

---

## Autor

Desenvolvido por **Kayck Arcanjo**.
