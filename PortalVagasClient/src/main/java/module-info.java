module vagas.portalvagasclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires com.google.gson;


    opens vagas.portalvagasclient to javafx.fxml;
    exports vagas.portalvagasclient;
}