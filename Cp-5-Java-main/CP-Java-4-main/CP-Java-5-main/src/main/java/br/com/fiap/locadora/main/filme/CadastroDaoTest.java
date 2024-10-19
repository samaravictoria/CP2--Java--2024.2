package br.com.fiap.locadora.main.filme;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FilmeDao;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Filme;
import br.com.fiap.locadora.model.Locadora;

import javax.swing.*;
import java.sql.Connection;

public class CadastroDaoTest {
    public static void main(String[] args) {
        // Pedir os dados do filme
        String nome = JOptionPane.showInputDialog("Insira o nome do filme");
        String diretor = JOptionPane.showInputDialog("Insira o nome do diretor");
        int ano = Integer.parseInt(JOptionPane.showInputDialog("Insira o ano de lançamento do filme"));
        String genero = JOptionPane.showInputDialog("Insira o gênero do filme");
        String sinopse = JOptionPane.showInputDialog("Insira a sinopse do filme");
        int duracao = Integer.parseInt(JOptionPane.showInputDialog("Insira a duração do filme (em minutos)"));
        String classificacao = JOptionPane.showInputDialog("Insira a classificação indicativa do filme");

        boolean vaiLocadora = JOptionPane.showConfirmDialog(null,
                "Deseja adicionar uma locadora?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

        // Instanciando o filme
        Filme filme = new Filme(nome, diretor, ano, genero, sinopse, duracao, classificacao);

        try (Connection conn = ConnectionFactory.getConnection()) {
            if (vaiLocadora) {
                int idLoc = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da locadora"));
                LocadoraDao locaDao = new LocadoraDao(conn);
                Locadora locadora = locaDao.pesquisaId(idLoc);
                filme.setLocadora(locadora);
            }

            // Criando o DAO
            FilmeDao dao = new FilmeDao(conn);
            dao.cadastrar(filme);
            System.out.println("Filme cadastrado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar filme: " + e.getMessage());
        }
    }
}
