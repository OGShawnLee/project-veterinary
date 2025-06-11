package com.veterinary.business.dto;

import com.veterinary.business.Validator;

public abstract class Person {
  private final String name;
  private final String paternalLastName;
  private final String maternalLastName;
  private final String street;
  private final String colony;
  private final int number;

  public Person(PersonBuilder<?> builder) {
    this.name = builder.name;
    this.paternalLastName = builder.paternalLastName;
    this.maternalLastName = builder.maternalLastName;
    this.street = builder.street;
    this.colony = builder.colony;
    this.number = builder.number;
  }

  public String getName() {
    return name;
  }

  public String getPaternalLastName() {
    return paternalLastName;
  }

  public String getMaternalLastName() {
    return maternalLastName;
  }

  public String getStreet() {
    return street;
  }

  public String getColony() {
    return colony;
  }

  public int getNumber() {
    return number;
  }

  public static abstract class PersonBuilder<T extends PersonBuilder<T>> {
    protected String name;
    protected String paternalLastName;
    protected String maternalLastName;
    protected String street;
    protected String colony;
    protected int number;

    public T self() {
      return (T) this;
    }

    public T setName(String name) throws IllegalArgumentException {
      this.name = Validator.getValidName(name, "Nombre", 3, 32);
      return self();
    }

    public T setPaternalLastName(String paternalLastName) throws IllegalArgumentException {
      this.paternalLastName = Validator.getValidName(
        paternalLastName,
        "Apellido Paterno",
        3,
        32
      );
      return self();
    }

    public T setMaternalLastName(String maternalLastName) throws IllegalArgumentException {
      this.maternalLastName = Validator.getValidName(
        maternalLastName,
        "Apellido Materno",
        3,
        32
      );
      return self();
    }

    public T setStreet(String street) throws IllegalArgumentException {
      this.street = Validator.getValidFlexibleName(street, "Calle", 3, 128);
      return self();
    }

    public T setColony(String colony) throws IllegalArgumentException {
      this.colony = Validator.getValidFlexibleName(colony, "Colonia", 3, 128);
      return self();
    }

    public T setNumber(int number) throws IllegalArgumentException {
      this.number = Validator.getValidPositiveInteger(String.valueOf(number));
      return self();
    }

    public abstract Person build();
  }
}
