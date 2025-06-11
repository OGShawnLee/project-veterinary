package com.veterinary.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.veterinary.gui.Modal;

import java.io.IOException;
import java.util.Objects;

public abstract class Controller {
  @FXML
  protected AnchorPane container;

  protected Stage getScene() {
    return (Stage) container.getScene().getWindow();
  }

  public void navigateToLandingPage() {
    navigateFromThisPageTo("Página de Inicio", "LandingAdministratorPage");
  }

  protected void navigateFromThisPageTo(String pageName, String resourceFileName) {
    navigateTo(getScene(), pageName, resourceFileName);
  }

  protected static void navigateTo(Stage currentStage, String pageName, String resourceFileName) {
    try {
      Parent newView = FXMLLoader.load(
        Objects.requireNonNull(
          Controller.class.getResource("/com/veterinary/" + resourceFileName + ".fxml")
        )
      );
      Scene newScene = new Scene(newView);

      currentStage.setScene(newScene);
      currentStage.show();
    } catch (IOException e) {
      Modal.displayError("No ha sido posible navegar a: " + pageName + " debido a un error de sistema.");
    }
  }
}
