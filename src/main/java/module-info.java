module com.northwind.universidad_europea_login_java {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.northwind.universidad_europea_login_java to javafx.fxml;
    exports com.northwind.universidad_europea_login_java;
}