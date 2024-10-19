package br.com.fiap.locadora.dao;

import br.com.fiap.locadora.exceptions.IdNaoEncontradoException;
import br.com.fiap.locadora.exceptions.NomeNaoEncontradoException;
import br.com.fiap.locadora.model.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    private Connection conexao;

    public ClienteDao(Connection conexao) {
        this.conexao = conexao;
    }

    // Cadastra um cliente
    public void cadastrar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO t_cliente (id_cliente, nr_cpf, nm_cliente, dt_nascimento, ds_endereco, ds_email) " +
                "VALUES (sq_t_cliente.nextval, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setDate(3, Date.valueOf(cliente.getDtNasc())); // Converte LocalDate para java.sql.Date
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getEmail());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao cadastrar cliente: " + e.getMessage(), e);
        }
    }

    // Lista todos os clientes
    public List<Cliente> listar() throws SQLException {
        String sql = "SELECT * FROM t_cliente ORDER BY id_cliente";
        List<Cliente> lista = new ArrayList<>();

        try (Statement stmt = conexao.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            while (resultSet.next()) {
                lista.add(parseCliente(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar clientes: " + e.getMessage(), e);
        }

        return lista;
    }

    // Pesquisa cliente por ID
    public Cliente pesquisarPorId(int id) throws SQLException, IdNaoEncontradoException {
        String sql = "SELECT * FROM t_cliente WHERE id_cliente = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new IdNaoEncontradoException("Cliente não encontrado com ID: " + id);
            }

            return parseCliente(resultSet);
        } catch (SQLException e) {
            throw new SQLException("Erro ao pesquisar cliente por ID: " + e.getMessage(), e);
        }
    }

    // Pesquisa clientes pelo nome
    public List<Cliente> pesquisarPorNome(String nome) throws SQLException, NomeNaoEncontradoException {
        String sql = "SELECT * FROM t_cliente WHERE nm_cliente LIKE ? ORDER BY id_cliente";
        List<Cliente> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                lista.add(parseCliente(resultSet));
            }

            if (lista.isEmpty()) {
                throw new NomeNaoEncontradoException("Nenhum cliente encontrado com o nome: " + nome);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao pesquisar cliente por nome: " + e.getMessage(), e);
        }

        return lista;
    }

    // Atualiza um cliente
    public void atualizar(Cliente cliente) throws SQLException, IdNaoEncontradoException {
        String sql = "UPDATE t_cliente SET nr_cpf = ?, nm_cliente = ?, dt_nascimento = ?, ds_endereco = ?, ds_email = ? " +
                "WHERE id_cliente = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setDate(3, Date.valueOf(cliente.getDtNasc())); // Converte LocalDate para java.sql.Date
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getEmail());
            stmt.setInt(6, cliente.getId());

            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new IdNaoEncontradoException("Cliente não encontrado para atualizar. ID: " + cliente.getId());
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar cliente: " + e.getMessage(), e);
        }
    }

    // Remove um cliente pelo ID
    public void remover(int id) throws SQLException, IdNaoEncontradoException {
        String sql = "DELETE FROM t_cliente WHERE id_cliente = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new IdNaoEncontradoException("Cliente não encontrado para remoção. ID: " + id);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao remover cliente: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para converter ResultSet em objeto Cliente
    private Cliente parseCliente(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_cliente");
        String cpf = resultSet.getString("nr_cpf");
        String nome = resultSet.getString("nm_cliente");
        LocalDate dtNasc = resultSet.getDate("dt_nascimento").toLocalDate(); // Converte java.sql.Date para LocalDate
        String endereco = resultSet.getString("ds_endereco");
        String email = resultSet.getString("ds_email");

        return new Cliente(id, cpf, nome, dtNasc, endereco, email);
    }

    // Métodos não implementados (adicionar implementação ou remover)
    public Cliente pesquisaId(int idCliente) {
        throw new UnsupportedOperationException("Método não implementado.");
    }

    public void apagar(int id) {
        throw new UnsupportedOperationException("Método não implementado.");
    }

    public List<Cliente> pesquisaNome(String nome) {
        throw new UnsupportedOperationException("Método não implementado.");
    }
}
