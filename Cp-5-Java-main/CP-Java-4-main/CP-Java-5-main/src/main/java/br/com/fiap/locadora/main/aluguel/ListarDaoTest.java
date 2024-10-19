package br.com.fiap.locadora.main.aluguel;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.AluguelDao;
import br.com.fiap.locadora.model.Aluguel;

import java.sql.Connection;
import java.util.List;

public class ListarDaoTest {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            // Criando a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();
            AluguelDao dao = new AluguelDao(conn);

            // Listando os aluguéis
            List<Aluguel> lista = dao.listar();

            if (lista.isEmpty()) {
                System.out.println("Nenhum aluguel encontrado.");
            } else {
                for (Aluguel aluguel : lista) {
                    System.out.println(aluguel);
                }
                System.out.println("Total de aluguéis encontrados: " + lista.size());
            }

        } catch (Exception e) {
            System.err.println("Erro ao listar aluguéis: " + e.getMessage());
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
