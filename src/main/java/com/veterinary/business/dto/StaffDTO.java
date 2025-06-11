package com.veterinary.business.dto;

import com.veterinary.business.Validator;

public class StaffDTO extends Person{
  private final String displayName;
  private final String phoneNumber;

  public StaffDTO(StaffBuilder builder) {
    super(builder);
    this.displayName = builder.displayName;
    this.phoneNumber = builder.phoneNumber;
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

    public StaffBuilder setDisplayName(String displayName) {
      this.displayName = Validator.getValidName(displayName, "Nombre de usuario", 3, 32);
      return this;
    }

    public StaffBuilder setPhoneNumber(String phoneNumber) {
      this.phoneNumber = Validator.getValidPhoneNumber(phoneNumber);
      return this;
    }

    @Override
    public StaffDTO build() {
      return new StaffDTO(this);
    }
  }
}
