package br.com.fiap.locadora.main.filme;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FilmeDao;
import br.com.fiap.locadora.model.Filme;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class ListarPorDiretorDaoTest {
    public static void main(String[] args) {

        // Pedir o nome do diretor
        String diretor = JOptionPane.showInputDialog("Insira o nome do diretor");

        try (Connection conn = ConnectionFactory.getConnection()) {
            // Realizando a conexão com o banco e a instanciação do DAO
            FilmeDao dao = new FilmeDao(conn);

            // Criando a lista de exibição
            List<Filme> lista = dao.pesquisaDiretor(diretor);

            // Verificando se a lista está vazia
            if (lista.isEmpty()) {
                System.out.println("Nenhum filme encontrado para o diretor: " + diretor);
            } else {
                for (Filme f : lista) {
                    System.out.println(f);
                }
                System.out.println("Filmes encontrados: " + lista.size());
            }

        } catch (Exception e) {
            System.err.println("Erro ao listar filmes por diretor: " + e.getMessage());
        }
    }
}
