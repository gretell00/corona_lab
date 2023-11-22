package com.bd.interfaz_bd;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class HelloController implements Initializable {
    @FXML
    public Button btn_izquierdo;
    @FXML
    public Button btn_derecha;
    @FXML
    public Pagination pagination;
    private final StackPane[] borderPanes = new StackPane[10];
    private String consultaEscogida;
    private String tablaEscogida;
    private final Map<TextField, TextField> textFieldsC = new HashMap<>();
    private final Map<TextField,TextField> textFieldsV = new HashMap<>();
    private final List<ComboBox<String>> listOperador = new ArrayList<>();
    private final List<AtomicBoolean> listAndOr = new ArrayList<>();
    private final Label salida = new Label( "Salida\n\n");
    private final String[] tablas = {
            "Paciente",
            "Parte Medico",
            "Medicamento",
            "Registro",
            "Inspector",
            "Intensivista",
            "Sala",
            "Cuidado"
    };
    private final String[] consultas = {
            "Select",
            "Insert",
            "Delete",
            "Update"
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);

        btn_derecha.setOnMousePressed(mouseEvent -> btn_derecha.setStyle("-fx-background-color: rgba(169, 169, 169, 0.7)"));
        btn_derecha.setOnMouseReleased(mouseEvent -> btn_derecha.setStyle("-fx-background-color: rgba(169, 169, 169, 0.428)"));
        btn_derecha.setOnMouseClicked(mouseEvent -> pagination.setCurrentPageIndex(pagination.getCurrentPageIndex() + 1));

        btn_izquierdo.setOnMousePressed(mouseEvent -> btn_izquierdo.setStyle("-fx-background-color: rgba(169, 169, 169, 0.7)"));
        btn_izquierdo.setOnMouseReleased(mouseEvent -> btn_izquierdo.setStyle("-fx-background-color: rgba(169, 169, 169, 0.428)"));
        btn_izquierdo.setOnMouseClicked(mouseEvent -> pagination.setCurrentPageIndex(pagination.getCurrentPageIndex() - 1));

        createPages();

        pagination.setPageFactory(integer -> {

            btn_derecha.setVisible(integer == 0);
            btn_izquierdo.setVisible(integer > 0);

            return borderPanes[integer];
        });
        pagination.setPageCount(5);
    }

    public void createPages() {
        for (int i = 0; i < borderPanes.length; i++) {
            borderPanes[i] = new StackPane();
            switch (i) {
                case 0 -> {
                    Label holaLabel = new Label("Hola, Bienvenido \n A");
                    Label coronaLabel = new Label("CoronaLab");
                    VBox vBox = new VBox(10);

                    holaLabel.setTextFill(Color.WHITE);
                    holaLabel.setFont(Font.font("Century Gothic", 40));
                    holaLabel.setAlignment(Pos.CENTER);
                    holaLabel.setTextAlignment(TextAlignment.CENTER);

                    coronaLabel.setTextFill(Color.WHITE);
                    coronaLabel.setFont(Font.font("Century Gothic", 60));
                    coronaLabel.setAlignment(Pos.CENTER);
                    coronaLabel.setTextAlignment(TextAlignment.CENTER);
                    coronaLabel.setStyle("");

                    vBox.setAlignment(Pos.CENTER);
                    vBox.getChildren().add(holaLabel);
                    vBox.getChildren().add(coronaLabel);

                    StackPane.setAlignment(vBox, Pos.CENTER);
                    borderPanes[i].getChildren().add(vBox);
                }

                case 1 -> {
                    Label seleccionLabel = new Label("Seleccione la consulta realizar");
                    Button[] buttons = new Button[4];
                    CheckBox[] checkBox = new CheckBox[4];
                    VBox vBox = new VBox(15);

                    vBox.getChildren().add(seleccionLabel);

                    for (int j = 0; j < buttons.length; j++) {
                        buttons[j] = new Button(consultas[j]);
                        checkBox[j] = new CheckBox();
                        checkBox[j].setDisable(true);
                        checkBox[j].setId("check");

                        buttons[j].setGraphic(checkBox[j]);
                        buttons[j].setFont(Font.font("Arial Rounded MT Bold", 30));
                        buttons[j].setTextFill(Color.WHITE);
                        buttons[j].setStyle("-fx-background-color: transparent");
                        buttons[j].setAlignment(Pos.TOP_LEFT);

                        int finalJ = j;
                        Timeline time = new Timeline(keys(buttons[j]));
                        int finalJ1 = j;

                        buttons[j]
                                .setOnMouseClicked(mouseEvent -> {
                                            for (int k = 0; k < buttons.length; k++) {
                                                if (k == finalJ) {
                                                    ((CheckBox) buttons[finalJ].getGraphic())
                                                            .setSelected(!((CheckBox) buttons[finalJ].getGraphic()).isSelected());
                                                    buttons[finalJ].setStyle(
                                                            ((CheckBox) buttons[finalJ].getGraphic()).isSelected()
                                                                    ? "-fx-background-color: rgb(19, 55, 109, 0.7); -fx-background-radius: 20px;"
                                                                    : "-fx-background-color: transparent"
                                                    );

                                                } else {
                                                    ((CheckBox) buttons[k].getGraphic())
                                                            .setSelected(false);
                                                    buttons[k].setStyle("-fx-background-color: transparent");
                                                }
                                            }
                                            time.play();
                                            consultaEscogida = buttons[finalJ1].getText();
                                            pagination.setCurrentPageIndex(pagination.getCurrentPageIndex() + 1);
                                        }
                                );

                        buttons[j].setOnMousePressed(mouseEvent -> buttons[finalJ1].setFont(Font.font("Arial Rounded MT Bold", 24)));
                        buttons[j].setOnMouseReleased(mouseEvent -> buttons[finalJ1].setFont(Font.font("Arial Rounded MT Bold", 30)));

                        vBox.getChildren().add(buttons[j]);
                    }

                    seleccionLabel.setTextFill(Color.WHITE);
                    seleccionLabel.setFont(Font.font("Century Gothic", 40));
                    vBox.setAlignment(Pos.CENTER);

                    StackPane.setAlignment(vBox, Pos.CENTER);
                    borderPanes[i].getChildren().add(vBox);
                }
                case 2 -> {
                    Label seleccionLabel = new Label("Seleccione la tabla a utilizar");
                    Button[] buttons = new Button[tablas.length];
                    CheckBox[] checkBox = new CheckBox[tablas.length];
                    GridPane grid = new GridPane();
                    grid.setHgap(5);
                    grid.setVgap(5);

                    for (int j = 0; j < buttons.length; j++) {
                        buttons[j] = new Button(tablas[j]);
                        checkBox[j] = new CheckBox();
                        checkBox[j].setDisable(true);
                        checkBox[j].setId("check");

                        buttons[j].setGraphic(checkBox[j]);
                        buttons[j].setFont(Font.font("Arial Rounded MT Bold", 30));
                        buttons[j].setTextFill(Color.WHITE);
                        buttons[j].setStyle("-fx-background-color: transparent");
                        buttons[j].setAlignment(Pos.TOP_LEFT);

                        int finalJ = j;
                        Timeline time = new Timeline(keys(buttons[j]));
                        int finalJ1 = j;

                        buttons[j]
                                .setOnMouseClicked(mouseEvent -> {
                                            for (int k = 0; k < buttons.length; k++) {
                                                if (k == finalJ) {
                                                    ((CheckBox) buttons[finalJ].getGraphic())
                                                            .setSelected(!((CheckBox) buttons[finalJ].getGraphic()).isSelected());
                                                    buttons[finalJ].setStyle(
                                                            ((CheckBox) buttons[finalJ].getGraphic()).isSelected()
                                                                    ? "-fx-background-color: rgb(19, 55, 109, 0.7); -fx-background-radius: 20px;"
                                                                    : "-fx-background-color: transparent"
                                                    );

                                                } else {
                                                    ((CheckBox) buttons[k].getGraphic())
                                                            .setSelected(false);
                                                    buttons[k].setStyle("-fx-background-color: transparent");
                                                }
                                            }
                                            time.play();
                                        }
                                );

                        buttons[j].setOnMousePressed(mouseEvent -> buttons[finalJ1].setFont(Font.font("Arial Rounded MT Bold", 24)));
                        buttons[j].setOnMouseReleased(mouseEvent -> buttons[finalJ1].setFont(Font.font("Arial Rounded MT Bold", 30)));

                    }

                    int f1 = 0, f2 = 0;

                    for (int j = 0; j < buttons.length; j++) {
                        if (j%2==0)grid.add(buttons[j], 0, ++f1);
                        else grid.add(buttons[j], 1, ++f2);
                    }
                    grid.setAlignment(Pos.CENTER);

                    seleccionLabel.setTextFill(Color.WHITE);
                    seleccionLabel.setFont(Font.font("Century Gothic", 40));
                    seleccionLabel.setAlignment(Pos.CENTER);
                    seleccionLabel.setTextAlignment(TextAlignment.CENTER);
                    seleccionLabel.setPadding(new Insets(0,10,0,0));

                    StackPane.setAlignment(seleccionLabel, Pos.TOP_CENTER);
                    StackPane.setAlignment(grid, Pos.CENTER);
                    borderPanes[i].getChildren().add(seleccionLabel);
                    borderPanes[i].getChildren().add(grid);
                    borderPanes[i].setPadding(new Insets(10));

                    Button buttonContinuar = new Button("Continuar");
                    buttonContinuar.setFont(Font.font("Arial Rounded MT Bold", 30));
                    buttonContinuar.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, new CornerRadii(15), Insets.EMPTY)));
                    buttonContinuar.setTextFill(Color.WHITE);
                    buttonContinuar.setOnMouseClicked(mouseEvent -> {
                        for (Button b :
                                buttons) {
                            if (((CheckBox) b.getGraphic()).isSelected()){
                                tablaEscogida = b.getText();
                            }
                        }

                        pagination.setCurrentPageIndex(pagination.getCurrentPageIndex() + 1);

                        System.out.println(consultaEscogida);
                        System.out.println(tablaEscogida);
                    });

                    buttonContinuar.setOnMousePressed(mouseEvent -> buttonContinuar.setFont(Font.font("Arial Rounded MT Bold", 20)));
                    buttonContinuar.setOnMouseReleased(mouseEvent -> buttonContinuar.setFont(Font.font("Arial Rounded MT Bold", 30)));

                    StackPane.setAlignment(buttonContinuar, Pos.BOTTOM_CENTER);
                    borderPanes[i].getChildren().add(buttonContinuar);
                }
                case 3 -> {
                    Label seleccionLabel = new Label("Rellene los campos");
                    Label campos = new Label("Campos");
                    Label condicion = new Label("Condiciones");
                    Button buttonV = new Button("+");
                    Button buttonC = new Button("+");
                    Button finalizar = new Button("Finalizar");
                    BorderPane principal = new BorderPane();
                    BorderPane izq = new BorderPane();
                    BorderPane der = new BorderPane();
                    HBox hBox = new HBox(30);
                    VBox vB1 = new VBox(10);
                    VBox vB2 = new VBox(10);

                    vB1.setAlignment(Pos.TOP_CENTER);
                    vB2.setAlignment(Pos.TOP_CENTER);
                    hBox.setAlignment(Pos.TOP_CENTER);

                    buttonV.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, new CornerRadii(15), Insets.EMPTY)));
                    buttonV.setFont(Font.font("Arial Rounded MT Bold", 15));
                    buttonV.setTextFill(Color.WHITE);
                    buttonV.setAlignment(Pos.CENTER);

                    buttonC.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, new CornerRadii(15), Insets.EMPTY)));
                    buttonC.setFont(Font.font("Arial Rounded MT Bold", 15));
                    buttonC.setTextFill(Color.WHITE);
                    buttonC.setAlignment(Pos.CENTER);

                    finalizar.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, new CornerRadii(15), Insets.EMPTY)));
                    finalizar.setFont(Font.font("Arial Rounded MT Bold", 30));
                    finalizar.setTextFill(Color.WHITE);
                    finalizar.setBorder(new Border(new BorderStroke(Color.WHITE,BorderStrokeStyle.SOLID, new CornerRadii(15), BorderStroke.THIN)));
                    finalizar.setAlignment(Pos.CENTER);
                    finalizar.setPadding(new Insets(20));

                    buttonV.setOnMouseClicked(mouseEvent -> addRow(vB1, 1));
                    buttonC.setOnMouseClicked(mouseEvent -> addRow(vB2, 2));

                    finalizar.setOnMouseClicked(mouseEvent -> {
                        System.out.println("[Valores]");
                        textFieldsV.forEach((textField, textField2) -> {
                            System.out.println("Campo: "+textField.getText());
                            System.out.println("Valor: "+textField2.getText());
                        });
                        System.out.println("[Condiciones]");
                        textFieldsC.forEach((textField, textField2) -> {
                            System.out.println("Campo: "+textField.getText());
                            System.out.println("Valor: "+textField2.getText());
                        });
                        pagination.setCurrentPageIndex(pagination.getCurrentPageIndex()+1);
                        try {
                            ConexionBD conexionBD = new ConexionBD();

                            salida.setText(salida.getText() +
                                    conexionBD.consulta(
                                            consultaEscogida,
                                            tablaEscogida,
                                            textFieldsC,
                                            textFieldsV,
                                            listOperador,
                                            listAndOr
                                    ));

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (IndexOutOfBoundsException e ){
                            System.err.println("error");
                        }
                    });

                    izq.setTop(campos);
                    izq.setCenter(vB1);
                    izq.setBottom(buttonV);

                    der.setTop(condicion);
                    der.setCenter(vB2);
                    der.setBottom(buttonC);

                    BorderPane.setAlignment(buttonV, Pos.CENTER);
                    BorderPane.setAlignment(buttonC, Pos.CENTER);

                    addRow(vB1, 1);
                    addRow(vB2, 2);

                    hBox.getChildren().add(izq);
                    hBox.getChildren().add(der);

                    buttonV.setOnMousePressed(mouseEvent -> buttonV.setFont(Font.font("Arial Rounded MT Bold", 10)));
                    buttonV.setOnMouseReleased(mouseEvent -> buttonV.setFont(Font.font("Arial Rounded MT Bold", 15)));

                    buttonC.setOnMousePressed(mouseEvent -> buttonC.setFont(Font.font("Arial Rounded MT Bold", 10)));
                    buttonC.setOnMouseReleased(mouseEvent -> buttonC.setFont(Font.font("Arial Rounded MT Bold", 15)));

                    finalizar.setOnMousePressed(mouseEvent -> finalizar.setFont(Font.font("Arial Rounded MT Bold", 20)));
                    finalizar.setOnMouseReleased(mouseEvent -> finalizar.setFont(Font.font("Arial Rounded MT Bold", 30)));

                    seleccionLabel.setTextFill(Color.WHITE);
                    seleccionLabel.setFont(Font.font("Century Gothic", 40));
                    seleccionLabel.setAlignment(Pos.TOP_CENTER);
                    seleccionLabel.setTextAlignment(TextAlignment.CENTER);
                    seleccionLabel.setPadding(new Insets(0,10,0,0));

                    campos.setTextFill(Color.WHITE);
                    campos.setFont(Font.font("Century Gothic", 25));
                    campos.setAlignment(Pos.TOP_CENTER);
                    campos.setTextAlignment(TextAlignment.CENTER);
                    campos.setPadding(new Insets(0,10,0,0));

                    condicion.setTextFill(Color.WHITE);
                    condicion.setFont(Font.font("Century Gothic", 25));
                    condicion.setAlignment(Pos.TOP_CENTER);
                    condicion.setTextAlignment(TextAlignment.CENTER);
                    condicion.setPadding(new Insets(0,10,0,0));

                    principal.setCenter(hBox);
                    principal.setTop(seleccionLabel);
                    principal.setBottom(finalizar);

                    BorderPane.setAlignment(seleccionLabel, Pos.CENTER);
                    BorderPane.setAlignment(hBox, Pos.TOP_CENTER);
                    BorderPane.setAlignment(finalizar, Pos.CENTER);

                    StackPane.setAlignment(principal, Pos.CENTER);

                    borderPanes[i].getChildren().add(principal);
                    borderPanes[i].setPadding(new Insets(10));
                }
                case 4 -> {
                    salida.setTextFill(Color.WHITE);
                    salida.setFont(Font.font(20));
                    salida.setTextAlignment(TextAlignment.JUSTIFY);
                    salida.setAlignment(Pos.CENTER);
                    borderPanes[i].getChildren().add(salida);
                    borderPanes[i].setPadding(new Insets(10));
                }
            }
        }
    }

    private void addRow(VBox vB, int i) {
        HBox h = new HBox(10);
        FlowPane flowPane = new FlowPane();
        TextField columna = new TextField();
        TextField valor = new TextField();
        ComboBox<String> operador = new ComboBox<>();
        RadioButton and = new RadioButton("And");
        RadioButton or = new RadioButton("Or");
        AtomicBoolean b = new AtomicBoolean(true);


        columna.setPromptText("Columna");
        valor.setPromptText("Valor");

        h.getChildren().add(columna);
        h.getChildren().add(valor);

        if (i == 1) textFieldsV.put(columna, valor);
        else{
            textFieldsC.put(columna, valor);
            operador.getItems().addAll("=","!=","<","<=",">=",">");
            operador.getSelectionModel().selectFirst();
            operador.getEditor().setFont(Font.font("Arial Black", 20));

            listOperador.add(operador);

            flowPane.setAlignment(Pos.TOP_CENTER);
            flowPane.setPadding(new Insets(0,30,0,0));
            and.setSelected(true);

            or.setOnMouseClicked(actionEvent -> {
                b.set(false);
                and.setSelected(false);
            });

            and.setOnMouseClicked(actionEvent -> {
                b.set(true);
                or.setSelected(false);
            });

            flowPane.getChildren().add(and);
            flowPane.getChildren().add(or);
            FlowPane.setMargin(and, new Insets(0,10,0,0));


            if (vB.getChildren().size() > 0){
                vB.getChildren().add(flowPane);
                listAndOr.add(b);
            }

            h.getChildren().add(operador);
        }

        h.setAlignment(Pos.TOP_CENTER);

        vB.getChildren().add(h);
    }

    private KeyFrame[] keys(Button buttons) {
        KeyFrame[] k = new KeyFrame[25];
        double huv = -0.002;

        for (int i = 0; i < k.length; i++) {
            k[i] = new KeyFrame(Duration.seconds(huv += 0.002), new KeyValue(buttons.styleProperty(), "-fx-background-color: rgb(19, 55, 109, 0.7); -fx-background-radius: " + i + "px;"));
        }

        return k;
    }

}