package si.fri.rso.domen2.restaurant.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import si.fri.rso.domen2.restaurant.lib.RestaurantMetadata;
import si.fri.rso.domen2.restaurant.models.converters.RestaurantMetadataConverter;
import si.fri.rso.domen2.restaurant.models.entities.RestaurantMetadataEntity;


@RequestScoped
public class RestaurantMetadataBean {

    private Logger log = Logger.getLogger(RestaurantMetadataBean.class.getName());

    @Inject
    private EntityManager em;

    public List<RestaurantMetadata> getRestaurantMetadata() {

        TypedQuery<RestaurantMetadataEntity> query = em.createNamedQuery(
                "RestaurantMetadataEntity.getAll", RestaurantMetadataEntity.class);

        List<RestaurantMetadataEntity> resultList = query.getResultList();

        return resultList.stream().map(RestaurantMetadataConverter::toDto).collect(Collectors.toList());

    }

    public List<RestaurantMetadata> getRestaurantMetadataFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, RestaurantMetadataEntity.class, queryParameters).stream()
                .map(RestaurantMetadataConverter::toDto).collect(Collectors.toList());
    }

    public RestaurantMetadata getRestaurantMetadata(Integer id) {

        RestaurantMetadataEntity restaurantMetadataEntity = em.find(RestaurantMetadataEntity.class, id);

        if (restaurantMetadataEntity == null) {
            throw new NotFoundException();
        }

        RestaurantMetadata restaurantMetadata = RestaurantMetadataConverter.toDto(restaurantMetadataEntity);

        return restaurantMetadata;
    }

    public RestaurantMetadataEntity getRestaurantEntity(int id) {
        return em.find(RestaurantMetadataEntity.class, id);
    }

    public RestaurantMetadata createRestaurantMetadata(RestaurantMetadata restaurantMetadata) {

        RestaurantMetadataEntity restaurantMetadataEntity = RestaurantMetadataConverter.toEntity(restaurantMetadata);

        try {
            beginTx();
            em.persist(restaurantMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (restaurantMetadataEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return RestaurantMetadataConverter.toDto(restaurantMetadataEntity);
    }

    public RestaurantMetadata putRestaurantMetadata(Integer id, RestaurantMetadata restaurantMetadata) {

        RestaurantMetadataEntity c = em.find(RestaurantMetadataEntity.class, id);

        if (c == null) {
            return null;
        }

        RestaurantMetadataEntity updatedRestaurantMetadataEntity = RestaurantMetadataConverter.toEntity(restaurantMetadata);

        try {
            beginTx();
            updatedRestaurantMetadataEntity.setId(c.getId());
            updatedRestaurantMetadataEntity = em.merge(updatedRestaurantMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return RestaurantMetadataConverter.toDto(updatedRestaurantMetadataEntity);
    }

    public boolean deleteRestaurantMetadata(Integer id) {

        RestaurantMetadataEntity restaurantMetadata = em.find(RestaurantMetadataEntity.class, id);

        if (restaurantMetadata != null) {
            try {
                beginTx();
                em.remove(restaurantMetadata);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
