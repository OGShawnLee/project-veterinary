package com.veterinary.business.dto;

public class OwnerDTO extends Person {
  private final String id;
  private final String email;

  public OwnerDTO(OwnerBuilder builder) {
    super(builder);
    this.id = builder.id;
    this.email = builder.email;
  }

  public String getID() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public static class OwnerBuilder extends PersonBuilder<OwnerBuilder> {
    private String id;
    private String email;

    public OwnerBuilder setID(String id) {
      this.id = id;
      return this;
    }

    public OwnerBuilder setEmail(String email) {
      this.email = email;
      return this;
    }

    @Override
    public OwnerDTO build() {
      return new OwnerDTO(this);
    }
  }
}
