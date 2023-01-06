package si.fri.rso.domen2.restaurant.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
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
import si.fri.rso.domen2.restaurant.services.beans.RestaurantMetadataBean;

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



@ApplicationScoped
@Path("/restaurant")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantMetadataResource {

    private Logger log = Logger.getLogger(RestaurantMetadataResource.class.getName());

    @Inject
    private RestaurantMetadataBean restaurantMetadataBean;

    @Inject
    private MenuBean menuBean;

    @Inject
    private ManagingMenusBean managingMenusBean;


    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all Restaurant metadata.", summary = "Get all metadata.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of Restaurant metadata.",
                    content = @Content(schema = @Schema(implementation = RestaurantMetadata.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getRestaurantMetadata() {

        List<RestaurantMetadata> restaurantMetadata = restaurantMetadataBean.getRestaurantMetadata();
        log.info("GET "+uriInfo.getRequestUri().toString());

        return Response.status(Response.Status.OK).entity(restaurantMetadata).build();
    }


    @Operation(description = "Get metadata for restaurant.", summary = "Get metadata for restaurant.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Restaurant metadata",
                    content = @Content(
                            schema = @Schema(implementation = RestaurantMetadata.class))
            )})
    @GET
    @Path("/{restaurantMetadataId}")
    public Response getClientMetadata(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("restaurantMetadataId") Integer restaurantMetadataId) {
        log.info("GET "+uriInfo.getRequestUri().toString());
        RestaurantMetadata restaurantMetadata = restaurantMetadataBean.getRestaurantMetadata(restaurantMetadataId);

        if (restaurantMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(restaurantMetadata).build();
    }

    @Operation(description = "Add restaurant metadata.", summary = "Add metadata")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Metadata successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
    })
    @POST
    public Response createRestaurantMetadata(@RequestBody(
            description = "DTO object with restaurant metadata.",
            required = true, content = @Content(
            schema = @Schema(implementation = RestaurantMetadata.class))) RestaurantMetadata restaurantMetadata) {
        log.info("POST "+uriInfo.getRequestUri().toString());
        restaurantMetadata = restaurantMetadataBean.createRestaurantMetadata(restaurantMetadata);

        return Response.status(Response.Status.CONFLICT).entity(restaurantMetadata).build();

    }


    @Operation(description = "Update metadata for restaurant.", summary = "Update metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully updated."
            )
    })
    @PUT
    @Path("{restaurantMetadataId}")
    public Response putClientMetadata(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("restaurantMetadataId") Integer restaurantMetadataId,
                                     @RequestBody(
                                             description = "DTO object with restaurant metadata.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = RestaurantMetadata.class)))
                                             RestaurantMetadata restaurantMetadata){
        log.info("PUT "+uriInfo.getRequestUri().toString());
        restaurantMetadata = restaurantMetadataBean.putRestaurantMetadata(restaurantMetadataId, restaurantMetadata);

        if (restaurantMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @Operation(description = "Delete metadata for restaurant.", summary = "Delete metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{restaurantMetadataId}")
    public Response deleteClientMetadata(@Parameter(description = "Metadata ID.", required = true)
                                        @PathParam("restaurantMetadataId") Integer restaurantMetadataId){
        log.info("DELETE "+uriInfo.getRequestUri().toString());
        boolean deleted = restaurantMetadataBean.deleteRestaurantMetadata(restaurantMetadataId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Operation(summary = "List of restaurant menus.", description = "Returns list of restaurant menus.")
    @APIResponses({
            @APIResponse(description = "Menus list", responseCode = "200", content = @Content(schema = @Schema(implementation = MenuEntity.class)))
    })
    @Path("{id}/menus")
    public Response getMenusForRestaurant(@PathParam("id") int id) {
        log.info("GET "+uriInfo.getRequestUri().toString());
        List<MenuEntity> menus = restaurantMetadataBean.getRestaurantEntity(id).getMenus();
        return Response.ok(menus).build();
    }

    @POST
    @Path("{id}/menus")
    @Operation(summary = "Adding menu to the restaurant.", description = "Menu with ID is added new menu.")
    @APIResponses({
            @APIResponse(description = "Menu created.", responseCode = "201"),
            @APIResponse(description = "Restaurant ID does not exist, can't create new menu.", responseCode = "404")
    })
    public Response createMenuForRestaurant(@Parameter(description = "Restaurant ID for which we want to create new menu.", required = true) @PathParam("id") int id,
                                            @RequestBody(description = "DTO object for creating menu.", required = true, content = @Content(
                                                    schema = @Schema(implementation = MenuMetadata.class)
                                            )) MenuMetadata menuDto) {
        log.info("POST "+uriInfo.getRequestUri().toString());
        menuDto.setRestaurantId(id);
        if(managingMenusBean.createMenu(menuDto)) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }





}
