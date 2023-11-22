package com.bd.interfaz_bd.tablas;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Medicamento {
    private int id_medicamento;
    private String tipo_medicamento;
    private String nombre;

    public Medicamento(ResultSet resultSet) throws SQLException {
        try {
            this.id_medicamento = resultSet.getInt("id_medicamento");
        } catch (SQLException e) {
            id_medicamento = -1;
        }
        try {
            this.tipo_medicamento = resultSet.getString("tipo_medicamento");
        } catch (SQLException e) {
            tipo_medicamento = "notFound";
        }
        try {
            this.nombre = resultSet.getString("nombre");
        } catch (SQLException e) {
            nombre = "notFound";
        }
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "id_medicamento=" + id_medicamento +
                ", tipo_medicamento='" + tipo_medicamento + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
