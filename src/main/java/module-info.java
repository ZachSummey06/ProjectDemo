module edu.utsa.cs3443.projectdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens edu.utsa.cs3443.projectdemo to javafx.fxml;
    exports edu.utsa.cs3443.projectdemo;
}