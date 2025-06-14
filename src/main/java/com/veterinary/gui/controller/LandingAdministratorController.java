package com.veterinary.gui.controller;

import com.veterinary.gui.Modal;

public class LandingAdministratorController extends Controller {
  public void handleLogOut() {
    Modal.displayInformation("La funcionalidad de cerrar sesión no está disponible aún.");
  }

  public void handleOpenRegisterOwner() {
    Modal.display("Registrar Dueño", "RegisterOwnerModal");
  }

  public void handleOpenRegisterPet() {
    Modal.display("Registrar Mascota", "RegisterPetModal");
  }

  public void handleOpenRegisterProduct() {
    Modal.display("Registrar Producto", "RegisterProductModal");
  }

  public void navigateToReviewOwnerListPage() {
    navigateFromThisPageTo("Consultar Dueños", "ReviewOwnerListPage");
  }

  public void navigateToReviewEmployeeListPage() {
    Modal.display("Consultar Empleados", "ReviewStaffListPage");
  }

  public void navigateToReviewPetListPage() {
    navigateFromThisPageTo("Consultar Mascotas", "ReviewPetListPage");
  }

  public void navigateToReviewAppointmentListPage() {
    Modal.displayInformation("La funcionalidad de revisar la lista de citas no está disponible aún.");
  }

  public void navigateToReviewDiagnosisListPage() {
    Modal.displayInformation("La funcionalidad de revisar la lista de diagnósticos no está disponible aún.");
  }

  public void navigateToReviewProductListPage() {
    navigateFromThisPageTo("Consultar Productos", "ReviewProductListPage");
  }

  public void navigateToReviewSicknessListPage() {
    navigateFromThisPageTo("Consultar Enfermedades", "ReviewSicknessListPage");
  }

  public void navigateToReviewSaleListPage() {
    navigateFromThisPageTo("Consultar Ventas", "ReviewSaleListPage");
  }

  public void handleOpenRegisterEmployee() {
    Modal.display("Registrar Empleado", "RegisterStaffModal");
  }

  public void handleOpenRegisterAppointment() {
    Modal.displayInformation("La funcionalidad de registrar citas no está disponible aún.");
  }

  public void handleOpenRegisterDiagnosis() {
    Modal.displayInformation("La funcionalidad de registrar diagnósticos no está disponible aún.");
  }

  public void handleOpenRegisterSickness() {
    Modal.display("Registrar Enfermedad", "RegisterSicknessModal");
  }
}