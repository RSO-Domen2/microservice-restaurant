package si.fri.rso.domen2.restaurant.models.entities;

import si.fri.rso.domen2.restaurant.lib.RestaurantMetadata;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Entity(name = "menu")
@NamedQueries(value =
        {
                @NamedQuery(name = "Menu.getAll", query = "SELECT m FROM menu m")
        })

public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public String name;

    public Double price;

    @JsonbTransient
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantMetadataEntity restaurant;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
}
    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public RestaurantMetadataEntity getRestaurant() {
        return restaurant;
    }
    public void setRestaurant(RestaurantMetadataEntity restaurant) {
        this.restaurant = restaurant;
    }

}
