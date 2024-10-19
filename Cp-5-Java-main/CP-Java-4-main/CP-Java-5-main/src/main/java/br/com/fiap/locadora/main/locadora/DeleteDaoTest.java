package br.com.fiap.locadora.main.locadora;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.LocadoraDao;

import javax.swing.*;
import java.sql.Connection;

public class DeleteDaoTest {
    public static void main(String[] args) {

        // Pedindo o id para o usuário
        String inputId = JOptionPane.showInputDialog("Insira o ID da locadora que deseja deletar");

        // Validar se o ID é um número válido
        if (inputId == null || inputId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID não pode ser vazio.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(inputId);
            if (id < 1) {
                JOptionPane.showMessageDialog(null, "ID deve ser um número positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um número válido para o ID.");
            return;
        }

        // Confirmação de deleção
        int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar a locadora com ID " + id + "?");
        if (confirm != JOptionPane.YES_OPTION) {
            return; // Sai se o usuário não confirmar
        }

        // Usando try-with-resources para garantir que a conexão seja fechada
        try (Connection conn = ConnectionFactory.getConnection()) {
            LocadoraDao dao = new LocadoraDao(conn);

            // Executando o delete
            dao.apagar(id);
            JOptionPane.showMessageDialog(null, "Locadora deletada com sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar locadora: " + e.getMessage());
        }
    }
}
