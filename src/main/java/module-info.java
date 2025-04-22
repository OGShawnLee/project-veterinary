module com.veterinary.veterinary {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;

  opens com.veterinary.veterinary to javafx.fxml;
  exports com.veterinary.veterinary;
}