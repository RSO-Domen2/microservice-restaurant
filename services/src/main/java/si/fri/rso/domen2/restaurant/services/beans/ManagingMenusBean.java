package si.fri.rso.domen2.restaurant.services.beans;

import si.fri.rso.domen2.restaurant.lib.MenuMetadata;
import si.fri.rso.domen2.restaurant.lib.RestaurantMetadata;
import si.fri.rso.domen2.restaurant.models.converters.RestaurantMetadataConverter;
import si.fri.rso.domen2.restaurant.models.entities.MenuEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class ManagingMenusBean {

    @Inject
    private MenuBean menuBean;

    @Inject
    private RestaurantMetadataBean restaurantMetadataBean;

    @Transactional
    public boolean createMenu(MenuMetadata menu) {
        // najprej pridobi id restravracije, za katero želim ustvariti nov meni
        RestaurantMetadata restaurantMetadata = restaurantMetadataBean.getRestaurantMetadata(menu.getRestaurantId());
        if(restaurantMetadata == null) {
            return false;
        }

        MenuEntity menuEntity = new MenuEntity();

        menuEntity.setName(menu.getName());
        menuEntity.setRestaurant(RestaurantMetadataConverter.toEntity(restaurantMetadata));

        return menuBean.createMenu(menuEntity);

    }

}
