package com.bd.interfaz_bd.tablas;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Paciente {
    private int dni;
    private String sexo, municipio, pais, provincia;

    public Paciente(ResultSet resultSet) throws SQLException {
        try {
            this.dni = resultSet.getInt("dni");
        } catch (SQLException e) {
            dni = -1;
        }
        try {
            this.sexo = resultSet.getString("sexo");
        } catch (SQLException e) {
            sexo = "notFound";
        }
        try {
            this.municipio = resultSet.getString("nombre_municipio");
        } catch (SQLException e) {
            municipio = "notFound";
        }
        try {
            this.pais = resultSet.getString("nombre_pais");
        } catch (SQLException e) {
            pais = "notFound";
        }
        try {
            this.provincia = resultSet.getString("nombre_provincia");
        } catch (SQLException e) {
            provincia = "notFound";
        }
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "dni=" + dni +
                ", sexo='" + sexo + '\'' +
                ", municipio='" + municipio + '\'' +
                ", pais='" + pais + '\'' +
                ", provincia='" + provincia + '\'' +
                '}';
    }
}
