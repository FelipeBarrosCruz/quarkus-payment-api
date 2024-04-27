package org.gatekeepers.payment.transaction;

import java.util.HashMap;

import org.gatekeepers.payment.enums.PaymentMethod;

import lombok.Getter;
import lombok.Setter;

public class TransactionEntity {
    private final HashMap<PaymentMethod, String> METHOD_MAP_TRANSFORMER = new HashMap<PaymentMethod, String>() {{
    put(PaymentMethod.CREDIT_CARD, "credit_card");
    put(PaymentMethod.DEBIT_CARD, "debit_card");
  }};

  @Getter
  @Setter
  private String value;

  @Getter
  @Setter
  private String description;
  
  @Getter
  private String method;

  @Getter
  @Setter
  private String cardNumber;
  
  @Getter
  @Setter
  private String cardHolderName;
  
  @Getter
  @Setter
  private String cardExpirationDate;
  
  @Getter
  @Setter
  private String cardCvv;

  public void setMethod(PaymentMethod method) {
    this.method = METHOD_MAP_TRANSFORMER.get(method);
  }
}
