package br.com.fiap.locadora.main.cliente;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.ClienteDao;

import javax.swing.*;
import java.sql.Connection;

public class DeleteDaoTest {
    public static void main(String[] args) {
        // Pedir o ID ao usuário
        String input = JOptionPane.showInputDialog("Insira o ID do cliente que você deseja deletar: ");

        // Validar a entrada
        if (input == null || !input.matches("\\d+")) {
            System.err.println("Por favor, insira um ID válido.");
            return;
        }

        int id = Integer.parseInt(input);

        try (Connection conn = ConnectionFactory.getConnection()) {
            // Instanciando o DAO do cliente
            ClienteDao dao = new ClienteDao(conn);

            // Executando o delete
            dao.apagar(id);
            System.out.println("Cliente apagado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao apagar cliente: " + e.getMessage());
        }
    }
}
