package br.com.fiap.locadora.main.aluguel;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.AluguelDao;
import br.com.fiap.locadora.model.Aluguel;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class PesquisaIdFilme {
    public static void main(String[] args) {
        // Pedir o ID do filme
        int idFilme = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do filme?"));
        Connection conn = null;

        try {
            // Criando a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();
            AluguelDao dao = new AluguelDao(conn);

            // Pesquisar aluguéis pelo ID do filme
            List<Aluguel> lista = dao.pesquisaIdFilme(idFilme);

            // Verificando se a lista está vazia
            if (lista.isEmpty()) {
                System.out.println("Nenhum aluguel encontrado para o filme com ID: " + idFilme);
            } else {
                // Exibindo os dados do aluguel
                for (Aluguel a : lista) {
                    System.out.println(a + "\n");
                }
                System.out.println("Quantidade de aluguéis encontrados: " + lista.size());
            }

        } catch (Exception e) {
            System.err.println("Erro ao pesquisar aluguéis do filme: " + e.getMessage());
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
