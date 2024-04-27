package org.gatekeepers.payment;


import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.gatekeepers.payment.RestClient.RestTransactionClient;
import org.gatekeepers.payment.RestClient.RestPayableClient;
import org.gatekeepers.payment.enums.PayableStatus;
import org.gatekeepers.payment.enums.PaymentMethod;
import org.gatekeepers.payment.payable.PayableEntity;
import org.gatekeepers.payment.transaction.TransactionEntity;

import jakarta.inject.Inject;
import lombok.Getter;

class PaymentMethodConfiguration {
  
  @Getter
  private PayableStatus status;
  
  @Getter
  private int daysToReceive;
  
  @Getter
  private double fee;

  public PaymentMethodConfiguration(
    PayableStatus status,
    int daysToReceive,
    double fee
  ) {
    this.status = status;
    this.daysToReceive = daysToReceive;
    this.fee = fee;
  }
}

@RegisterProvider(PaymentService.class)
public class PaymentService {

  private final HashMap<PaymentMethod, PaymentMethodConfiguration> PAYMENT_METHOD_CONFIGS = new HashMap<PaymentMethod, PaymentMethodConfiguration>() {{
    put(PaymentMethod.CREDIT_CARD, new PaymentMethodConfiguration(PayableStatus.WAITING_FUNDS, 30, 0.04));
    put(PaymentMethod.DEBIT_CARD, new PaymentMethodConfiguration(PayableStatus.PAID, 0, 0.02));
  }};

  @Inject
  @RestClient
  RestTransactionClient transactionClient;

  @Inject
  @RestClient
  RestPayableClient payableClient;


  public void create(PaymentSchema request) {
    saveTransaction(request).savePayable(request);
  }

  private PaymentService saveTransaction(PaymentSchema request) {
    var transaction = new TransactionEntity();
    
    transaction.setValue(request.getValue());
    transaction.setMethod(request.getPaymentMethod());
    transaction.setDescription(request.getDescription());
    transaction.setCardNumber(request.getCardNumber());
    transaction.setCardHolderName(request.getCardHolderName());
    transaction.setCardExpirationDate(request.getCardExpirationDate());
    transaction.setCardCvv(request.getCardCvv());
    
    transactionClient.requestToCreateATransaction(transaction);
    return this;
  }

  private void savePayable(PaymentSchema request) {
    var now = Instant.now();
    var payable = new PayableEntity();
    var paymentMethodConfig = PAYMENT_METHOD_CONFIGS.get(request.getPaymentMethod());
    
    var subtotal = new BigDecimal(request.getValue());
    var discount = subtotal.multiply(new BigDecimal(paymentMethodConfig.getFee()));
    var total = subtotal.subtract(discount);
    var createDate = now.plus(paymentMethodConfig.getDaysToReceive(), ChronoUnit.DAYS);

    payable.setStatus(paymentMethodConfig.getStatus());
    payable.setSubTotal(subtotal);
    payable.setDiscount(discount);
    payable.setTotal(total);
    payable.setCreateDate(createDate);
    
    payableClient.requestToCreateAPayable(payable);
  }
}
