package controllers;

import javax.swing.JOptionPane;
import java.sql.*;
import modelos.Produto;

public class DBManager {

    String dbdriver = "com.mysql.cj.jdbc.Driver";
    String dburl = "jdbc:mysql://localhost:3306/multisono";
    String dbusuario = "root";
    String dbsenha = "libertadores";

    public void conectar() {
        try {
            Class.forName(dbdriver);
            Connection con = DriverManager.getConnection(dburl, dbusuario, dbsenha);
            System.out.println("Conexão bem sucedida!");
        } catch (ClassNotFoundException e) {
            System.out.println("Falha em carregar o DRIVER " + e);
        } catch (SQLException e) {
            System.out.println("Falha na Conexão ou no SQL " + e);
        }
    }

    public void inserir(Produto p) {
        try {
            Connection con = DriverManager.getConnection(dburl, dbusuario, dbsenha);
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO Produtos VALUES"
                    + String.format(
                            "(%d, '%s', '%s', '%s', '%s',  %.2f, %d)", p.getCodigo(), p.getNome(), p.getModelo(), p.getMarca(),
                            p.getEstado(), p.getPreco(), p.getFoiDeletado()));
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            statement.close();
            con.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Erro: esse código já está cadastrado no sistema.");
            } else {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro na conexão com o banco de dados, ou os dados estão incorretos.");
            }
        }
    }

    public void excluir(int codigo) {
        try {
            Connection con = DriverManager.getConnection(dburl, dbusuario, dbsenha);
            Statement statement = con.createStatement();
            statement.executeUpdate("UPDATE Produtos SET foiDeletado = 1 WHERE codigo = " + codigo);
            if (statement.getUpdateCount() >= 1) {
                JOptionPane.showMessageDialog(null, "Produto marcado como apagado!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
            }
            statement.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro na conexão com o banco de dados, ou os dados estão incorretos.");
        }
    }

    public void excluirNome(String nome) {
        try {
            Connection con = DriverManager.getConnection(dburl, dbusuario, dbsenha);
            Statement statement = con.createStatement();
            statement.executeUpdate("UPDATE Produtos SET foiDeletado = 1 WHERE nome = '" + nome + "'");
            if (statement.getUpdateCount() >= 1) {
                JOptionPane.showMessageDialog(null, "Produto marcado como apagado!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
            }
            statement.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro na conexão com o banco de dados, ou os dados estão incorretos.");

        }
    }

    public Produto encontrarProduto(int codigo) {
        try {
            Connection con = DriverManager.getConnection(dburl, dbusuario, dbsenha);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Produtos WHERE codigo = " + codigo + " LIMIT 1");
            Produto p = null;

            if (rs == null) {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
                return null;
            }

            while (rs.next()) {
                int codigoProduto = rs.getInt("codigo");
                String nome = rs.getString("nome");
                String modelo = rs.getString("modelo");
                String marca = rs.getString("marca");
                String estado = rs.getString("estado");
                Double preco = rs.getDouble("preco");
                int foiDeletado = rs.getInt("foiDeletado");

                p = new Produto(codigoProduto, nome, modelo, marca, estado, preco, foiDeletado);
            }

            statement.close();
            con.close();

            return p;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro na conexão com o banco de dados, ou os dados estão incorretos.");

        } catch (NullPointerException np) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado");
        }
        return null;
    }

    public Produto encontrarProdutoNome(String nome) {
        try {
            Connection con = DriverManager.getConnection(dburl, dbusuario, dbsenha);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Produtos WHERE nome = '" + nome + "' LIMIT 1");
            Produto p = null;

            if (rs == null) {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
                return null;
            }

            while (rs.next()) {
                int codigoProduto = rs.getInt("codigo");
                String nomeProduto = rs.getString("nome");
                String modelo = rs.getString("modelo");
                String marca = rs.getString("marca");
                String estado = rs.getString("estado");
                Double preco = rs.getDouble("preco");
                int foiDeletado = rs.getInt("foiDeletado");

                p = new Produto(codigoProduto, nomeProduto, modelo, marca, estado, preco, foiDeletado);
            }

            statement.close();
            con.close();
            return p;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro na conexão com o banco de dados, ou os dados estão incorretos.");
        } catch (NullPointerException np) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado");
        }
        return null;
    }

    public void update(int codigo, Produto p) {
        try {
            Connection con = DriverManager.getConnection(dburl, dbusuario, dbsenha);
            Statement statement = con.createStatement();
            statement.executeUpdate("UPDATE Produtos SET "
                    + String.format("nome = '%s', modelo = '%s', marca = '%s', estado = '%s', preco = %.2f WHERE codigo = %d",
                            p.getNome(), p.getModelo(), p.getMarca(), p.getEstado(), p.getPreco(), codigo)
            );
            if (statement.getUpdateCount() >= 1) {
                JOptionPane.showMessageDialog(null, "Produto atualizado!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
            }
            statement.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro na conexão com o banco de dados, ou os dados estão incorretos.");

        }

    }

    public void listarLogico() { // os dados do banco menos aqueles marcados com foiDeletado = 1
        try {
            Connection con = DriverManager.getConnection(dburl, dbusuario, dbsenha);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Produtos WHERE foiDeletado = 0");
            Produto p = null;
            String result = "";

            if (rs == null) {
                JOptionPane.showMessageDialog(null, "Não há produtos a serem exibidos.");
            }

            while (rs.next()) {
                int codigoProduto = rs.getInt("codigo");
                String nome = rs.getString("nome");
                String modelo = rs.getString("modelo");
                String marca = rs.getString("marca");
                String estado = rs.getString("estado");
                Double preco = rs.getDouble("preco");
                int foiDeletado = rs.getInt("foiDeletado");

                p = new Produto(codigoProduto, nome, modelo, marca, estado, preco, foiDeletado);
                result += p.toString() + "\n";
            }

            statement.close();
            con.close();

            JOptionPane.showMessageDialog(null, result.length() > 0 ? result : "Não há produtos a serem exibidos.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro na conexão com o banco de dados, ou os dados estão incorretos.");
        }

    }

    public void listarFisico() { // os dados do banco
        try {
            Connection con = DriverManager.getConnection(dburl, dbusuario, dbsenha);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Produtos");
            Produto p = null;
            String result = "";

            if (rs == null) {
                JOptionPane.showMessageDialog(null, "Não há produtos a serem exibidos.");
            }

            while (rs.next()) {
                int codigoProduto = rs.getInt("codigo");
                String nome = rs.getString("nome");
                String modelo = rs.getString("modelo");
                String marca = rs.getString("marca");
                String estado = rs.getString("estado");
                Double preco = rs.getDouble("preco");
                int foiDeletado = rs.getInt("foiDeletado");

                p = new Produto(codigoProduto, nome, modelo, marca, estado, preco, foiDeletado);
                result += p.toString() + "\n";
            }

            statement.close();
            con.close();

            JOptionPane.showMessageDialog(null, result.length() > 0 ? result : "Não há produtos a serem exibidos.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro na conexão com o banco de dados, ou os dados estão incorretos.");
        }

    }

    public void esvaziarTabela() {
        try {
            Connection con = DriverManager.getConnection(dburl, dbusuario, dbsenha);
            Statement statement = con.createStatement();
            statement.executeUpdate("DELETE FROM Produtos");
            statement.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Tabela de produtos limpa!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro na conexão com o banco de dados, ou os dados estão incorretos.");
        }
    }

    public void qtdRegistros() {
        try {
            Connection con = DriverManager.getConnection(dburl, dbusuario, dbsenha);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM Produtos");
            int total = 0;
            
            while(rs.next()) {
                total = rs.getInt("COUNT(*)");   
            }
            statement.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Total de registros na tabela produtos: " + total);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro na conexão com o banco de dados, ou os dados estão incorretos.");
            System.out.println(e);
        }

    }

}
