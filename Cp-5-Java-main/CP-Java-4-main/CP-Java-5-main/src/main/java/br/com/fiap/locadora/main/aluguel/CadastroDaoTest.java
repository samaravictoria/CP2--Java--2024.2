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
import java.sql.SQLException;

public class CadastroDaoTest {
    public static void main(String[] args) {
        // Pedindo os dados para o usuário
        int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Qual o ID do cliente que está alugando?"));
        int idFilme = Integer.parseInt(JOptionPane.showInputDialog("Qual o ID do filme alugado?"));
        String dtAluguel = JOptionPane.showInputDialog("Qual a data em que foi alugado? (Formato: YYYY-MM-DD)");
        String devolucao = JOptionPane.showInputDialog("Qual a data de devolução? (Formato: YYYY-MM-DD)");

        Aluguel aluguel = new Aluguel(dtAluguel, devolucao);

        Connection conn = null;

        try {
            // Criando a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();
            AluguelDao aluguelDao = new AluguelDao(conn);
            ClienteDao clienteDao = new ClienteDao(conn);
            FilmeDao filmeDao = new FilmeDao(conn);

            // Pesquisando e setando a FK id_cliente
            Cliente cliente = clienteDao.pesquisaId(idCliente);
            aluguel.setCliente(cliente);

            // Pesquisando e setando a FK id_filme
            Filme filme = filmeDao.pesquisaID(idFilme);
            aluguel.setFilme(filme);

            // Executando o cadastro
            aluguelDao.cadastrar(aluguel);
            System.out.println("Filme " + aluguel.getFilme().getNome() +
                    " alugado com sucesso por: " + aluguel.getCliente().getNome());

        } catch (SQLException e) {
            System.err.println("Erro ao acessar o banco de dados: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Por favor, insira um número válido para IDs.");
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        } finally {
            // Fechando a conexão com o banco de dados
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar a conexão: " + e.getMessage());
                }
            }
        }
    }
}
