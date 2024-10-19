package br.com.fiap.locadora.main.locadora;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Locadora;

import javax.swing.*;
import java.sql.Connection;

public class UpdateDaoTest {
    public static void main(String[] args) {

        // Pedindo o id e os dados a serem modificados para o usuário
        int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID da locadora que você quer modificar"));
        String nome = JOptionPane.showInputDialog("Insira o novo nome");
        String endereco = JOptionPane.showInputDialog("Insira o novo endereço");

        // Perguntando se o usuário deseja modificar o telefone
        String telefone = null;
        if (JOptionPane.showConfirmDialog(null, "Deseja adicionar ou modificar o telefone?", "Modificar Telefone", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            telefone = JOptionPane.showInputDialog("Insira o novo telefone (ou deixe em branco para não modificar)");
        }

        try (Connection conn = ConnectionFactory.getConnection()) {
            // Criando o DAO
            LocadoraDao dao = new LocadoraDao(conn);

            // Instanciando a locadora
            Locadora locadora = new Locadora(id, nome, endereco, telefone);

            // Atualizando a locadora
            dao.atualizar(locadora);
            System.out.println("Locadora atualizada com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao atualizar a locadora: " + e.getMessage());
        }
    }
}
