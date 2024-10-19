package br.com.fiap.locadora.model;

import lombok.*;

// Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Filme {

    private int id;
    private String nome;
    private String diretor;
    private int anoLancamento;
    private Genero genero;
    private String sinopse;
    private int duracao;
    private String classificacao;

    // Foreign Key
    private Locadora locadora;

    public Filme(int id, String nome, String nomeDiretor, int ano, Genero genero, String sinopse, int duracao, String classificacao) {
    }

    public Filme(String nome, String diretor, int ano, String genero, String sinopse, int duracao, String classificacao) {
    }

    public Filme(int id, String nome, String diretor, int ano, String genero, String sinopse, int duracao, String classificacao) {
    }
}
