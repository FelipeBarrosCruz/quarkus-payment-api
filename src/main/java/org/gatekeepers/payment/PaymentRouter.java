package org.gatekeepers.payment;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

@Path("/payments")
@RequestScoped
class PaymentRouter {
  
  @Inject
  PaymentService service;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response request_create_payment(@Valid PaymentSchema PaymentSchema) {
    this.service.create(PaymentSchema);
    return Response.status(Response.Status.CREATED).build();
  }
}
