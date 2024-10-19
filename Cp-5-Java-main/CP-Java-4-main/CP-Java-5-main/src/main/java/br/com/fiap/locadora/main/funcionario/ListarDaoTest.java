package br.com.fiap.locadora.main.funcionario;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FuncionarioDao;
import br.com.fiap.locadora.model.Funcionario;

import java.sql.Connection;
import java.util.List;

public class ListarDaoTest {
    public static void main(String[] args) {
        // Usando try-with-resources para garantir que a conexão seja fechada
        try (Connection conn = ConnectionFactory.getConnection()) {
            // Criando o DAO do funcionário
            FuncionarioDao dao = new FuncionarioDao(conn);

            // Criando a lista de exibição dos funcionários
            List<Funcionario> lista = dao.listar();

            // Exibindo os dados encontrados
            for (Funcionario f : lista) {
                System.out.println(f); // Assumindo que a classe Funcionario tem um método toString() apropriado
            }
            System.out.println("Funcionários encontrados: " + lista.size());

        } catch (Exception e) {
            System.err.println("Erro ao listar funcionários: " + e.getMessage());
        }
    }
}
