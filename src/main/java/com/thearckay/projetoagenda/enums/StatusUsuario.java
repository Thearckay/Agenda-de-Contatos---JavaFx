package com.thearckay.projetoagenda.enums;

public enum StatusUsuario {
    SUCESSO("Sucesso ao realizar a operação! "),
    NOME_VAZIO("Nome vazio!"),
    TELEFONE_VAZIO("Numero de telefone vazio!"),
    LOCALIZACAO_VAZIA("Localização vazia!"),
    EMAIL_VAZIO("Email vazio!"),
    EMAIL_INDISPONIVEL("O email já está em uso"),
    EMAIL_DISPONIVEL("Email disponivel para cadastro!"),
    SENHA_VAZIA("Senha vazia!"),
    CREDENCIAIS_INVALIDAS("Email ou senha incorretos"),
    DATA_NASCIMENTO_VAZIA("Data de nascimento vazio!"),
    EMAIL_JA_ESTA_EM_USO("O email digitado já está em uso!"),
    ID_INVALIDO("O id do usuário é inválido para executar a ação!");

    private String mensagem;
    StatusUsuario(String mensagem){
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
