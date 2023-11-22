package com.bd.interfaz_bd.tablas;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParteMedico {
    private int id_parte;
    private String fecha;
    private String estado_paciente;

    public ParteMedico(ResultSet resultSet) throws SQLException {
        try {
            this.id_parte = resultSet.getInt("id_parte");
        } catch (SQLException e) {
            this.id_parte = -1;
        }
        try {
            this.fecha = resultSet.getString("fecha");
        } catch (SQLException e) {
            this.fecha = "notFound";
        }
        try {
            this.estado_paciente = resultSet.getString("estado_paciente");
        } catch (SQLException e) {
            this.estado_paciente = "notFound";
        }
    }

    @Override
    public String toString() {
        return "ParteMedico{" +
                "id_parte=" + id_parte +
                ", fecha='" + fecha + '\'' +
                ", estado_paciente='" + estado_paciente + '\'' +
                '}';
    }
}
