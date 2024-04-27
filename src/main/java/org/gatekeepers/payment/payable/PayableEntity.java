package org.gatekeepers.payment.payable;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.gatekeepers.payment.enums.PayableStatus;

import lombok.Getter;
import lombok.Setter;


public class PayableEntity {
  private final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault());
  private final HashMap<PayableStatus, String> STATUS_MAP_TRANSFORMER = new HashMap<PayableStatus, String>() {{
    put(PayableStatus.PAID, "paid");
    put(PayableStatus.WAITING_FUNDS, "waiting_funds");
  }};

  @Setter
  private PayableStatus status;
  
  @Getter
  private String createDate;
  
  @Getter
  private String subtotal;
  
  @Getter
  private String discount;
  
  @Getter
  private String total;


  public String getStatus() {
    return STATUS_MAP_TRANSFORMER.get(this.status);
  }

  public PayableEntity setCreateDate(Instant createDate) {
    this.createDate = DATE_PATTERN.format(createDate);
    return this;
  }

  public PayableEntity setSubTotal(BigDecimal subtotal) {
    this.subtotal = this.formatCurrency(subtotal);
    return this;
  }

  public PayableEntity setDiscount(BigDecimal discount) {
    this.discount = this.formatCurrency(discount);
    return this;
  }

  public PayableEntity setTotal(BigDecimal total) {
    this.total = this.formatCurrency(total);
    return this;
  }
  
  private String formatCurrency(BigDecimal value) {
    return String.format("%.2f", value);
  }
}
