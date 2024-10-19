package br.com.fiap.locadora.dao;

import br.com.fiap.locadora.exceptions.EnderecoNaoEncontradoException;
import br.com.fiap.locadora.exceptions.IdNaoEncontradoException;
import br.com.fiap.locadora.exceptions.NomeNaoEncontradoException;
import br.com.fiap.locadora.model.Locadora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocadoraDao {

    private final Connection conexao;

    // Construtor que recebe a conexão
    public LocadoraDao(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para cadastrar uma nova locadora
    public void cadastrar(Locadora locadora) throws SQLException {
        String sql = "INSERT INTO t_locadora (id_locadora, nm_locadora, ds_endereco, nr_telefone) " +
                "VALUES (sq_t_locadora.nextval, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, locadora.getNome());
            stmt.setString(2, locadora.getEndereco());

            // Verifica se o telefone é nulo e insere o valor correspondente
            if (locadora.getTelefone() != null) {
                stmt.setString(3, locadora.getTelefone());
            } else {
                stmt.setNull(3, Types.VARCHAR);
            }

            // Executa a query de inserção
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao cadastrar locadora: " + e.getMessage(), e);
        }
    }

    // Método para pesquisar uma locadora pelo ID
    public Locadora pesquisarPorId(int id) throws SQLException, IdNaoEncontradoException {
        String sql = "SELECT * FROM t_locadora WHERE id_locadora = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            // Verifica se a locadora foi encontrada
            if (!resultSet.next()) {
                throw new IdNaoEncontradoException("Locadora com ID " + id + " não encontrada.");
            }

            // Retorna a locadora encontrada
            return parseLocadora(resultSet);
        } catch (SQLException e) {
            throw new SQLException("Erro ao pesquisar locadora por ID: " + e.getMessage(), e);
        }
    }

    // Método para listar todas as locadoras cadastradas
    public List<Locadora> listar() throws SQLException {
        String sql = "SELECT * FROM t_locadora ORDER BY id_locadora";
        List<Locadora> lista = new ArrayList<>();

        try (Statement stmt = conexao.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            // Adiciona cada locadora encontrada na lista
            while (resultSet.next()) {
                lista.add(parseLocadora(resultSet));
            }

            // Retorna a lista de locadoras
            return lista;
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar locadoras: " + e.getMessage(), e);
        }
    }

    // Método para atualizar os dados de uma locadora
    public void atualizar(Locadora locadora) throws SQLException, IdNaoEncontradoException {
        String sql = "UPDATE t_locadora SET nm_locadora = ?, ds_endereco = ?, nr_telefone = ? WHERE id_locadora = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, locadora.getNome());
            stmt.setString(2, locadora.getEndereco());

            // Verifica se o telefone é nulo e insere o valor correspondente
            if (locadora.getTelefone() != null) {
                stmt.setString(3, locadora.getTelefone());
            } else {
                stmt.setNull(3, Types.VARCHAR);
            }

            // Define o ID da locadora a ser atualizada
            stmt.setInt(4, locadora.getId());
            int linhasAfetadas = stmt.executeUpdate();

            // Verifica se a locadora foi encontrada para atualização
            if (linhasAfetadas == 0) {
                throw new IdNaoEncontradoException("Locadora com ID " + locadora.getId() + " não encontrada para atualização.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar locadora: " + e.getMessage(), e);
        }
    }

    // Método para remover uma locadora pelo ID
    public void remover(int id) throws SQLException, IdNaoEncontradoException {
        String sql = "DELETE FROM t_locadora WHERE id_locadora = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            // Verifica se a locadora foi encontrada para remoção
            if (linhasAfetadas == 0) {
                throw new IdNaoEncontradoException("Locadora com ID " + id + " não encontrada para remoção.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao remover locadora: " + e.getMessage(), e);
        }
    }

    // Método para pesquisar locadoras por nome
    public List<Locadora> pesquisarPorNome(String nome) throws SQLException, NomeNaoEncontradoException {
        String sql = "SELECT * FROM t_locadora WHERE nm_locadora LIKE ?";
        List<Locadora> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet resultSet = stmt.executeQuery();

            // Adiciona locadoras com nome correspondente na lista
            while (resultSet.next()) {
                lista.add(parseLocadora(resultSet));
            }

            // Verifica se alguma locadora foi encontrada
            if (lista.isEmpty()) {
                throw new NomeNaoEncontradoException("Locadora não encontrada com o nome: " + nome);
            }

            // Retorna a lista de locadoras encontradas
            return lista;
        } catch (SQLException e) {
            throw new SQLException("Erro ao pesquisar locadoras por nome: " + e.getMessage(), e);
        }
    }

    // Método para pesquisar locadoras por endereço
    public List<Locadora> pesquisarPorEndereco(String endereco) throws SQLException, EnderecoNaoEncontradoException {
        String sql = "SELECT * FROM t_locadora WHERE ds_endereco LIKE ?";
        List<Locadora> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + endereco + "%");
            ResultSet resultSet = stmt.executeQuery();

            // Adiciona locadoras com endereço correspondente na lista
            while (resultSet.next()) {
                lista.add(parseLocadora(resultSet));
            }

            // Verifica se alguma locadora foi encontrada no endereço
            if (lista.isEmpty()) {
                throw new EnderecoNaoEncontradoException("Não foram encontradas locadoras no endereço: " + endereco);
            }

            // Retorna a lista de locadoras encontradas
            return lista;
        } catch (SQLException e) {
            throw new SQLException("Erro ao pesquisar locadoras por endereço: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para converter ResultSet em objeto Locadora
    private Locadora parseLocadora(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_locadora");
        String nome = resultSet.getString("nm_locadora");
        String endereco = resultSet.getString("ds_endereco");
        String telefone = resultSet.getString("nr_telefone");

        // Cria e retorna o objeto Locadora com os dados do ResultSet
        return new Locadora(id, nome, endereco, telefone);
    }
}
