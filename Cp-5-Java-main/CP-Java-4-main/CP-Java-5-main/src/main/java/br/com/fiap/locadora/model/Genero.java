package br.com.fiap.locadora.model;

public enum Genero {
    ACAO,
    AVENTURA,
    ANIMACAO,
    COMEDIA,
    DRAMA,
    TERROR,
    FICCAO_CIENTIFICA,
    FANTASIA,
    ROMANCE,
    MISTERIO,
    SUSPENSE,
    DOCUMENTARIO,
    BIOGRAFIA,
    MUSICAL,
    HISTORICO,
    GUERRA,
    FAMILIA,
    ESPORTE,
    NOIR,
    THRILLER;


    @Override
    public String toString() {

        return this.name().replace("_", " ").toLowerCase();
    }
}
