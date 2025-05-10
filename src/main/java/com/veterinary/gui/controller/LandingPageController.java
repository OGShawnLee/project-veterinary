package com.veterinary.gui.controller;

import javafx.stage.Stage;

public class LandingPageController extends Controller {
  public void navigateToRegisterOwnerPage() {
    RegisterOwnerController.navigateToRegisterOwnerPage(getScene());
  }

  public void navigateToRegisterPetPage() {
    RegisterPetController.navigateToRegisterPetPage(getScene());
  }

  public static void navigateToLandingPage(Stage currentStage) {
    navigateTo(currentStage,"Página de Inicio","LandingPage");
  }
}
