package si.fri.rso.domen2.restaurant.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.jboss.logging.Logger;
import si.fri.rso.domen2.restaurant.lib.MenuMetadata;
import si.fri.rso.domen2.restaurant.models.converters.RestaurantMetadataConverter;
import si.fri.rso.domen2.restaurant.models.entities.MenuEntity;
import si.fri.rso.domen2.restaurant.models.entities.RestaurantMetadataEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped

public class MenuBean {
    private Logger log = Logger.getLogger(MenuBean.class.getName());

    @Inject
    private EntityManager em;


    public List<MenuEntity> getAllMenus() {
        return em.createNamedQuery("Menu.getAll").getResultList();
    }

    public List<MenuEntity> getAllMenus(QueryParameters query) {
        List<MenuEntity> menus = JPAUtils.queryEntities(em, MenuEntity.class, query);
        return menus;
    }

    public boolean createMenu(MenuEntity menuEntity) {

        try {
            beginTx();
            em.persist(menuEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (menuEntity.getId() == null) {
            return false;
        }

        return true;
    }


    public MenuEntity getMenu(int menuId) {
        return em.find(MenuEntity.class, menuId);
    }

    @Transactional
    public boolean deleteMenu(int id) {
        MenuEntity m = getMenu(id);
        if(m != null) {
            em.remove(m);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateMenu(int id, MenuEntity m) {
        MenuEntity update = getMenu(id);
        if(update != null) {
            update.setName(m.getName());
            update.setPrice(m.getPrice());
            return true;
        }
        return false;
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
