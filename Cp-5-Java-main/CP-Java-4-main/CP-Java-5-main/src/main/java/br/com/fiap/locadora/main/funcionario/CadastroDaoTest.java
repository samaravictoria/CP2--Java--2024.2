package br.com.fiap.locadora.main.funcionario;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FuncionarioDao;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Funcionario;
import br.com.fiap.locadora.model.Locadora;

import javax.swing.*;
import java.sql.Connection;

public class CadastroDaoTest {
    public static void main(String[] args) {

        // Pedir as informações ao usuário
        String cpf = JOptionPane.showInputDialog("Insira seu CPF: ");
        String nome = JOptionPane.showInputDialog("Insira seu nome: ");
        String dtNasc = JOptionPane.showInputDialog("Insira sua data de nascimento: ");
        int anoAdmissao = Integer.parseInt(JOptionPane.showInputDialog("Em qual ano este funcionário foi contratado?"));
        int idLoca = Integer.parseInt(JOptionPane.showInputDialog("Qual o id da locadora em que " +
                "esse funcionário trabalha?"));

        try (Connection conn = ConnectionFactory.getConnection()) {
            // Criando o DAO do Funcionário
            FuncionarioDao dao = new FuncionarioDao(conn);
            Funcionario funcionario = new Funcionario(cpf, nome, dtNasc, anoAdmissao);

            // Pesquisar a locadora
            LocadoraDao locaDao = new LocadoraDao(conn);
            Locadora locadora = locaDao.pesquisaId(idLoca);

            if (locadora != null) {
                funcionario.setLocadora(locadora);
            } else {
                System.out.println("Locadora não encontrada para o ID fornecido.");
                return; // Encerrar o processo se a locadora não for encontrada
            }

            dao.cadastrar(funcionario);
            System.out.println("Funcionário " + funcionario.getNome() + " cadastrado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }
}
