package br.com.fiap.locadora.main.filme;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FilmeDao;
import br.com.fiap.locadora.model.Filme;

import java.sql.Connection;
import java.util.List;

public class ListarDaoTest {
    public static void main(String[] args) {

        try (Connection conn = ConnectionFactory.getConnection()) {
            // Criando o DAO
            FilmeDao dao = new FilmeDao(conn);

            // Criando a lista de exibição
            List<Filme> lista = dao.listar();

            // Exibindo os dados encontrados
            if (lista.isEmpty()) {
                System.out.println("Nenhum filme encontrado.");
            } else {
                for (Filme f : lista) {
                    System.out.println(f);  // Você pode formatar a saída aqui, se necessário
                }
                System.out.println("Filmes encontrados: " + lista.size());
            }

        } catch (Exception e) {
            System.err.println("Erro ao listar filmes: " + e.getMessage());
        }
    }
}
