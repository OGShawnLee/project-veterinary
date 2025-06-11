package com.veterinary.business.dto;

import com.veterinary.business.Validator;

import org.mindrot.jbcrypt.BCrypt;

public class Account {
  public enum Role {
    SECRETARY,
    VETERINARY,
    STOCK_MANAGER,
    ADMINISTRATOR;

    public static Role fromStaffRole(StaffDTO.Role staffRole) {
      return switch (staffRole) {
        case SECRETARY -> Role.SECRETARY;
        case VETERINARY -> Role.VETERINARY;
        case STOCK_MANAGER -> Role.STOCK_MANAGER;
      };
    }
  }

  private final String email;
  private final String displayName;
  private final String encryptedPassword;
  private final Role role;


  public Account(String email, String displayName, String encryptedPassword, Role role) {
    this.email = Validator.getValidEmail(email);
    this.displayName = Validator.getValidDisplayName(displayName);
    this.encryptedPassword = Validator.getValidString(encryptedPassword, "Contraseña", 8, 64);
    this.role = role;
  }

  public String getEmail() {
    return email;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }

  public Role getRole() {
    return role;
  }

  public boolean hasPasswordMatch(String candidate) {
    return BCrypt.checkpw(candidate, this.encryptedPassword);
  }
}