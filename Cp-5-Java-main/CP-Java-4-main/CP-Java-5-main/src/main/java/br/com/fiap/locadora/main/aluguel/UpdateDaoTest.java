package br.com.fiap.locadora.main.aluguel;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.AluguelDao;
import br.com.fiap.locadora.dao.ClienteDao;
import br.com.fiap.locadora.dao.FilmeDao;
import br.com.fiap.locadora.model.Aluguel;
import br.com.fiap.locadora.model.Cliente;
import br.com.fiap.locadora.model.Filme;

import javax.swing.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateDaoTest {
    public static void main(String[] args) {
        // Pedindo os dados para o cliente
        int idAluguel = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do aluguel do qual você deseja atualizar?"));
        int idFilme = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do filme?"));
        int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do cliente?"));
        String dtAluguel = JOptionPane.showInputDialog("Qual a data do aluguel? (yyyy-MM-dd)");
        String dtDevolucao = JOptionPane.showInputDialog("Qual a data de devolução? (yyyy-MM-dd)");

        // Parseando as datas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataAluguel = LocalDate.parse(dtAluguel, formatter);
        LocalDate dataDevolucao = dtDevolucao.isEmpty() ? null : LocalDate.parse(dtDevolucao, formatter);

        // Instanciando um aluguel
        Aluguel aluguel = new Aluguel(idAluguel, dataAluguel, dataDevolucao);

        try (Connection conn = ConnectionFactory.getConnection()) {
            // Criando os DAO's
            AluguelDao aluguelDao = new AluguelDao(conn);
            FilmeDao filmeDao = new FilmeDao(conn);
            ClienteDao clienteDao = new ClienteDao(conn);

            // Setando os id do filme e do cliente
            Filme filme = filmeDao.pesquisaID(idFilme);
            aluguel.setFilme(filme);

            Cliente cliente = clienteDao.pesquisaId(idCliente);
            aluguel.setCliente(cliente);

            // Executando a atualização
            aluguelDao.atualizar(aluguel);
            System.out.println("Update realizado com sucesso!");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
