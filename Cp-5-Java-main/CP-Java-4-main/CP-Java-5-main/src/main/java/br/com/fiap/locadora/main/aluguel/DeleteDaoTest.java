package br.com.fiap.locadora.main.aluguel;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.AluguelDao;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class DeleteDaoTest {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            // Pedindo o ID do aluguel para o usuário
            int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID do aluguel que deseja deletar"));

            // Criando a conexão com o banco e o DAO
            conn = ConnectionFactory.getConnection();
            AluguelDao dao = new AluguelDao(conn);

            // Executando o delete
            dao.deletar(id);
            System.out.println("Aluguel deletado com sucesso!");

        } catch (NumberFormatException e) {
            System.err.println("Por favor, insira um número válido para o ID.");
        } catch (SQLException e) {
            System.err.println("Erro ao acessar o banco de dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        } finally {
            // Fechando a conexão com o banco de dados
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar a conexão: " + e.getMessage());
                }
            }
        }
    }
}
