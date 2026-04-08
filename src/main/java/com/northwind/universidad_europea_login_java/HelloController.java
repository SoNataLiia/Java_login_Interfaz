package com.northwind.universidad_europea_login_java;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Map;

public class HelloController {

    @FXML
    private TextField campoNombre;
    @FXML
    private PasswordField campoContrasena;
    @FXML
    private ComboBox<String> comboCargo;
    @FXML
    private TextArea areaTexto;
    @FXML
    private TableView<Map<String, String>> tablaUsuarios;
    @FXML
    private TableColumn<Map<String, String>, String> colNombre;
    @FXML
    private TableColumn<Map<String, String>, String> colContrasena;
    @FXML
    private TableColumn<Map<String, String>, String> colCargo;


    //datos
    @FXML
    private static final List<Map<String, String>> USUARIOS_VALIDOS = List.of(
            Map.of("nombre", "admin", "contrasena", "admin123", "cargo", "Administrador"),
            Map.of("nombre", "profesor", "contrasena", "prof2025", "cargo", "Profesor"),
            Map.of("nombre", "alumno", "contrasena", "alumno01", "cargo", "Alumno")
    );

    @FXML
    public void initialize() {
        comboCargo.getItems().addAll("Administrador", "Profesor", "Alumno");
        comboCargo.setPromptText("Selecciona una opción");

        // Настраиваем колонки таблицы — говорим какой ключ из Map показывать
        colNombre.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().get("nombre")));
        colContrasena.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().get("contrasena")));
        colCargo.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().get("cargo")));

        // Заполняем таблицу данными
        tablaUsuarios.getItems().addAll(USUARIOS_VALIDOS);
    }

    //limpiar
    @FXML
    private void accionLimpiar() {
        campoNombre.clear();
        campoContrasena.clear();
        comboCargo.setValue(null);
        comboCargo.setPromptText("Selecciona una opción");
        areaTexto.setText("Formulario limpiado.");
    }

    //entrar
    @FXML
    private void accionEntrar() {
        String nombre = campoNombre.getText().trim();
        String contrasena = campoContrasena.getText().trim();
        String cargo = comboCargo.getValue();

        if (nombre.isEmpty() || contrasena.isEmpty() || cargo == null) {
            areaTexto.setText("Por favor, completa todos los campos.");
            return;
        }

        boolean valido = USUARIOS_VALIDOS.stream().anyMatch(u ->
                u.get("nombre").equals(nombre) &&
                        u.get("contrasena").equals(contrasena) &&
                        u.get("cargo").equals(cargo)
        );

        if (valido) {
            areaTexto.setText("Acceso concedido. Bienvenido/a, "
                    + nombre + ".\nCargo: " + cargo + ".");
        } else {
            areaTexto.setText("Datos incorrectos. Comprueba nombre, contraseña y cargo.");
        }
        // Заполняем таблицу данными пользователей
        tablaUsuarios.getItems().addAll(USUARIOS_VALIDOS);
    }

    // salir
    @FXML
    private void accionSalir() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setHeaderText(null);
        alert.setContentText("¿Deseas cerrar la aplicación?");
        alert.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) Platform.exit();
        });
    }

    //logo

    private HBox crearCabecera() {
        HBox cabecera = new HBox(12);
        cabecera.setPadding(new Insets(12, 20, 12, 20));
        cabecera.setAlignment(Pos.CENTER_LEFT);
        cabecera.setStyle("-fx-background-color: white;");

        // busca ue.png
        File archivoLogo = new File("ue.png");
        if (archivoLogo.exists()) {
            Image img = new Image(archivoLogo.toURI().toString(), 80, 0, true, true);
            cabecera.getChildren().add(new ImageView(img));
        }

        Label titulo = new Label("Universidad\nEuropea");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        cabecera.getChildren().add(titulo);

        return cabecera;
    }

}