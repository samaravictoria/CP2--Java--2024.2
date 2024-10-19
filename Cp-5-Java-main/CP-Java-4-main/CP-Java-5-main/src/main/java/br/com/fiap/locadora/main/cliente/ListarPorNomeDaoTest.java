package br.com.fiap.locadora.main.cliente;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.ClienteDao;
import br.com.fiap.locadora.model.Cliente;

import javax.swing.*;
import java.util.List;

public class ListarPorNomeDaoTest {
    public static void main(String[] args) {
        // Pedir o nome do cliente
        String nome = JOptionPane.showInputDialog("Qual o nome do cliente que você deseja pesquisar?");

        try (var conn = ConnectionFactory.getConnection()) {
            // Instanciando o DAO e a conexão com o banco
            ClienteDao dao = new ClienteDao(conn);

            // Criando a lista de exibição e exibindo os dados
            List<Cliente> lista = dao.pesquisaNome(nome);

            if (lista.isEmpty()) {
                System.out.println("Nenhum cliente encontrado com o nome: " + nome);
            } else {
                for (Cliente c : lista) {
                    System.out.println(c + "\n");
                }
                System.out.println("Clientes encontrados: " + lista.size());
            }
        } catch (Exception e) {
            System.err.println("Erro ao pesquisar clientes: " + e.getMessage());
        }
    }
}
