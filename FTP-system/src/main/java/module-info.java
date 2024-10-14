module dev.pvhg.ftpsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.pvhg.ftpsystem to javafx.fxml;
    exports dev.pvhg.ftpsystem;
}