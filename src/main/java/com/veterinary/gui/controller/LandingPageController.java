package com.veterinary.gui.controller;

import com.veterinary.gui.controller.register.RegisterOwnerController;
import com.veterinary.gui.controller.register.RegisterPetController;
import com.veterinary.gui.controller.register.RegisterProductController;
import javafx.stage.Stage;

public class LandingPageController extends Controller {
  public void navigateToRegisterOwnerPage() {
    RegisterOwnerController.navigateToRegisterOwnerPage(getScene());
  }

  public void navigateToRegisterPetPage() {
    RegisterPetController.navigateToRegisterPetPage(getScene());
  }

  public void navigateToRegisterProductPage() {
    RegisterProductController.navigateToRegisterProductPage(getScene());
  }

  public static void navigateToLandingPage(Stage currentStage) {
    navigateTo(currentStage,"Página de Inicio","LandingPage");
  }
}
