package br.com.fiap.locadora.main.filme;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FilmeDao;
import br.com.fiap.locadora.model.Filme;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class PesquisaPorAnoDaoTest {
    public static void main(String[] args) {

        // Pedir o ano de lançamento para o usuário
        int ano = Integer.parseInt(JOptionPane.showInputDialog("Insira o ano para se pesquisar"));

        try (Connection conn = ConnectionFactory.getConnection()) {
            // Criando a conexão com o banco e o DAO
            FilmeDao dao = new FilmeDao(conn);

            // Criando a lista de exibição e exibindo os dados encontrados
            List<Filme> lista = dao.pesquisaAnoLancamento(ano);

            if (lista.isEmpty()) {
                System.out.println("Nenhum filme encontrado para o ano: " + ano);
            } else {
                for (Filme f : lista) {
                    System.out.println(f + "\n");
                }
                System.out.println("Filmes encontrados que foram lançados em " + ano + ": " + lista.size());
            }

        } catch (Exception e) {
            System.err.println("Erro ao pesquisar filmes: " + e.getMessage());
        }
    }
}
