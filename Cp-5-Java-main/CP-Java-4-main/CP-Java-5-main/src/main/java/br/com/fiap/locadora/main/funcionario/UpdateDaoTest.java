package br.com.fiap.locadora.main.funcionario;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FuncionarioDao;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Funcionario;
import br.com.fiap.locadora.model.Locadora;

import javax.swing.*;
import java.sql.Connection;

public class UpdateDaoTest {
    public static void main(String[] args) {

        // Pedir os dados para o usuário
        int id = Integer.parseInt(JOptionPane.showInputDialog("Qual o ID do funcionário que você deseja modificar?"));
        String cpf = JOptionPane.showInputDialog("Insira o novo CPF: ");
        String nome = JOptionPane.showInputDialog("Insira o novo nome: ");
        String dtNasc = JOptionPane.showInputDialog("Insira a nova data de nascimento: ");
        int anoAdmissao = Integer.parseInt(JOptionPane.showInputDialog("Em qual ano este funcionário foi contratado?"));
        int idLoca = Integer.parseInt(JOptionPane.showInputDialog("Qual o ID da locadora em que " +
                "esse funcionário trabalha?"));

        // Verificar se CPF e nome não estão vazios
        if (cpf == null || cpf.trim().isEmpty() || nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "CPF e Nome não podem ser vazios.");
            return;
        }

        Funcionario funcionario = new Funcionario(id, cpf, nome, dtNasc, anoAdmissao);

        // Usando try-with-resources para garantir que a conexão seja fechada
        try (Connection conn = ConnectionFactory.getConnection()) {
            FuncionarioDao daoFunc = new FuncionarioDao(conn);
            LocadoraDao locaDao = new LocadoraDao(conn);
            Locadora locadora = locaDao.pesquisaId(idLoca);

            if (locadora == null) {
                JOptionPane.showMessageDialog(null, "Locadora não encontrada com o ID: " + idLoca);
                return;
            }

            funcionario.setLocadora(locadora);
            daoFunc.atualizar(funcionario);
            JOptionPane.showMessageDialog(null, "Funcionário atualizado com sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar funcionário: " + e.getMessage());
        }
    }
}
