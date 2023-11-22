package com.bd.interfaz_bd.tablas;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Inspector {
    private int solapin,anos_servicio;
    private String nombre,sexo;

    public Inspector(ResultSet resultSet) throws SQLException {
        try {
            this.solapin = resultSet.getInt("solapin");
        } catch (SQLException e) {
            solapin = -1;
        }
        try {
            this.anos_servicio = resultSet.getInt("años_servicio");
        } catch (SQLException e) {
            anos_servicio = -1;
        }
        try {
            this.nombre = resultSet.getString("nombre");
        } catch (SQLException e) {
            nombre = "notFound";
        }
        try {
            this.sexo = resultSet.getString("sexo");
        } catch (SQLException e) {
            sexo = "notFound";
        }
    }

    @Override
    public String toString() {
        return "Inspector{" +
                "solapin=" + solapin +
                ", anos_servicio=" + anos_servicio +
                ", nombre='" + nombre + '\'' +
                ", sexo='" + sexo + '\'' +
                '}';
    }
}
