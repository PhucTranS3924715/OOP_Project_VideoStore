module Project {
    requires javafx.controls;
    requires javafx.fxml;


    opens Project to javafx.fxml;
    exports Project;
}