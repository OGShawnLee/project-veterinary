package com.veterinary.business;

public class Validator {
  private static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
  private static final String NAME_REGEX_SPANISH = "^[A-Za-zÑñÁáÉéÍíÓóÚúÜü\\s]+$";
  private static final String FLEXIBLE_NAME_REGEX = "^[A-Za-zÑñÁáÉéÍíÓóÚúÜü0-9\\s\\-_/.:]+$";
  private static final String PHONE_NUMBER_REGEX = "^[0-9]$";

  private static boolean isValidEmail(String email) {
    return isValidString(email) && email.trim().matches(EMAIL_REGEX);
  }

  private static boolean isValidName(String name, int minLength, int maxLength) {
    return isValidString(name, minLength, maxLength) && name.matches(NAME_REGEX_SPANISH);
  }

  private static boolean isValidString(String string) {
    return string != null && string.trim().length() > 0;
  }

  private static boolean isValidString(String value, int minLength, int maxLength) {
    if (value == null || value.trim().isEmpty()) {
      return false;
    }

    String trimmedString = value.trim();
    return trimmedString.length() >= minLength && trimmedString.length() <= maxLength;
  }

  public static String getValidPetSpecies(String value) throws IllegalArgumentException   {
    String finalValue = getValidString(value, "Especie");

    if (
        finalValue.equals("Dog") ||
        finalValue.equals("Cat")
    ) {
      return finalValue;
    }

    if (finalValue.equals("Perro")) {
      return "Dog";
    }

    if (finalValue.equals("Gato")) {
      return "Cat";
    }

    throw new IllegalArgumentException("Rol académico debe ser uno de los siguientes: Evaluador, Evaluador-Profesor, Profesor.");
  }

  public static String getValidPhoneNumber(String value) throws IllegalArgumentException {
    if (isValidString(value) && value.trim().matches(PHONE_NUMBER_REGEX)) {
      return value.trim();
    }

    throw new IllegalArgumentException("Número de teléfono debe ser una cadena de texto con el formato correcto.");
  }

  public static String getValidProductSpecies(String value) {
    String finalValue = getValidString(value, "Especie");

    if (
        finalValue.equals("Dog") ||
        finalValue.equals("Cat") ||
        finalValue.equals("Both")
    ) {
      return finalValue;
    }

    if (finalValue.equals("Perro")) {
      return "Dog";
    }

    if (finalValue.equals("Gato")) {
      return "Cat";
    }

    if (finalValue.equals("Ambos")) {
      return "Both";
    }

    throw new IllegalArgumentException("Rol académico debe ser uno de los siguientes: Evaluador, Evaluador-Profesor, Profesor.");
  }

  public static String getValidEmail(String value) throws IllegalArgumentException {
    if (isValidEmail(value)) {
      return value.trim();
    }

    throw new IllegalArgumentException("Correo Electrónico debe ser una cadena de texto con el formato correcto.");
  }

  public static String getValidFlexibleName(String value, String name, int minLength, int maxLength) throws IllegalArgumentException {
    if (isValidString(value, minLength, maxLength) && value.trim().matches(FLEXIBLE_NAME_REGEX)) {
      return value.trim();
    }

    throw new IllegalArgumentException(name + " no puede ser nulo o vacío.");
  }

  public static String getValidKind(String value) {
    String finalValue = getValidString(value, "Tipo");

    if (
        finalValue.equals("Accessory") ||
        finalValue.equals("Food") ||
        finalValue.equals("Medicine")
    ) {
      return finalValue;
    }

    if (finalValue.equals("Accesorio")) {
      return "Accessory";
    }

    if (finalValue.equals("Comida")) {
      return "Food";
    }

    if (finalValue.equals("Medicamento")) {
      return "Medicine";
    }

    throw new IllegalArgumentException("Rol académico debe ser uno de los siguientes: Evaluador, Evaluador-Profesor, Profesor.");
  }

  public static int getValidPositiveInteger(String value) throws IllegalArgumentException {
    if (isValidString(value)) {
      int number = Integer.parseInt(value.trim());

      if (number > 0) {
        return number;
      }
    }

    throw new IllegalArgumentException("El número debe ser un entero positivo.");
  }

  public static String getValidName(String value, String name, int minLength, int maxLength) throws IllegalArgumentException {
    if (isValidName(value, minLength, maxLength)) {
      return value.trim();
    }

    throw new IllegalArgumentException(
      name + " debe ser una cadena de texto entre " + minLength + " y " + maxLength + " carácteres."
    );
  }

  private static String getValidString(String value, String name) throws IllegalArgumentException {
    if (isValidString(value, 3, 128)) {
      return value.trim();
    }

    throw new IllegalArgumentException(name + " no puede ser nulo o vacío.");
  }

  public static String getValidText(String value, String name) throws IllegalArgumentException {
    if (isValidString(value, 3, 512)) {
      return value.trim();
    }

    throw new IllegalArgumentException(name + " no puede ser nulo o vacío.");
  }
}
