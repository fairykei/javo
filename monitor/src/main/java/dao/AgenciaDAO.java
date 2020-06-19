/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Agencia;

/**
 *
 * @author Aluno
 */
public class AgenciaDAO {

    public List<Agencia> labs(int idUsuario) {
        String SQL = "SELECT * FROM TBD_AGENCIA WHERE IDBANCO = ?";
        System.out.println(SQL);
        Connection cnx = new Banco().getInstance();

        List<Agencia> list = new ArrayList<Agencia>();

        try {

            PreparedStatement ps = cnx.prepareStatement(SQL);
            ps.setInt(1, idUsuario);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Agencia(rs.getInt("ID_AGENCIA"), rs.getString("NOME"),rs.getString("LOCALIZACAO")));
            }

        } catch (SQLException sqlEx) {
            System.err.print("ERRO SQL0003: ");
            sqlEx.printStackTrace();
        } catch (Exception e) {
            System.err.print("ERRO DESC0001: ");
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
        return list;
    }
}
