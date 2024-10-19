package br.com.fiap.locadora.dao;

import br.com.fiap.locadora.exceptions.*;
import br.com.fiap.locadora.model.Filme;
import br.com.fiap.locadora.model.Genero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmeDao {

    private Connection conexao;

    public FilmeDao(Connection conexao) {
        this.conexao = conexao;
    }

    // Cadastra um filme
    public void cadastrar(Filme filme) throws SQLException {
        String sql = "INSERT INTO t_filme VALUES (sq_t_filme.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            // Verifica se a locadora está associada ao filme
            if (filme.getLocadora() != null) {
                stmt.setInt(1, filme.getLocadora().getId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }

            // Configura os dados do filme no PreparedStatement
            stmt.setString(2, filme.getNome());
            stmt.setString(3, filme.getDiretor());
            stmt.setInt(4, filme.getAnoLancamento());
            stmt.setString(5, filme.getGenero().name()); // Usa name() para obter a representação string do enum
            stmt.setString(6, filme.getSinopse());
            stmt.setInt(7, filme.getDuracao());
            stmt.setString(8, filme.getClassificacao());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao cadastrar filme: " + e.getMessage(), e);
        }
    }

    // Lista todos os filmes
    public List<Filme> listar() throws SQLException {
        String sql = "SELECT * FROM t_filme ORDER BY id_filme";
        List<Filme> lista = new ArrayList<>();

        try (Statement stm = conexao.createStatement();
             ResultSet resultSet = stm.executeQuery(sql)) {
            while (resultSet.next()) {
                lista.add(parseFilme(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar filmes: " + e.getMessage(), e);
        }

        return lista;
    }

    // Pesquisa filme por ID
    public Filme pesquisarPorId(int id) throws SQLException, IdNaoEncontradoException {
        String sql = "SELECT * FROM t_filme WHERE id_filme = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new IdNaoEncontradoException("Filme não encontrado com ID: " + id);
            }

            return parseFilme(resultSet);
        } catch (SQLException e) {
            throw new SQLException("Erro ao pesquisar filme por ID: " + e.getMessage(), e);
        }
    }

    // Atualiza um filme
    public void atualizar(Filme filme) throws SQLException, IdNaoEncontradoException {
        String sql = "UPDATE t_filme SET nm_filme = ?, nm_diretor = ?, ano_lancamento = ?, " +
                "ds_genero = ?, ds_sinopse = ?, tm_duracao = ?, ds_classificacao = ?, " +
                "id_locadora = ? WHERE id_filme = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, filme.getNome());
            stmt.setString(2, filme.getDiretor());
            stmt.setInt(3, filme.getAnoLancamento());
            stmt.setString(4, filme.getGenero().name()); // Usa name() para o enum
            stmt.setString(5, filme.getSinopse());
            stmt.setInt(6, filme.getDuracao());
            stmt.setString(7, filme.getClassificacao());

            if (filme.getLocadora() != null) {
                stmt.setInt(8, filme.getLocadora().getId());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }

            stmt.setInt(9, filme.getId());
            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new IdNaoEncontradoException("Filme não encontrado para atualizar. ID: " + filme.getId());
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar filme: " + e.getMessage(), e);
        }
    }

    // Remove um filme por ID
    public void remover(int id) throws SQLException, IdNaoEncontradoException {
        String sql = "DELETE FROM t_filme WHERE id_filme = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new IdNaoEncontradoException("Filme não encontrado para remoção. ID: " + id);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao remover filme: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para converter ResultSet em objeto Filme
    private Filme parseFilme(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_filme");
        String nome = resultSet.getString("nm_filme");
        String nomeDiretor = resultSet.getString("nm_diretor");
        int ano = resultSet.getInt("ano_lancamento");

        // Tratamento para conversão de gênero
        String generoString = resultSet.getString("ds_genero");
        Genero genero;
        try {
            genero = Genero.valueOf(generoString.toUpperCase()); // Converte a string de volta para o enum
        } catch (IllegalArgumentException e) {
            throw new SQLException("Gênero inválido: " + generoString, e);
        }

        String sinopse = resultSet.getString("ds_sinopse");
        int duracao = resultSet.getInt("tm_duracao");
        String classificacao = resultSet.getString("ds_classificacao");

        return new Filme(id, nome, nomeDiretor, ano, genero, sinopse, duracao, classificacao);
    }

    // Métodos não implementados (adicionar implementação ou remover)
    public Filme pesquisaID(int idFilme) {
        throw new UnsupportedOperationException("Método não implementado.");
    }

    public void apagar(int id) {
        throw new UnsupportedOperationException("Método não implementado.");
    }

    public List<Filme> pesquisaDiretor(String diretor) {
        throw new UnsupportedOperationException("Método não implementado.");
    }

    public List<Filme> pesquisaGenero(String genero) {
        throw new UnsupportedOperationException("Método não implementado.");
    }

    public List<Filme> pesquisaNome(String nome) {
        throw new UnsupportedOperationException("Método não implementado.");
    }

    public List<Filme> pesquisaAnoLancamento(int ano) {
        throw new UnsupportedOperationException("Método não implementado.");
    }
}
