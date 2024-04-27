package org.gatekeepers.payment.RestClient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.gatekeepers.payment.payable.PayableEntity;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/payables")
@RegisterRestClient(configKey="rest-client")
public interface RestPayableClient {
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  String requestToCreateAPayable(PayableEntity payableEntity);
}