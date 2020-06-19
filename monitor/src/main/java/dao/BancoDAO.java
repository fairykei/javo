package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Usuario;

public class BancoDAO {

    public Usuario logar(Usuario u) {
        String SQL = "SELECT IDBANCO, EMAIL, SENHA FROM TBD_BANCO WHERE EMAIL = ? AND SENHA = ?";
        System.out.println(SQL);
        Connection cnx = new Banco().getInstance();
        
        Usuario user = new Usuario();

        try {

            PreparedStatement ps = cnx.prepareStatement(SQL);
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getSenha());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // System.out.println(rs);
                user.setId(rs.getInt("IDBANCO"));
                user.setEmail(rs.getString("EMAIL"));
                user.setSenha(rs.getString("SENHA"));

                return user;
            }

        } catch (SQLException sqlEx) {
            System.out.print("ERRO SQL0003: ");
            sqlEx.printStackTrace();
        } catch (Exception e) {
            System.out.print("ERRO DESC0001: ");
            e.getMessage();
        } finally {
            try {

                if (!cnx.isClosed()) {

                    cnx.close();

                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
}
