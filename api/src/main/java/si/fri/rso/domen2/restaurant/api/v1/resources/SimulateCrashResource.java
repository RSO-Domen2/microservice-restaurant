package si.fri.rso.domen2.restaurant.api.v1.resources;

import org.jboss.logging.Logger;
import si.fri.rso.domen2.restaurant.config.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/demo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SimulateCrashResource {

    private Logger log = Logger.getLogger(SimulateCrashResource.class.getName());

    @Inject
    private RestProperties restProperties;

    @POST
    @Path("break")
    public Response makeUnhealthy() {

        restProperties.setBroken(true);

        return Response.status(Response.Status.OK).build();
    }
}