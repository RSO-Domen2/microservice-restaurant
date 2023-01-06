package si.fri.rso.domen2.restaurant.api.v1.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.domen2.restaurant.models.entities.MenuEntity;
import si.fri.rso.domen2.restaurant.services.beans.ManagingMenusBean;
import si.fri.rso.domen2.restaurant.services.beans.MenuBean;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("menu")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class MenusResource {

    @Inject
    private ManagingMenusBean managingMenusBean;

    @Inject
    private MenuBean menuBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    @Operation(summary = "List of all menus.", description = "Return details of all menus.")
    @APIResponses({
            @APIResponse(description = "List of menus.", responseCode = "200", content = @Content(schema = @Schema(implementation =
                    MenuEntity.class)))
    })
    public Response getMenus() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<MenuEntity> menus = menuBean.getAllMenus(query);
        return Response.status(Response.Status.OK).entity(menus).build();
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Deleting menu", description = "Menu can be deleted by providing ID.")
    @APIResponses({
            @APIResponse(description = "Successfully deleted menu.", responseCode = "200"),
            @APIResponse(description = "Menu ID does not exist.", responseCode = "404")
    })
    public Response deleteMenu(@Parameter(description = "Menu ID we want to delete.", required = true) @PathParam("id") int id) {
        if(menuBean.deleteMenu(id)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Update menu", description = "Menu can be updated by providing ID.")
    @APIResponses({
            @APIResponse(description = "Successfully updated menu.", responseCode = "202"),
            @APIResponse(description = "Menu ID does not exist", responseCode = "404")
    })
    public Response updateMenu(@Parameter(description = "Menu ID we want to update.", required = true) @PathParam("id") int id, MenuEntity menuEntity) { ;
        if(menuBean.updateMenu(id, menuEntity)) {
            return Response.status(Response.Status.ACCEPTED).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }





}
