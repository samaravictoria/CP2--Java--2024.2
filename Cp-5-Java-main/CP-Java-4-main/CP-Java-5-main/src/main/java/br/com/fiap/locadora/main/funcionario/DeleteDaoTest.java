package br.com.fiap.locadora.main.funcionario;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FuncionarioDao;

import javax.swing.*;

public class DeleteDaoTest {
    public static void main(String[] args) {

        // Pedindo o id para o usuário
        int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o id do funcionário que você deseja deletar"));

        try (var conn = ConnectionFactory.getConnection()) {
            // Setando o DAO e a conexão com o banco de dados
            FuncionarioDao dao = new FuncionarioDao(conn);

            // Verifica se o funcionário existe antes de tentar deletar
            if (dao.pesquisaId(id) != null) {
                dao.apagar(id);
                System.out.println("Funcionário deletado com sucesso!");
            } else {
                System.out.println("Funcionário com ID " + id + " não encontrado.");
            }

        } catch (Exception e) {
            System.err.println("Erro ao deletar funcionário: " + e.getMessage());
        }
    }
}
