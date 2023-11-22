package com.bd.interfaz_bd.tablas;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Sala {
    private int piso;
    private int num_sala;
    private int cant_medicos;
    private int cant_instrumentos;
    private final ResultSet resultSet;

    public Sala(ResultSet resultSet){
        this.resultSet = resultSet;
        llenarDatos();
    }

    public void llenarDatos(){
        try {
            this.piso = resultSet.getInt("piso");
        } catch (SQLException e) {
            piso = -1;
        }
        try {
            this.num_sala = resultSet.getInt("num_sala");
        } catch (SQLException e) {
            num_sala = -1;
        }
        try {
            this.cant_medicos = resultSet.getInt("cant_medicos");
        } catch (SQLException e) {
            cant_medicos = -1;
        }
        try {
            this.cant_instrumentos = resultSet.getInt("cant_instrumentos");
        } catch (SQLException e) {
            cant_instrumentos = -1;
        }
    }

    @Override
    public String toString() {
        return "Sala{" +
                "piso=" + piso +
                ", num_sala=" + num_sala +
                ", cant_medicos=" + cant_medicos +
                ", cant_instrumentos=" + cant_instrumentos +
                '}';
    }
}
