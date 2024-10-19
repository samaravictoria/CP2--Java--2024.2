package br.com.fiap.locadora.dao;

import br.com.fiap.locadora.exceptions.DataNaoEncontradaException;
import br.com.fiap.locadora.exceptions.IdNaoEncontradoException;
import br.com.fiap.locadora.model.Aluguel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AluguelDao {

    private Connection conexao;

    public AluguelDao(Connection conexao) {
        this.conexao = conexao;
    }

    // Cadastra um aluguel e relaciona um filme e um cliente
    public void cadastrar(Aluguel aluguel) throws SQLException {
        String sql = "INSERT INTO t_aluguel VALUES (sq_t_aluguel.nextval, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, aluguel.getFilme().getId());
            stmt.setInt(2, aluguel.getCliente().getId());
            stmt.setDate(3, Date.valueOf(aluguel.getDtAluguel()));
            stmt.setDate(4, aluguel.getDtDevolucao() != null ? Date.valueOf(aluguel.getDtDevolucao()) : null);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao cadastrar aluguel: " + e.getMessage(), e);
        }
    }

    // Lista todos os aluguéis
    public List<Aluguel> listar() throws SQLException {
        String sql = "SELECT * FROM t_aluguel ORDER BY id_aluguel";
        List<Aluguel> lista = new ArrayList<>();

        try (Statement stmt = conexao.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            while (resultSet.next()) {
                lista.add(parseAluguel(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar aluguéis: " + e.getMessage(), e);
        }

        return lista;
    }

    // Pesquisa um aluguel pelo ID
    public Aluguel pesquisarPorId(int id) throws SQLException, IdNaoEncontradoException {
        String sql = "SELECT * FROM t_aluguel WHERE id_aluguel = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new IdNaoEncontradoException("Aluguel não encontrado com ID: " + id);
            }

            return parseAluguel(resultSet);
        } catch (SQLException e) {
            throw new SQLException("Erro ao pesquisar aluguel por ID: " + e.getMessage(), e);
        }
    }

    // Atualiza um aluguel
    public void atualizar(Aluguel aluguel) throws SQLException, IdNaoEncontradoException {
        String sql = "UPDATE t_aluguel SET id_filme = ?, id_cliente = ?, dt_aluguel = ?, dt_devolucao = ? WHERE id_aluguel = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, aluguel.getFilme().getId());
            stmt.setInt(2, aluguel.getCliente().getId());
            stmt.setDate(3, Date.valueOf(aluguel.getDtAluguel()));
            stmt.setDate(4, aluguel.getDtDevolucao() != null ? Date.valueOf(aluguel.getDtDevolucao()) : null);
            stmt.setInt(5, aluguel.getId());

            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new IdNaoEncontradoException("Aluguel não encontrado para atualização. ID: " + aluguel.getId());
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar aluguel: " + e.getMessage(), e);
        }
    }

    // Remove um aluguel
    public void remover(int id) throws SQLException, IdNaoEncontradoException {
        String sql = "DELETE FROM t_aluguel WHERE id_aluguel = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new IdNaoEncontradoException("Aluguel não encontrado para remoção. ID: " + id);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao remover aluguel: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para converter ResultSet em objeto Aluguel
    private Aluguel parseAluguel(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_aluguel");
        LocalDate dtAluguel = resultSet.getDate("dt_aluguel").toLocalDate();
        LocalDate dtDevolucao = resultSet.getDate("dt_devolucao") != null ? resultSet.getDate("dt_devolucao").toLocalDate() : null;

        return new Aluguel(id, dtAluguel, dtDevolucao);
    }

    // Métodos não implementados (adicionar implementação ou remover)
    public void deletar(int id) {
        throw new UnsupportedOperationException("Método não implementado.");
    }

    public List<Aluguel> pesquisarIdCliente(int idCliente) {
        throw new UnsupportedOperationException("Método não implementado.");
    }

    public List<Aluguel> pesquisarIdFilme(int idFilme) {
        throw new UnsupportedOperationException("Método não implementado.");
    }

    public List<Aluguel> pesquisarDtDevolucao(String dtDevolucao) {
        throw new UnsupportedOperationException("Método não implementado.");
    }
}
