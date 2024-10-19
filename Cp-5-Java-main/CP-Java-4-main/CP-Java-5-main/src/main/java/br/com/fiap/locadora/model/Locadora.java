package br.com.fiap.locadora.model;

import lombok.*;

// Lombok
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Locadora {

    private int id;
    private String nome;
    private String endereco;
    private String telefone;

    public Locadora(String nome, String endereco) {
    }
}
