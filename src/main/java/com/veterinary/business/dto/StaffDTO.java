package com.veterinary.business.dto;

import com.veterinary.business.Validator;

public class StaffDTO extends Person{
  public enum Role {
    SECRETARY, VETERINARY, STOCK_MANAGER
  }

  private final String displayName;
  private final String phoneNumber;
  private final Role role;

  public StaffDTO(StaffBuilder builder) {
    super(builder);
    this.displayName = builder.displayName;
    this.phoneNumber = builder.phoneNumber;
    this.role = builder.role;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public static class StaffBuilder extends Person.PersonBuilder<StaffBuilder> {
    private String displayName;
    private String phoneNumber;
    private Role role;

    public StaffBuilder setDisplayName(String displayName) {
      this.displayName = Validator.getValidName(displayName, "Nombre de usuario", 3, 32);
      return this;
    }

    public StaffBuilder setPhoneNumber(String phoneNumber) {
      this.phoneNumber = Validator.getValidPhoneNumber(phoneNumber);
      return this;
    }

    public StaffBuilder setRole(Role role) {
      this.role = role;
      return this;
    }

    @Override
    public StaffDTO build() {
      return new StaffDTO(this);
    }
  }
}
