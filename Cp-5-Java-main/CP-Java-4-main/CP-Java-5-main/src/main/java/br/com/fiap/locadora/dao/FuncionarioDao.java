package br.com.fiap.locadora.dao;

import br.com.fiap.locadora.exceptions.IdNaoEncontradoException;
import br.com.fiap.locadora.model.Funcionario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDao {

    private final Connection conexao;

    public FuncionarioDao(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para cadastrar um funcionário
    public void cadastrar(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO t_funcionario VALUES (sq_t_funcionario.nextval, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, funcionario.getLocadora().getId());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getNome());
            stmt.setDate(4, Date.valueOf(funcionario.getDtNasc())); // Converte LocalDate para java.sql.Date
            stmt.setInt(5, funcionario.getAnoAdmissao());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao cadastrar funcionário: " + e.getMessage(), e);
        }
    }

    // Método para listar todos os funcionários
    public List<Funcionario> listar() throws SQLException {
        String sql = "SELECT * FROM t_funcionario ORDER BY id_funcionario";
        List<Funcionario> lista = new ArrayList<>();

        try (Statement stmt = conexao.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            while (resultSet.next()) {
                Funcionario funcionario = parseFuncionario(resultSet);
                lista.add(funcionario);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar funcionários: " + e.getMessage(), e);
        }

        return lista;
    }

    // Método auxiliar para converter ResultSet em objeto Funcionario
    private Funcionario parseFuncionario(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_funcionario");
        String cpf = resultSet.getString("nr_cpf");
        String nome = resultSet.getString("nm_funcionario");
        LocalDate dtNasc = resultSet.getDate("dt_nascimento").toLocalDate(); // Converte java.sql.Date para LocalDate
        int anoAdmissao = resultSet.getInt("ano_admissao");

        return new Funcionario(id, cpf, nome, dtNasc, anoAdmissao);
    }

    // Método para pesquisar funcionário por ID
    public Funcionario pesquisarPorId(int id) throws SQLException, IdNaoEncontradoException {
        String sql = "SELECT * FROM t_funcionario WHERE id_funcionario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new IdNaoEncontradoException("Funcionário não encontrado com ID: " + id);
            }

            return parseFuncionario(resultSet);
        } catch (SQLException e) {
            throw new SQLException("Erro ao pesquisar funcionário por ID: " + e.getMessage(), e);
        }
    }

    // Método para remover um funcionário por ID
    public void apagar(int id) throws SQLException, IdNaoEncontradoException {
        String sql = "DELETE FROM t_funcionario WHERE id_funcionario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhas = stmt.executeUpdate();

            if (linhas == 0) {
                throw new IdNaoEncontradoException("Funcionário não encontrado para remoção. ID: " + id);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao remover funcionário: " + e.getMessage(), e);
        }
    }

    // Método para pesquisar funcionários por locadora
    public List<Funcionario> pesquisaPorLocadora(int idLocadora) throws SQLException {
        String sql = "SELECT * FROM t_funcionario WHERE id_locadora = ? ORDER BY id_funcionario";
        List<Funcionario> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idLocadora);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Funcionario funcionario = parseFuncionario(resultSet);
                lista.add(funcionario);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao pesquisar funcionários por locadora: " + e.getMessage(), e);
        }

        return lista;
    }

    // Método para pesquisar funcionários por nome
    public List<Funcionario> pesquisaNome(String nome) throws SQLException {
        String sql = "SELECT * FROM t_funcionario WHERE nm_funcionario LIKE ? ORDER BY id_funcionario";
        List<Funcionario> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Funcionario funcionario = parseFuncionario(resultSet);
                lista.add(funcionario);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao pesquisar funcionários por nome: " + e.getMessage(), e);
        }

        return lista;
    }

    // Método para atualizar um funcionário
    public void atualizar(Funcionario funcionario) throws SQLException, IdNaoEncontradoException {
        String sql = "UPDATE t_funcionario SET nr_cpf = ?, nm_funcionario = ?, dt_nascimento = ?, ano_admissao = ?, id_locadora = ? WHERE id_funcionario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getCpf());
            stmt.setString(2, funcionario.getNome());
            stmt.setDate(3, Date.valueOf(funcionario.getDtNasc())); // Converte LocalDate para java.sql.Date
            stmt.setInt(4, funcionario.getAnoAdmissao());
            stmt.setInt(5, funcionario.getLocadora().getId());
            stmt.setInt(6, funcionario.getId());

            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new IdNaoEncontradoException("Funcionário não encontrado para atualizar. ID: " + funcionario.getId());
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar funcionário: " + e.getMessage(), e);
        }
    }
}
