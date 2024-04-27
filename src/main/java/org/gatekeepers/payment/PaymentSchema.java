package org.gatekeepers.payment;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.gatekeepers.payment.enums.PaymentMethod;

class PaymentSchema {
  
  @NotBlank
  @Getter
  private String value; 

  @NotBlank
  @Getter
  private String description;

  @NotBlank
  @Pattern(regexp = "^(credit_card|debit_card)$")
  private String paymentMethod;

  @NotBlank
  @Length(min = 16, max = 16)
  private String cardNumber;

  @NotBlank
  @Getter
  private String cardHolderName;

  @NotBlank
  @Getter
  @Pattern(regexp = "^(0[1-9]|1[0-2])\\/?([0-9]{2})$")
  private String cardExpirationDate;
  
  @NotBlank
  @Getter
  @Length(min = 3, max = 3)
  private String cardCvv;

  public PaymentMethod getPaymentMethod() {
    if (this.paymentMethod.equals("credit_card")) {
      return PaymentMethod.CREDIT_CARD;
    }

    return PaymentMethod.DEBIT_CARD;
  }

  public String getCardNumber() {
    return this.cardNumber.substring(Math.max(0, this.cardNumber.length() - 4));
  }
}
