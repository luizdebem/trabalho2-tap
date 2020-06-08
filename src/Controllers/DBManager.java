package Controllers;

import javax.swing.JOptionPane;
import java.sql.*;
import Modelos.Produto;

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

//            Statement stmt = con.createStatement();
//            ResultSet rs
//                    = stmt.executeQuery("SELECT clienteId, nome, "
//                            + " cidade FROM cliente ");
//            while (rs.next()) {
//                int clienteId = rs.getInt("clienteId");
//                String nome = rs.getString("nome");
//                String cidade = rs.getString("cidade");
//                System.out.println(clienteId + nome + cidade);
//            }
//
//            con.close();
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
                            "(%d, '%s', '%s', '%s', %.2f, %d)", p.getCodigo(), p.getNome(), p.getModelo(), p.getMarca(),
                            p.getPreco(), p.getFoiDeletado()));
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

}
