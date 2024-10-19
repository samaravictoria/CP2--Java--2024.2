package br.com.fiap.locadora.main.funcionario;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FuncionarioDao;
import br.com.fiap.locadora.model.Funcionario;

import javax.swing.*;
import java.sql.Connection;

public class PesquisarPorIdDaoTest {
    public static void main(String[] args) {

        // Pedir o ID para o usuário
        int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID do funcionário"));

        // Usando try-with-resources para garantir que a conexão seja fechada
        try (Connection conn = ConnectionFactory.getConnection()) {
            // Criando o DAO da locadora
            FuncionarioDao dao = new FuncionarioDao(conn);

            Funcionario funcionario = dao.pesquisarId(id);

            // Verificando se o funcionário foi encontrado
            if (funcionario != null) {
                // Exibindo os dados pesquisados
                System.out.println(funcionario);
            } else {
                System.out.println("Nenhum funcionário encontrado com o ID: " + id);
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar funcionário: " + e.getMessage());
        }
    }
}
