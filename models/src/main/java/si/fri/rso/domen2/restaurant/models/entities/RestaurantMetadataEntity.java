package si.fri.rso.domen2.restaurant.models.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "restaurant_metadata")
@NamedQueries(value =
        {
                @NamedQuery(name = "RestaurantMetadataEntity.getAll",
                        query = "SELECT res FROM RestaurantMetadataEntity res")
        })
public class RestaurantMetadataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "created")
    private Instant created;

    @Column(name = "street_number")
    private String street_number;

    @Column(name = "street_name")
    private String street_name;

    @Column(name = "postal_code")
    private Integer postal_code;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<MenuEntity> menus;


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getStreetNumber() { return street_number; }
    public void setStreetNumber(String street_number) {this.street_number = street_number;}

    public String getStreetName() { return street_name; }
    public void setStreetName(String street_name) {this.street_name = street_name;}

    public Integer getPostalCode() { return postal_code; }
    public void setPostalCode(Integer postal_code) {this.postal_code = postal_code;}

    public String getCountry() {return country;}
    public void setCountry(String country) {this.country = country;}

    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}

    public Instant getCreated() {
        return created;
    }
    public void setCreated(Instant created) {
        this.created = created;
    }

    public List<MenuEntity> getMenus() {
        return menus;
    }
    public void setMenus(List<MenuEntity> menus) {
        this.menus = menus;
    }

}