package br.com.fiap.locadora.main.locadora;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Locadora;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class PesquisaEnderecoDaoTest {
    public static void main(String[] args) {

        // Pedir o endereço ao usuário
        String endereco = JOptionPane.showInputDialog("Insira o endereço do qual você deseja ver as locadoras");

        // Usando try-with-resources para garantir que a conexão seja fechada
        try (Connection conn = ConnectionFactory.getConnection()) {
            LocadoraDao dao = new LocadoraDao(conn);

            // Criando a lista para exibir as informações
            List<Locadora> lista = dao.pesquisaEndereco(endereco);

            // Verificando se a lista está vazia
            if (lista.isEmpty()) {
                System.out.println("Nenhuma locadora encontrada para o endereço informado.");
            } else {
                System.out.println("Locadoras encontradas:");
                for (Locadora l : lista) {
                    System.out.println(l); // Aqui você pode formatar a saída conforme necessário
                }
            }

            System.out.println("\nTotal de locadoras encontradas: " + lista.size());

        } catch (Exception e) {
            System.err.println("Erro ao pesquisar locadoras: " + e.getMessage());
        }
    }
}
