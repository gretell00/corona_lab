package com.bd.interfaz_bd.tablas;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Cuidado {
    private String calidad;
    private String evaluacion;
    private int nivel;

    public Cuidado(ResultSet resultSet) throws SQLException {
        try {
            this.calidad = resultSet.getString("calidad");
        } catch (SQLException e) {
            calidad = "notFound";
        }
        try {
            this.evaluacion = resultSet.getString("evaluacion");
        } catch (SQLException e) {
            evaluacion = "notFound";
        }
        try {
            this.nivel = resultSet.getInt("nivel");
        } catch (SQLException e) {
            nivel = -1;
        }
    }

    @Override
    public String toString() {
        return "Cuidado{" +
                "calidad='" + calidad + '\'' +
                ", evaluacion='" + evaluacion + '\'' +
                ", nivel=" + nivel +
                '}';
    }
}
