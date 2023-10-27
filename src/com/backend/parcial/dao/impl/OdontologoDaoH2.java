package com.backend.parcial.dao.impl;

import com.backend.parcial.dao.H2Connection;
import com.backend.parcial.dao.IDao;
import com.backend.parcial.model.Odontologo;
import org.apache.log4j.Logger;

import java.sql.*;

public class OdontologoDaoH2 implements IDao<Odontologo> {
    private final Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);


    @Override
    public Odontologo registrar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologoPersistido = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ODONTOLOGO (ID, NUMEROMATRICULA, NOMBRE, APELLIDO) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, odontologo.getId());
            preparedStatement.setInt(2, odontologo.getNumeroMatricula());
            preparedStatement.setString(3, odontologo.getNombre());
            preparedStatement.setString(4, odontologo.getApellido());
            preparedStatement.execute();

            connection.commit();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                odontologoPersistido = new Odontologo(resultSet.getInt(1), odontologo.getNumeroMatricula(), odontologo.getNombre(), odontologo.getApellido());
            }

            LOGGER.info("Odontologo guardado: " + odontologoPersistido);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    LOGGER.info("Tuvimos un problema");
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                } catch (SQLException exception) {
                    LOGGER.error(exception.getMessage());
                    exception.printStackTrace();
                }
            }
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("No se pudo cerrar la conexion: " + ex.getMessage());
            }
        }


        return odontologoPersistido;
    }

    @Override
    public Odontologo buscarPorId(int id) {
        Odontologo odontologo = null;

        Connection connection = null;
        try {
            connection = H2Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ODONTOLOGOS WHERE ID = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                odontologo = crearObjetoOdontologo(rs);

            }
            LOGGER.info("Se ha encontrado el odontologo " + odontologo);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("Ha ocurrido un error al intentar cerrar la bdd. " + ex.getMessage());
                ex.printStackTrace();
            }
        }


        return odontologo;
    }


    private Odontologo crearObjetoOdontologo(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        int numeroMatricula = rs.getInt("numeroMatricula");
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");


        return new Odontologo(id, numeroMatricula, nombre, apellido);

    }
}