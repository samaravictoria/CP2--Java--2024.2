package br.com.fiap.locadora.main.cliente;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.ClienteDao;
import br.com.fiap.locadora.model.Cliente;

import javax.swing.*;
import java.sql.Connection;

public class UpdateDaoTest {
    public static void main(String[] args) {
        // Pedindo as informações ao usuário
        int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID do cliente que você deseja atualizar: "));
        String cpf = JOptionPane.showInputDialog("Insira o novo CPF: ");
        String nome = JOptionPane.showInputDialog("Insira o novo nome: ");
        String dtNasc = JOptionPane.showInputDialog("Insira a nova data de nascimento: ");
        String endereco = JOptionPane.showInputDialog("Insira o novo endereço: ");
        String email = JOptionPane.showInputDialog("Insira o novo Email: ");

        try (Connection conn = ConnectionFactory.getConnection()) {
            // Criando o DAO
            ClienteDao dao = new ClienteDao(conn);

            // Instanciando o Cliente
            Cliente cliente = new Cliente(id, cpf, nome, dtNasc, endereco, email);

            // Executando a atualização
            dao.atualizar(cliente);
            System.out.println("Cliente atualizado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }
}
