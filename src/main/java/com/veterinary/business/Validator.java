package com.veterinary.business;

public class Validator {
  private static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
  private static final String NAME_REGEX_SPANISH = "^[A-Za-zÑñÁáÉéÍíÓóÚúÜü\\s]+$";
  private static final String FLEXIBLE_NAME_REGEX = "^[A-Za-zÑñÁáÉéÍíÓóÚúÜü0-9\\s\\-_/.:]+$";
  private static final String PHONE_NUMBER_REGEX = "^[0-9]{10,16}$";
  private static final String DISPLAY_NAME_REGEX = "^[A-Za-z0-9\\-_.]{3,32}$";

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

  public static String getValidDisplayName(String value) throws IllegalArgumentException {
    if (isValidString(value) && value.trim().matches(DISPLAY_NAME_REGEX)) {
      return value.trim();
    }

    throw new IllegalArgumentException("Nombre de usuario debe ser una cadena de texto con el formato correcto.");
  }

  public static String getValidPhoneNumber(String value) throws IllegalArgumentException {
    if (isValidString(value) && value.trim().matches(PHONE_NUMBER_REGEX)) {
      return value.trim();
    }

    throw new IllegalArgumentException("Número de teléfono debe ser una cadena de texto con el formato correcto.");
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

  public static String getValidString(String value, String name, int minLength, int maxLength) throws IllegalArgumentException {
    if (isValidString(value, minLength, maxLength)) {
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
