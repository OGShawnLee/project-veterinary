package com.veterinary.business;

public class Validator {
  private static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

  public static Result<String> getFilledString(String value, String message, int maxLength) {
    if (value == null || value.trim().isEmpty() || value.trim().length() > maxLength) {
      return Result.createFailureResult(message);
    }

    return Result.createSuccessResult(value);
  }

  public static Result<String> getEmail(String value, String message, int maxLength) {
    Result<String> emailResult = getFilledString(value, message, maxLength);

    if (emailResult.isFailure()) {
      return emailResult;
    }

    if (value.matches(EMAIL_REGEX)) {
      return Result.createSuccessResult(value);
    }

    return Result.createFailureResult(message);
  }

  public static Result<Integer> getPositiveInteger(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      return Result.createFailureResult(message);
    }

    try {
      int number = Integer.parseInt(value);

      if (number <= 0) {
        return Result.createFailureResult(message);
      }

      return Result.createSuccessResult(number);
    } catch (NumberFormatException e) {
      return Result.createFailureResult(message);
    }
  }
}
