package br.com.fiap.locadora.main.aluguel;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.AluguelDao;
import br.com.fiap.locadora.model.Aluguel;

import javax.swing.*;
import java.sql.Connection;

public class PesquisaPorIdDaoTest {
    public static void main(String[] args) {
        // Perguntando o ID para o usuário
        int id = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do aluguel?"));
        Connection conn = null;

        try {
            // Criando o DAO e a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();
            AluguelDao dao = new AluguelDao(conn);

            Aluguel aluguel = dao.pesquisaId(id);

            // Verificando se o aluguel foi encontrado
            if (aluguel != null) {
                // Exibindo os dados do aluguel
                System.out.println(aluguel);
            } else {
                System.out.println("Nenhum aluguel encontrado com o ID: " + id);
            }

        } catch (Exception e) {
            System.err.println("Erro ao pesquisar aluguel: " + e.getMessage());
        } finally {
            // Fechando a conexão com o banco de dados
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    System.err.println("Erro ao fechar a conexão: " + e.getMessage());
                }
            }
        }
    }
}
