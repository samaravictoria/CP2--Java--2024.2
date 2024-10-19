package br.com.fiap.locadora.model;

import lombok.*;

import java.time.LocalDate;

// Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Pessoa {

    private int id;
    private String cpf;
    private String nome;
    private LocalDate dtNasc;

}
