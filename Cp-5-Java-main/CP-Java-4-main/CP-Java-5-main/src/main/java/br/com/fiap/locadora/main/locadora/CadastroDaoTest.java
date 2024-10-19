package br.com.fiap.locadora.main.locadora;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Locadora;

import javax.swing.*;
import java.sql.Connection;

public class CadastroDaoTest {
    public static void main(String[] args) {

        // Pedir os dados da locadora
        String nome = JOptionPane.showInputDialog("Insira o nome da locadora que será registrada: ");
        String endereco = JOptionPane.showInputDialog("Insira o endereço da locadora: ");

        // Validar se nome e endereço não estão vazios
        if (nome == null || nome.trim().isEmpty() || endereco == null || endereco.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome e endereço são obrigatórios.");
            return;
        }

        boolean adicionarTelefone = JOptionPane.showConfirmDialog(null, "Deseja adicionar um número de telefone?") == 0;
        String telefone = null;

        if (adicionarTelefone) {
            telefone = JOptionPane.showInputDialog("Insira o número de telefone: ");
            // Aqui você pode adicionar uma validação para o telefone se necessário
        }

        // Usando try-with-resources para garantir que a conexão seja fechada
        try (Connection conn = ConnectionFactory.getConnection()) {
            LocadoraDao dao = new LocadoraDao(conn);

            // Instanciando a locadora
            Locadora locadora = new Locadora(nome, endereco);
            if (telefone != null && !telefone.trim().isEmpty()) {
                locadora.setTelefone(telefone);
            }

            dao.cadastrar(locadora);
            JOptionPane.showMessageDialog(null, "Locadora cadastrada com sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar locadora: " + e.getMessage());
        }
    }
}
