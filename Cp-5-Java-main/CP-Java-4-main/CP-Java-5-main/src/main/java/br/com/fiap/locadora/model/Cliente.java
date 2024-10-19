package br.com.fiap.locadora.model;

import lombok.*;

import java.time.LocalDate;

// Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente extends Pessoa {

    private String endereco;
    private String email;

    // Construtor que chama o construtor da classe Pessoa
    public Cliente(int id, String cpf, String nome, LocalDate dtNasc, String endereco, String email) {
        super(id, cpf, nome, dtNasc);
        this.endereco = endereco;
        this.email = email;
    }

    public Cliente(String cpf, String nome, String dtNasc, String endereco, String email) {
    }

    public Cliente(int id, String cpf, String nome, String dtNasc, String endereco, String email) {
    }
}
