package si.fri.rso.domen2.restaurant.api.v1.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.domen2.restaurant.lib.MenuMetadata;
import si.fri.rso.domen2.restaurant.lib.RestaurantMetadata;
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
import java.util.logging.Logger;

@Path("menu")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class MenusResource {
    private Logger log = Logger.getLogger(MenusResource.class.getName());
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
        log.info("GET "+uriInfo.getRequestUri().toString());
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<MenuEntity> menus = menuBean.getAllMenus(query);
        return Response.status(Response.Status.OK).entity(menus).build();
    }


    @GET
    @Path("/{id}")
    public Response getMenuEntity(@Parameter(description = "Menu ID.", required = true)
                                      @PathParam("id") Integer menu_id) {
        log.info("GET "+uriInfo.getRequestUri().toString());
        MenuEntity menuEntity = menuBean.getMenu(menu_id);

        if (menuEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(menuEntity).build();
    }



    @DELETE
    @Path("{id}")
    @Operation(summary = "Deleting menu", description = "Menu can be deleted by providing ID.")
    @APIResponses({
            @APIResponse(description = "Successfully deleted menu.", responseCode = "200"),
            @APIResponse(description = "Menu ID does not exist.", responseCode = "404")
    })
    public Response deleteMenu(@Parameter(description = "Menu ID we want to delete.", required = true) @PathParam("id") int id) {
        log.info("DELETE "+uriInfo.getRequestUri().toString());
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
    public Response updateMenu(@Parameter(description = "Menu ID we want to update.", required = true) @PathParam("id") int id, MenuEntity menuEntity) {
        log.info("PUT "+uriInfo.getRequestUri().toString());
        if(menuBean.updateMenu(id, menuEntity)) {
            return Response.status(Response.Status.ACCEPTED).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }





}
