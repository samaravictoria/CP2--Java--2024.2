package br.com.fiap.locadora.main.cliente;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.ClienteDao;
import br.com.fiap.locadora.model.Cliente;

import javax.swing.*;
import java.sql.Connection;

public class BuscarIdDaoTest {
    public static void main(String[] args) {
        // Perguntar o ID que quer pesquisar
        int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID que deseja pesquisar: "));

        try (Connection conn = ConnectionFactory.getConnection()) {
            // Criando o DAO
            ClienteDao dao = new ClienteDao(conn);

            Cliente cliente = dao.pesquisaId(id);

            // Verificando se o cliente foi encontrado
            if (cliente != null) {
                // Exibindo os dados encontrados
                System.out.println(cliente);
            } else {
                System.out.println("Cliente não encontrado com o ID: " + id);
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        }
    }
}
