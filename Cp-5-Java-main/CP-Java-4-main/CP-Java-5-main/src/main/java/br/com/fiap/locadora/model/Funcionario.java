package br.com.fiap.locadora.model;

import lombok.*;

import java.time.LocalDate;

// Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Funcionario extends Pessoa {

    private int anoAdmissao;
    private String cpf;
    private LocalDate dtNasc;
    private Locadora locadora;

    // Construtor com todos os atributos
    public Funcionario(int id, String cpf, String nome, LocalDate dtNasc, int anoAdmissao, Locadora locadora) {
        super(id, cpf, nome, dtNasc);
        this.cpf = cpf;
        this.dtNasc = dtNasc;
        this.anoAdmissao = anoAdmissao;
        this.locadora = locadora;
    }

    public Funcionario(String cpf, String nome, String dtNasc, int anoAdmissao) {
    }

    public Funcionario(int id, String cpf, String nome, String dtNasc, int anoAdmissao) {
    }

    public Funcionario(int id, String cpf, String nome, LocalDate dtNasc, int anoAdmissao) {
    }
}
