package com.bd.interfaz_bd;

import com.bd.interfaz_bd.tablas.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ConexionBD {
    Connection baseDatos = null;
    Statement st = null;
    String url="jdbc:postgresql://localhost:5432/Segundo Corte";
    String usuario="postgres";
    String contrasena="Liamir.21";
    String datos = "";

    public ConexionBD() throws SQLException {
        conectar();
    }

    public void conectar() throws SQLException {
        baseDatos = DriverManager.getConnection(url, usuario, contrasena);
        st = baseDatos.createStatement();
    }

    public String consulta(String consulta,
                         String tabla,
                         Map<TextField, TextField> textFieldsC,
                         Map<TextField, TextField> textFieldsV,
                         List<ComboBox<String>> listOperador,
                         List<AtomicBoolean> listAndOr) throws SQLException {
        AtomicReference<String> query = new AtomicReference<>(consulta + " ");
        AtomicInteger posOperador = new AtomicInteger();

        AtomicReference<String> asterisco = new AtomicReference<>();
        AtomicBoolean a = new AtomicBoolean(true);

        textFieldsV.forEach((textField, textField2) -> {
            if (a.get()) {
                asterisco.set(textField.getText());
                a.set(false);
            }
        });
        a.set(true);

        switch (consulta) {
            case "Select" -> {
                if (asterisco.get().contains("*")) {
                    query.set(query.get() + "* FROM " + tabla + "1;");
                }
                else {
                    textFieldsV.forEach((columna, valor) -> {
                        query.set(query.get() + tabla + "." + columna.getText() + ",");
                    });

                    query.set(query.get().substring(0, query.get().length() - 1));

                    query.set(query.get() + " FROM " + tabla + " WHERE ");

                    textFieldsC.forEach((columna, valor) -> {
                        query.set(query.get() + tabla + "." + columna.getText() + " " +
                                listOperador.get(posOperador.get()).getSelectionModel().getSelectedItem() + " " +
                                valor.getText() + " " +
                                createAndOr(listAndOr, posOperador) + " "
                        );
                    });

                    if (!listAndOr.isEmpty())
                        query.set(query.get()
                                .substring(0, query.get().length() - (listAndOr.get(0).get() ? 5 : 4)) + ";");
                    else query.set(query.get() + ";");

                }
            }
            case "Insert" -> {
                query.set(query.get() + "INTO " + tabla + "( ");

                textFieldsV.forEach((textField, textField2) -> {
                    query.set(query.get() + textField.getText() + " ,");
                });

                query.set(query.get().substring(0, query.get().length()-1) + " ) VALUES ( ");

                textFieldsV.forEach((textField, textField2) -> {
                    query.set(query.get() + textField2.getText() + " ,");
                });

                query.set(query.get().substring(0, query.get().length()-1) + " );");
            }

            case "Update" -> {
                query.set(query.get() + tabla + " SET ");

                textFieldsV.forEach((textField, textField2) -> {
                    query.set(query.get() + textField.getText() + " = " + textField2.getText() + " WHERE ");
                });


                textFieldsC.forEach((columna, valor) -> {
                    query.set(query.get() + columna.getText() + " " +
                            listOperador.get(posOperador.get()).getSelectionModel().getSelectedItem() + " " +
                            valor.getText() + " " +
                            createAndOr(listAndOr, posOperador) + " "
                    );
                });

                if (!listAndOr.isEmpty())
                    query.set(query.get()
                            .substring(0, query.get().length() - (listAndOr.get(0).get() ? 5 : 4)) + ";");
                else query.set(query.get() + ";");

            }

            case "Delete" -> {
                query.set(query.get() + " FROM " + tabla + " WHERE ");

                textFieldsC.forEach((columna, valor) -> {
                    query.set(query.get() + columna.getText() + " " +
                            listOperador.get(posOperador.get()).getSelectionModel().getSelectedItem() + " " +
                            valor.getText() + " " +
                            createAndOr(listAndOr, posOperador) + " "
                    );
                });

                if (!listAndOr.isEmpty())
                    query.set(query.get()
                            .substring(0, query.get().length() - (listAndOr.get(0).get() ? 5 : 4)) + ";");
                else query.set(query.get() + ";");
            }
        }
        System.out.println(query.get());

        ResultSet resultSet = st.executeQuery(query.get());

        while (resultSet.next()){
            switch (tabla){
                case "Paciente" -> datos += new Paciente(resultSet) + "\n";
                case "Parte Medico" -> datos += new ParteMedico(resultSet) + "\n";
                case "Medicamento" -> datos += new Medicamento(resultSet) + "\n";
                case "Registro" -> datos += new Registro(resultSet) + "\n";
                case "Inspector" -> datos += new Inspector(resultSet) + "\n";
                case "Sala" -> datos += new Sala(resultSet) + "\n";
                case "Intensivista" -> datos += new Intensivista(resultSet) + "\n";
                case "Cuidado" -> datos += new Cuidado(resultSet) + "\n";
            }
        }

        resultSet.close();
        baseDatos.close();
        st.close();

        return datos;
    }

    private String createAndOr(List<AtomicBoolean> listAndOr, AtomicInteger i) {
        if ( listAndOr.size() > i.get()) {
            String f = listAndOr.get(i.get()).get() ? "And" : "Or";
            i.incrementAndGet();
            return f;
        }
        else if(listAndOr.isEmpty()) return "";
        else return listAndOr.get(0).get() ? "And" : "Or";
    }
}
