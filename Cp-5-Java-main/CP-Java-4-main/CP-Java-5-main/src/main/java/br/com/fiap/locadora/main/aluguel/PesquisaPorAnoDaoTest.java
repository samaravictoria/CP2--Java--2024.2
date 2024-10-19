package br.com.fiap.locadora.main.aluguel;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.AluguelDao;
import br.com.fiap.locadora.model.Aluguel;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class PesquisaPorAnoDaoTest {
    public static void main(String[] args) {
        // Pedindo a data de devolução do aluguel
        String dtDevolucao = JOptionPane.showInputDialog("Qual a data de devolução?");
        Connection conn = null;

        try {
            // Criando a conexão com o banco de dados e o DAO do aluguel
            conn = ConnectionFactory.getConnection();
            AluguelDao dao = new AluguelDao(conn);

            // Criando a lista de exibição
            List<Aluguel> lista = dao.pesquisaDtDevolucao(dtDevolucao);

            // Verificando se a lista está vazia
            if (lista.isEmpty()) {
                System.out.println("Nenhum aluguel encontrado para a data de devolução: " + dtDevolucao);
            } else {
                // Exibindo os dados do aluguel
                for (Aluguel a : lista) {
                    System.out.println(a + "\n");
                }
                System.out.println("Aluguéis encontrados: " + lista.size());
            }

        } catch (Exception e) {
            System.err.println("Erro ao pesquisar aluguéis: " + e.getMessage());
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
