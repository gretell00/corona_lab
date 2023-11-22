package com.bd.interfaz_bd.tablas;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Registro {
    private int id_registro,frecuencia;
    private String dosis,fecha;

    public Registro(ResultSet resultSet) {
        try {
            this.id_registro = resultSet.getInt("id_registro");
        } catch (SQLException e) {
            this.id_registro = -1;
        }
        try {
            this.frecuencia = resultSet.getInt("frecuencia");
        } catch (SQLException e) {
            this.frecuencia = -1;
        }
        try {
            this.dosis = resultSet.getString("dosis");
        } catch (SQLException e) {
            this.dosis = "notFound";
        }
        try {
            this.fecha = resultSet.getString("fecha");
        } catch (SQLException e) {
            this.fecha = "notFound";
        }
    }

    @Override
    public String toString() {
        return "Registro{" +
                "id_registro=" + id_registro +
                ", frecuencia=" + frecuencia +
                ", dosis='" + dosis + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
