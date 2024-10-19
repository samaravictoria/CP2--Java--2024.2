package br.com.fiap.locadora.main.filme;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FilmeDao;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Filme;
import br.com.fiap.locadora.model.Locadora;

import javax.swing.*;
import java.sql.Connection;

public class UpdateDaoTest {
    public static void main(String[] args) {

        // Pedir os dados para o cliente
        int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID do filme que deseja atualizar"));
        String nome = JOptionPane.showInputDialog("Insira o nome do filme");
        String diretor = JOptionPane.showInputDialog("Insira o nome do diretor");
        int ano = Integer.parseInt(JOptionPane.showInputDialog("Insira o ano de lançamento do filme"));
        String genero = JOptionPane.showInputDialog("Insira o gênero do filme");
        String sinopse = JOptionPane.showInputDialog("Insira a sinopse do filme");
        int duracao = Integer.parseInt(JOptionPane.showInputDialog("Insira a duração do filme (em minutos)"));
        String classificacao = JOptionPane.showInputDialog("Insira a classificação indicativa do filme");

        boolean uptLocadora = JOptionPane.showConfirmDialog(null,
                "Deseja mudar a locadora?", "Confirmação", JOptionPane.YES_NO_OPTION) == 0;

        Filme filme = new Filme(id, nome, diretor, ano, genero, sinopse, duracao, classificacao);

        try (Connection conn = ConnectionFactory.getConnection()) {
            // Instanciar o DAO
            FilmeDao dao = new FilmeDao(conn);

            if (uptLocadora) {
                int idLoca = Integer.parseInt(JOptionPane.showInputDialog("Qual o novo ID da locadora? "));

                LocadoraDao daoLoca = new LocadoraDao(conn);
                Locadora locadora = daoLoca.pesquisaId(idLoca);
                if (locadora != null) {
                    filme.setLocadora(locadora);
                } else {
                    System.out.println("Locadora não encontrada para o ID fornecido.");
                    return; // Encerrar o processo se a locadora não for encontrada
                }
            }

            dao.atualizar(filme);
            System.out.println("Update realizado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao atualizar o filme: " + e.getMessage());
        }
    }
}
