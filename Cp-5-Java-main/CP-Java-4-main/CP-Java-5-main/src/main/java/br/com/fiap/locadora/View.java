package br.com.fiap.locadora;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.*;
import br.com.fiap.locadora.model.*;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class View {
    public static void main(String[] args) {

        //Variável para utilizar dentro das opções de dentro do switch
        String opcao;

        //Iniciando o atendimento
        String atendimento = JOptionPane.showInputDialog("Bem vindo à LocaFilm, como podemos ajudar?: " +
                "\n1- Cadastrar" +
                "\n2- Listar" +
                "\n3- Atualizar" +
                "\n4- Deletar" +
                "\n5- Cancelar");

        try {

            //Criando a conexão com o banco e os DAOs que serão utilizados
            Connection conn = ConnectionFactory.getConnection();
            LocadoraDao locadoraDao = new LocadoraDao(conn);
            ClienteDao clienteDao = new ClienteDao(conn);
            FilmeDao filmeDao = new FilmeDao(conn);
            FuncionarioDao funcionarioDao = new FuncionarioDao(conn);
            AluguelDao aluguelDao = new AluguelDao(conn);

            switch (atendimento){
                case "1":

                    opcao = JOptionPane.showInputDialog("O que você deseja cadastrar?: " +
                            "\n1- Cliente" +
                            "\n2- Locadora" +
                            "\n3- Funcionário" +
                            "\n4- Filme" +
                            "\n5- Aluguel" +
                            "\n6- Cancelar");

                    switch (opcao){
                        case "1":
                            JOptionPane.showMessageDialog(null, "Cadastro de cliente!");

                            //Pedir os dados de cadastro
                            String cpfCliente = JOptionPane.showInputDialog("Insira seu CPF: ");
                            String nomeCliente = JOptionPane.showInputDialog("Insira seu nome: ");
                            String dtNascCliente = JOptionPane.showInputDialog("Insira sua data de nascimento: ");
                            String enderecoCliente = JOptionPane.showInputDialog("Insira seu endereço: ");
                            String emailClie = JOptionPane.showInputDialog("Insira seu Email: ");

                            try {

                                //Instanciando o cliente
                                Cliente cliente = new Cliente(cpfCliente, nomeCliente, dtNascCliente,
                                        enderecoCliente, emailClie);

                                clienteDao.cadastrar(cliente);
                                System.out.println("Cadastro realizado com sucesso!");

                            } catch (Exception e){
                                System.err.println(e.getMessage());
                            }
                            break;

                        case "2":

                            JOptionPane.showMessageDialog(null, "Cadastro de locadora!");

                            //Pedir os dados da locadora
                            String nomeLoca = JOptionPane.showInputDialog("Insira o nome da locadora que será registrada: ");
                            String enderecoLoca = JOptionPane.showInputDialog("Insira o endereço da locadora: ");

                            boolean adicionarTelefone = JOptionPane.showConfirmDialog(null,
                                    "Deseja adicionar um número de telefone?") == 0;

                            try {

                                //Instanciando a locadora
                                Locadora locadora = new Locadora(nomeLoca, enderecoLoca);

                                if (adicionarTelefone){
                                    String telefoneLoca = JOptionPane.showInputDialog("Insira o número de telefone: ");
                                    locadora.setTelefone(telefoneLoca);
                                }

                                locadoraDao.cadastrar(locadora);
                                System.out.println("Locadora cadastrada com sucesso");

                            } catch (Exception e){
                                System.err.println(e.getMessage());
                            }
                            break;

                        case "3":
                            JOptionPane.showMessageDialog(null, "Cadastro de funcionário");

                            //Pedir as informações ao usuário
                            String cpf = JOptionPane.showInputDialog("Insira seu CPF: ");
                            String nome = JOptionPane.showInputDialog("Insira seu nome: ");
                            String dtNasc = JOptionPane.showInputDialog("Insira sua data de nascimento: ");
                            int anoAdmissao = Integer.parseInt(JOptionPane.showInputDialog("Em qual ano este " +
                                    "funcionário foi contratado?"));
                            int idLoca = Integer.parseInt(JOptionPane.showInputDialog("Qual o id da locadora em que " +
                                    "esse funcionário trabalha?"));


                            try {

                                Funcionario funcionario = new Funcionario(cpf, nome, dtNasc, anoAdmissao);

                                LocadoraDao locaDao = new LocadoraDao(conn);
                                Locadora locadora = locaDao.pesquisaId(idLoca);
                                funcionario.setLocadora(locadora);


                                funcionarioDao.cadastrar(funcionario);
                                System.out.println("Funcionário " + funcionario.getNome() + " cadastrado com sucesso!");

                            } catch (Exception e){
                                System.err.println(e.getMessage());
                            }
                            break;

                        case "4":
                            JOptionPane.showMessageDialog(null, "Cadastro de filmes!!!");

                            //Pedir os dados para o cliente
                            String nomeFilme = JOptionPane.showInputDialog("Insira o nome do filme");
                            String diretor = JOptionPane.showInputDialog("Insira o nome do diretor");
                            int ano = Integer.parseInt(JOptionPane.showInputDialog("Insira o ano de " +
                                    "lançamento do filme"));
                            String genero = JOptionPane.showInputDialog("Insira o gênero do filme");
                            String sinopse = JOptionPane.showInputDialog("Insira a sinopse do filme");
                            int duracao = Integer.parseInt(JOptionPane.showInputDialog("Insira a duração do " +
                                    "filme (em minutos)"));
                            String classificacao = JOptionPane.showInputDialog("Insira a classificação " +
                                    "indicativa do filme");

                            boolean vaiLocadora = JOptionPane.showConfirmDialog(null,
                                    "Deseja adicionar uma locadora?", "Confirmação",
                                    JOptionPane.YES_NO_OPTION) == 0;

                            //Instanciando o filme
                            Filme filme = new Filme(nomeFilme, diretor, ano, genero, sinopse, duracao, classificacao);

                            try {

                                if (vaiLocadora){
                                    int idLoc = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da locadora"));
                                    LocadoraDao locaDao = new LocadoraDao(ConnectionFactory.getConnection());
                                    Locadora locadora = locaDao.pesquisaId(idLoc);
                                    filme.setLocadora(locadora);
                                }

                                filmeDao.cadastrar(filme);
                                System.out.println("Filme cadastrado com sucesso!");
                            } catch (Exception e){
                                System.err.println();
                            }
                            break;

                        case "5":
                            JOptionPane.showMessageDialog(null, "Cadastro de aluguéis!");

                            //Pedindo os dados para o usuário
                            int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Qual o ID do cliente " +
                                    "que está alugando?"));
                            int idFilme = Integer.parseInt(JOptionPane.showInputDialog("Qual o ID do filme alugado?"));
                            String dtAluguel = JOptionPane.showInputDialog("Qual a data em que foi alugado?");
                            String devolucao = JOptionPane.showInputDialog("Qual a data de devolução?");

                            Aluguel aluguel = new Aluguel(dtAluguel, devolucao);

                            try {

                                //Pesquisando e setando a FK id_cliente
                                Cliente clienteAluguel = clienteDao.pesquisaId(idCliente);
                                aluguel.setCliente(clienteAluguel);

                                //Pesquisando e setando a FK id_filme
                                Filme filmeAluguel = filmeDao.pesquisaID(idFilme);
                                aluguel.setFilme(filmeAluguel);

                                //Executando o cadastro
                                aluguelDao.cadastrar(aluguel);
                                System.out.println("Filme " + aluguel.getFilme().getNome() +
                                        " alugado com sucesso por: " + aluguel.getCliente().getNome());

                            } catch (Exception e){
                                System.err.println(e.getMessage());
                            }
                            break;

                        case "6":
                            JOptionPane.showMessageDialog(null, "Atendimento cancelado." +
                                    "\nObrigado pela preferência!");
                            break;

                        default:
                            JOptionPane.showMessageDialog(null, "Opção inválida!");
                    }

                    break;

                case "2":
                    opcao = JOptionPane.showInputDialog("Qual você gostaria de listar?" +
                            "\n1- Filmes" +
                            "\n2- Clientes" +
                            "\n3- Aluguéis" +
                            "\n4- Locadoras" +
                            "\n5- Funcionários" +
                            "\n6- Cancelar");

                    switch (opcao){
                        case "1":

                            opcao = JOptionPane.showInputDialog("Escolha como quer listar:" +
                                    "\n1- Listar todos" +
                                    "\n2- Listar por ID" +
                                    "\n3- Listar por nome" +
                                    "\n4- Listar por genero" +
                                    "\n5- Listar por diretor" +
                                    "\n6- Listar por ano" +
                                    "\n7- Cancelar");

                            switch (opcao){
                                case "1":

                                    try {
                                        //Criando a lista de exibição
                                        List<Filme> lista = filmeDao.listar();

                                        for (Filme f : lista){
                                            System.out.println(f + "\n");
                                        }
                                        System.out.println("Filmes encontrados: " + lista.size());

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "2":
                                    //Perguntar o ID ao usuário
                                    int idFilme = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID do " +
                                            "filme desejado"));

                                    try {
                                        Filme filme = filmeDao.pesquisaID(idFilme);

                                        //Exibindo os dados encontrados
                                        System.out.println(filme);

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "3":
                                    //Pegar o nome do filme
                                    String nomeFilme = JOptionPane.showInputDialog("Insira o nome do filme");

                                    try {

                                        //Lista de exibição
                                        List<Filme> lista = filmeDao.pesquisaNome(nomeFilme);

                                        for (Filme f : lista){
                                            System.out.println(f + "\n");
                                        }
                                        System.out.println("Filmes encontrados: " + lista.size());

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "4":
                                    //Pedir o gênero que o usuário quer ver
                                    String genero = JOptionPane.showInputDialog("Qual gênero você gostaria de pesquisar?");

                                    try {

                                        //Criando a lista de exibição
                                        List<Filme> lista = filmeDao.pesquisaGenero(genero);

                                        for (Filme f : lista){
                                            System.out.println(f + "\n");
                                        }
                                        System.out.println("Filmes encontrados: " + lista.size());

                                    } catch (Exception e){
                                        System.out.println(e.getMessage());
                                    }
                                    break;

                                case "5":
                                    //Pedir o nome do diretor
                                    String diretor = JOptionPane.showInputDialog("Insira o nome do diretor");

                                    try {
                                        //Criando a lista de exibição
                                        List<Filme> lista = filmeDao.pesquisaDiretor(diretor);

                                        for (Filme f : lista){
                                            System.out.println(f + "\n");
                                        }
                                        System.out.println("Filmes encontrados: " + lista.size());

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "6":
                                    //Pedir o ano de lançamento para o usuário
                                    int ano = Integer.parseInt(JOptionPane.showInputDialog("Insira o ano para " +
                                            "se pesquisar"));

                                    try {
                                        //Criando a lista de exibição e exibindo os dados encontrados
                                        List<Filme> lista = filmeDao.pesquisaAnoLancamento(ano);

                                        for (Filme f : lista){
                                            System.out.println(f + "\n");
                                        }
                                        System.out.println("Filmes encontrados que foram lançados em "
                                                + ano + ": " + lista.size());

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "7":
                                    JOptionPane.showMessageDialog(null, "Atendimento cancelado!");
                                    break;

                                default:
                                    JOptionPane.showMessageDialog(null, "Opção Inválida!");
                                    break;
                            }

                        case "2":
                            opcao = JOptionPane.showInputDialog("Como gostaria de listar os clientes?" +
                                    "\n1- Todos" +
                                    "\n2- ID" +
                                    "\n3- Nome" +
                                    "\n4- Cancelar");

                            switch (opcao){
                                case "1":
                                    try {
                                        //Criando a lista de exibição
                                        List<Cliente> lista = clienteDao.listar();

                                        //Exibir os dados encontrados
                                        for (Cliente c : lista){
                                            System.out.println(c + "\n");
                                        }

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "2":
                                    //Perguntar o ID que quer pesquisar
                                    int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID " +
                                            "que deseja pesquisar: "));

                                    try {
                                        Cliente cliente = clienteDao.pesquisaId(id);

                                        //Exibindo os dados encontrados
                                        System.out.println(cliente);

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "3":
                                    //Pedir o nome do cliente
                                    String nome = JOptionPane.showInputDialog("Qual o nome do cliente que você deseja pesquisar?");

                                    try {
                                        //Criando a lista de exibição e exibindo os dados
                                        List<Cliente> lista = clienteDao.pesquisaNome(nome);

                                        for (Cliente c : lista){
                                            System.out.println(c + "\n");
                                        }
                                        System.out.println("Clientes encontrados: " + lista.size());
                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "4":
                                    JOptionPane.showMessageDialog(null, "Atendimento cancelado!");
                                    break;

                                default:
                                    JOptionPane.showMessageDialog(null, "Opção inválida!");
                                    break;


                            }

                        default:
                            JOptionPane.showMessageDialog(null, "Opção inválida!");
                            break;

                        case "3":
                            opcao = JOptionPane.showInputDialog("Como gostaria de listar os aluguéis?" +
                                    "\n1- Todos" +
                                    "\n2- ID" +
                                    "\n3- Cliente" +
                                    "\n4- Filmes" +
                                    "\n5- Data de devolução" +
                                    "\n6- Cacelar");

                            switch (opcao){
                                case "1":
                                    try {
                                        //Criando a lista de exibição e exibindo os resultados
                                        List<Aluguel> lista = aluguelDao.listar();

                                        for (Aluguel a : lista){
                                            System.out.println(a + "\n");
                                        }
                                        System.out.println("Aluguéis encontrados: " + lista.size());

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "2":
                                    int id = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do aluguel?"));

                                    try {
                                        Aluguel aluguel = aluguelDao.pesquisaId(id);

                                        //Exibindo os dados do aluguel
                                        System.out.println(aluguel);

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "3":
                                    //Pedir o ID do cliente
                                    int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do cliente?"));

                                    try {

                                        //Instanciando a lista de exibição
                                        List<Aluguel> lista = aluguelDao.pesquisaIdCliente(idCliente);

                                        //Exibindo os dados do aluguel
                                        for (Aluguel a : lista){
                                            System.out.println(a + "\n");
                                        }
                                        System.out.println("Aluguéis encontrados: " + lista.size());

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "4":
                                    //Pedir o ID do filme
                                    int idFilme = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do filme?"));

                                    try {
                                        List<Aluguel> lista = aluguelDao.pesquisaIdFilme(idFilme);

                                        //Exibindo os dados do aluguel
                                        for (Aluguel a : lista){
                                            System.out.println(a + "\n");
                                        }
                                        System.out.println("Quantidade de aluguéis encontrados: " + lista.size());

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "5":
                                    //Pedindo a data de devolução do aluguel
                                    String dtDevolucao = JOptionPane.showInputDialog("Qual a data de devolução?");

                                    try {
                                        //Criando a lista de exibição
                                        List<Aluguel> lista = aluguelDao.pesquisaDtDevolucao(dtDevolucao);

                                        for (Aluguel a : lista){
                                            System.out.println(a + "\n");
                                        }
                                        System.out.println("Aluguéis encontrados: " + lista.size());

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "6":
                                    JOptionPane.showMessageDialog(null, "Atendimento cancelado." +
                                            "\nObrigado pela preferência!");
                                    break;

                                default:
                                    JOptionPane.showMessageDialog(null, "Opção inválida!");
                                    break;
                            }

                        case "4":
                            opcao = JOptionPane.showInputDialog("Como gostaria de listar as locadoras?" +
                                    "\n1- Todas" +
                                    "\n2- ID" +
                                    "\n3- Endereço" +
                                    "\n4- Nome" +
                                    "\n5- Cacelar");

                            switch (opcao){
                                case "1":
                                    try {
                                        //Criando a lista para exibir os valores
                                        List<Locadora> lista = locadoraDao.pesquisar();

                                        for (Locadora l : lista){
                                            System.out.println(l + "\n");
                                        }
                                        System.out.println("\nLocadoras encontradas: " + lista.size());

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "2":
                                    //Pedindo o id para o usuário
                                    int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID da locadora: "));

                                    try {
                                        Locadora locadora = locadoraDao.pesquisaId(id);

                                        //Exibindo os dados pesquisados
                                        System.out.println(locadora);

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "3":
                                    //Pedir o endereço ao usuário
                                    String endereco = JOptionPane.showInputDialog("Insira o endereço do " +
                                            "qual você deseja ver as locadoras");

                                    try {

                                        //Criando a lista para exibir as informações
                                        List<Locadora> lista = locadoraDao.pesquisaEndereco(endereco);

                                        for (Locadora l : lista){
                                            System.out.println(l + "\n");
                                        }
                                        System.out.println("\nLocadoras encontradas: " + lista.size());
                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "4":
                                    //Pedir o nome da locadora desejada
                                    String nome = JOptionPane.showInputDialog("Insira o nome da Locadora " +
                                            "que você deseja pesquisar");

                                    try {
                                        List<Locadora> lista = locadoraDao.pesquisarNome(nome);

                                        //Percorrendo o método e imprimindo os valores encontrados
                                        for (Locadora l : lista){
                                            System.out.println(l + "\n");
                                        }
                                        System.out.println("\nLocadoras encontradas: " + lista.size());

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "5":
                                    JOptionPane.showMessageDialog(null, "Atendimento cancelado." +
                                            "\nObrigado pela preferência!");
                                    break;

                                default:
                                    JOptionPane.showMessageDialog(null, "Opção inválida!");

                                    break;
                            }

                        case "5":
                            opcao = JOptionPane.showInputDialog("Como gostaria de listar os funcionários?" +
                                    "\n1- Todos" +
                                    "\n2- ID" +
                                    "\n3- Locadora" +
                                    "\n4- Nome" +
                                    "\n5- Cacelar");

                            switch (opcao){
                                case "1":
                                    try {
                                        //Criando a lista de exibição dos funcionários
                                        List<Funcionario> lista = funcionarioDao.listar();

                                        for (Funcionario f : lista){
                                            System.out.println(f + "\n");
                                        }
                                        System.out.println("Funcionários encontrados: " + lista.size());

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "2":
                                    //Pedir o id para o usuário
                                    int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID do funcionário"));

                                    try {
                                        Funcionario funcionario = funcionarioDao.pesquisarId(id);

                                        //Exibindo os dados pesquisados
                                        System.out.println(funcionario);

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "3":
                                    //Pedindo o id ca locadora ao usuário
                                    int idConcess = Integer.parseInt(JOptionPane.showInputDialog("Qual a locadora " +
                                            "que você deseja " +
                                            "ver os funcionários?"));

                                    try {

                                        //Criando a lista de exibição e exibindo os dados encontrados
                                        List<Funcionario> lista = funcionarioDao.pesquisaPorLocadora(idConcess);

                                        for (Funcionario f : lista){
                                            System.out.println(f + "\n");
                                        }
                                        System.out.println("Funcionários que trabalham nessa locadora: " + lista.size());
                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "4":
                                    //Pedir o nome ao usuário
                                    String nome = JOptionPane.showInputDialog("Qual o nome do funcionário?");

                                    try {
                                        //Criando a lista de exibição
                                        List<Funcionario> lista = funcionarioDao.pesquisaNome(nome);

                                        for (Funcionario f : lista){
                                            System.out.println(f + "\n");
                                        }
                                        System.out.println("Funcionários encontrados: " + lista.size());

                                    } catch (Exception e){
                                        System.err.println(e.getMessage());
                                    }
                                    break;

                                case "5":
                                    JOptionPane.showMessageDialog(null, "Atendimento cancelado." +
                                            "\nObrigado pela preferência!");
                                    break;

                                default:
                                    JOptionPane.showMessageDialog(null, "Opção inválida!");
                                    break;
                            }

                        case "6":
                            JOptionPane.showMessageDialog(null, "Atendimento cancelado." +
                                    "\nObrigado pela preferência!");
                            break;

                    }
                    break;

                case "3":
                    opcao = JOptionPane.showInputDialog("Qual você gostaria de atualizar?:" +
                            "\n1- Cliente" +
                            "\n2- Filme" +
                            "\n3- Aluguel" +
                            "\n4- Locadora" +
                            "\n5- Funcionário" +
                            "\n6- Cancelar");

                    switch (opcao){
                        case "1":
                            //Pedindo as informações ao usuário
                            int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Insira o " +
                                    "ID do cliente que você deseja atualizar: "));
                            String cpfCliente = JOptionPane.showInputDialog("Insira o novo CPF: ");
                            String nomeCliente = JOptionPane.showInputDialog("Insira o novo nome: ");
                            String dtNasc = JOptionPane.showInputDialog("Insira a nova data de nascimento: ");
                            String enderecoCliente = JOptionPane.showInputDialog("Insira o novo endereço: ");
                            String email = JOptionPane.showInputDialog("Insira o novo Email: ");

                            try {
                                //Instanciando o Cliente
                                Cliente cliente = new Cliente(idCliente, cpfCliente, nomeCliente, dtNasc, enderecoCliente, email);

                                clienteDao.atualizar(cliente);
                                System.out.println("Cliente atualizado com sucesso!");

                            } catch (Exception e){
                                System.err.println(e.getMessage());
                            }
                            break;

                        case "2":
                            //Pedir os dados para o cliente
                            int idFilme = Integer.parseInt(JOptionPane.showInputDialog("Insira o " +
                                    "ID do filme que deseja atualizar"));
                            String nomeFilme = JOptionPane.showInputDialog("Insira o nome do filme");
                            String diretor = JOptionPane.showInputDialog("Insira o nome do diretor");
                            int ano = Integer.parseInt(JOptionPane.showInputDialog("Insira o ano de lançamento do filme"));
                            String genero = JOptionPane.showInputDialog("Insira o gênero do filme");
                            String sinopse = JOptionPane.showInputDialog("Insira a sinopse do filme");
                            int duracao = Integer.parseInt(JOptionPane.showInputDialog("Insira a duração " +
                                    "do filme (em minutos)"));
                            String classificacao = JOptionPane.showInputDialog("Insira a classificação " +
                                    "indicativa do filme");

                            boolean uptLocadora = JOptionPane.showConfirmDialog(null,
                                    "Deseja mudar a locadora?", "Confirmação", JOptionPane.YES_NO_OPTION) == 0;

                            Filme filme = new Filme(idFilme, nomeFilme, diretor, ano, genero, sinopse, duracao, classificacao);

                            try {

                                if (uptLocadora){
                                    int idLoca = Integer.parseInt(JOptionPane.showInputDialog("Qual o novo " +
                                            "ID da locadora? "));

                                    LocadoraDao daoLoca = new LocadoraDao(conn);
                                    Locadora locadora = daoLoca.pesquisaId(idLoca);
                                    filme.setLocadora(locadora);
                                }

                                filmeDao.atualizar(filme);
                                System.out.println("Update realizado com sucesso!");

                            } catch (Exception e){
                                System.err.println(e.getMessage());
                            }
                            break;

                        case "3":
                            //Pedindo os dados para o cliente
                            int idAluguel = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do aluguel do qual você deseja atualizar?"));
                            int idFilme2 = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do filme?"));
                            int idCliente2 = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do cliente?"));
                            String dtAluguel = JOptionPane.showInputDialog("Qual a data do aluguel?");
                            String dtDevolucao = JOptionPane.showInputDialog("Qual a data de devolução?");

                            //Instanciando um aluguel
                            Aluguel aluguel = new Aluguel(idAluguel, dtAluguel, dtDevolucao);

                            try {

                                //Setando os id do filme e do cliente
                                Filme filmeUpdate = filmeDao.pesquisaID(idFilme2);
                                aluguel.setFilme(filmeUpdate);

                                Cliente clienteUpdate = clienteDao.pesquisaId(idCliente2);
                                aluguel.setCliente(clienteUpdate);

                                //Executando a atualização
                                aluguelDao.update(aluguel);
                                System.out.println("Update realizado com sucesso!");

                            } catch (Exception e){
                                System.err.println(e.getMessage());
                            }
                            break;

                        case "4":
                            //Pedindo o id e os dados a serem modificados para o usuário
                            int idLocadora = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID da " +
                                    "locadora cujo você quer modificar"));
                            String nomeLocadora = JOptionPane.showInputDialog("Insira o novo nome");
                            String enderecoLocadora = JOptionPane.showInputDialog("Insira o novo endereço");
                            String telefone = JOptionPane.showInputDialog("Insira o novo telefone(Não obrigatório)");

                            try {
                                //Instanciando a locadora
                                Locadora locadora = new Locadora(idLocadora, nomeLocadora, enderecoLocadora, telefone);

                                locadoraDao.atualizar(locadora);
                                System.out.println("Locadora atualizada com sucesso!");

                            } catch (Exception e){
                                System.err.println(e.getMessage());
                            }
                            break;

                        case "5":
                            //Pedir os dados para o usuário
                            int idFunc = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do " +
                                    "funcionário que você deseja modificar?"));
                            String cpfFunc = JOptionPane.showInputDialog("Insira o novo CPF: ");
                            String nomeFunc = JOptionPane.showInputDialog("Insira o novo nome: ");
                            String dtNascFunc = JOptionPane.showInputDialog("Insira a nova data de nascimento: ");
                            int anoAdmissao = Integer.parseInt(JOptionPane.showInputDialog("Em qual ano " +
                                    "este funcionário foi contratado?"));
                            int idLoca = Integer.parseInt(JOptionPane.showInputDialog("Qual o id da locadora em que " +
                                    "esse funcionário trabalha?"));

                            Funcionario funcionario = new Funcionario(idFunc, cpfFunc, nomeFunc, dtNascFunc, anoAdmissao);

                            try {
                                Locadora locadora = locadoraDao.pesquisaId(idLoca);
                                funcionario.setLocadora(locadora);

                                funcionarioDao.atualizar(funcionario);
                                System.out.println("Funcionário atualizado com sucesso!");

                            } catch (Exception e){
                                System.err.println(e.getMessage());
                            }
                            break;

                        case "6":
                            JOptionPane.showMessageDialog(null, "Atendimento cancelado." +
                                    "\nObrigado pela preferência!");
                            break;

                        default:
                            JOptionPane.showMessageDialog(null, "Opção inválida!");
                            break;
                    }
                    break;

                case "4":
                    opcao = JOptionPane.showInputDialog("O que você gostaria de deletar?" +
                            "\n1- Cliente" +
                            "\n2- Filme" +
                            "\n3- Aluguel" +
                            "\n4- Locadora" +
                            "\n5- Funcionário" +
                            "\n6- Cancelar");

                    switch (opcao){
                        case "1":
                            //Pedir o ID ao usuário
                            int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID do " +
                                    "cliente que você deseja deletar: "));

                            try {
                                //Executando o delete
                                clienteDao.apagar(idCliente);
                                System.out.println("Cliente apagado com sucesso!");
                            } catch (Exception e){
                                System.err.println(e.getMessage());
                            }
                            break;

                        case "2":
                            //Pedindo o id para o usuário
                            int idFilme = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID do " +
                                    "filme que deseja deletar"));

                            try {
                                //Executando o delete
                                filmeDao.apagar(idFilme);
                                System.out.println("Filme deletado com sucesso!");

                            } catch (Exception e){
                                System.err.println(e.getMessage());
                            }
                            break;

                        case "3":
                            //Pedindo o id para o usuário
                            int idAluguel = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID do " +
                                    "aluguel que deseja deletar"));

                            try {
                                //Executando o delete
                                aluguelDao.deletar(idAluguel);
                                System.out.println("Filme deletado com sucesso!");

                            } catch (Exception e){
                                System.err.println(e.getMessage());
                            }
                            break;

                        case "4":
                            //Pedindo o id para o usuário
                            int idLocadora = Integer.parseInt(JOptionPane.showInputDialog("Insira o ID da " +
                                    "locadora que deseja deletar"));

                            try {
                                //Executando o delete
                                locadoraDao.apagar(idLocadora);
                                System.out.println("Locadora deletada com sucesso!");

                            } catch (Exception e){
                                System.err.println(e.getMessage());
                            }
                            break;

                        case "5":
                            //Pedindo o id para o usuário
                            int idFuncionario = Integer.parseInt(JOptionPane.showInputDialog("Insira o id do " +
                                    "funcionário que você deseja deletar"));

                            try {
                                funcionarioDao.apagar(idFuncionario);
                                System.out.println("Funcionário deletado com sucesso!");
                            } catch (Exception e){
                                System.err.println(e.getMessage());
                            }
                            break;

                        case "6":
                            JOptionPane.showMessageDialog(null, "Atendimento cancelado." +
                                    "\nObrigado pela preferência!");
                            break;

                        default:
                            JOptionPane.showMessageDialog(null, "Opção inválida!");
                    }
                    break;

                case "5":
                    JOptionPane.showMessageDialog(null, "Atendimento cancelado." +
                            "\nObrigado pela preferência!");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }

    }
}
