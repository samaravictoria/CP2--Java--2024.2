package br.com.fiap.locadora.main.locadora;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Locadora;

import javax.swing.*;
import java.sql.Connection;

public class PesquisaIdDaoTest {
    public static void main(String[] args) {

        // Pedindo o ID para o usuário
        int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID da locadora: "));

        // Usando try-with-resources para garantir que a conexão seja fechada
        try (Connection conn = ConnectionFactory.getConnection()) {
            LocadoraDao dao = new LocadoraDao(conn);

            Locadora locadora = dao.pesquisaId(id);

            // Verificando se a locadora foi encontrada
            if (locadora != null) {
                // Exibindo os dados pesquisados
                System.out.println("Locadora encontrada:");
                System.out.println(locadora); // Aqui você pode formatar a saída conforme necessário
            } else {
                System.out.println("Nenhuma locadora encontrada com o ID informado.");
            }

        } catch (Exception e) {
            System.err.println("Erro ao pesquisar locadora: " + e.getMessage());
        }
    }
}
