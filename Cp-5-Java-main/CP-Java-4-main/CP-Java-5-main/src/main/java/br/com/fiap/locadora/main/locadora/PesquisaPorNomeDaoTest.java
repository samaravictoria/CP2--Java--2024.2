package br.com.fiap.locadora.main.locadora;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Locadora;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class PesquisaPorNomeDaoTest {
    public static void main(String[] args) {

        // Pedir o nome da locadora desejada
        String nome = JOptionPane.showInputDialog("Insira o nome da Locadora que você deseja pesquisar");

        // Usando try-with-resources para garantir que a conexão seja fechada
        try (Connection conn = ConnectionFactory.getConnection()) {
            LocadoraDao dao = new LocadoraDao(conn);

            List<Locadora> lista = dao.pesquisarNome(nome);

            // Verificando se a lista está vazia
            if (lista.isEmpty()) {
                System.out.println("Nenhuma locadora encontrada com o nome informado.");
            } else {
                // Percorrendo o método e imprimindo os valores encontrados
                System.out.println("Locadoras encontradas:");
                for (Locadora l : lista) {
                    System.out.println(l + "\n"); // Aqui você pode formatar a saída conforme necessário
                }
                System.out.println("Total de locadoras encontradas: " + lista.size());
            }

        } catch (Exception e) {
            System.err.println("Erro ao pesquisar locadoras: " + e.getMessage());
        }
    }
}
