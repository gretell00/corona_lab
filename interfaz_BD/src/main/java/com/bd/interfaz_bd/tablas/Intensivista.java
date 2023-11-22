package com.bd.interfaz_bd.tablas;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Intensivista {
    private int solapin,nivel;
    private String nombre,sexo, telefono;

    public Intensivista(ResultSet resultSet) throws SQLException {
        try {
            this.solapin = resultSet.getInt("solapin");
        } catch (SQLException e) {
            solapin = -1;
        }
        try {
            this.nivel = resultSet.getInt("nivel");
        } catch (SQLException e) {
            nivel = -1;
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
        try {
            this.telefono = resultSet.getString("num_tel");
        } catch (SQLException e) {
            telefono = "notFound";
        }
    }

    @Override
    public String toString() {
        return "Intensivista{" +
                "solapin=" + solapin +
                ", nivel=" + nivel +
                ", nombre='" + nombre + '\'' +
                ", sexo='" + sexo + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
