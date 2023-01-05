package si.fri.rso.domen2.restaurant.lib;

public class MenuMetadata {

    private Integer restaurant_id;
    private String name;
    private double price;

    public Integer getRestaurantId() { return restaurant_id; }
    public void setRestaurantId(Integer restaurant_id) { this.restaurant_id = restaurant_id; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
