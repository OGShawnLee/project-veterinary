package com.veterinary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main {
  public static class App extends Application {
    public App() {
      System.out.println("MAIN: Creating Application...");
    }

    @Override
    public void start(Stage stage) throws IOException {
      FXMLLoader loader = new FXMLLoader(Main.class.getResource("LandingAdministratorPage.fxml"));
      Scene scene = new Scene(loader.load());
      stage.setTitle("Sistema Gestor de Veterinaria");
      stage.setScene(scene);
      stage.show();
    }

    @Override
    public void init() {
      System.out.println("INIT: Initializing Application...");
    }
  }

  public static void main(String[] args) {
    Application.launch(App.class);
  }
}