package com.veterinary.business.dto;

import com.veterinary.business.Validator;

public class OwnerDTO extends Person {
  private final String email;

  public OwnerDTO(OwnerBuilder builder) {
    super(builder);
    this.email = builder.email;
  }

  public String getEmail() {
    return email;
  }

  public static class OwnerBuilder extends PersonBuilder<OwnerBuilder> {
    private String email;

    public OwnerBuilder setEmail(String email) {
      this.email = Validator.getValidEmail(email);
      return this;
    }

    @Override
    public OwnerDTO build() {
      return new OwnerDTO(this);
    }
  }
}
