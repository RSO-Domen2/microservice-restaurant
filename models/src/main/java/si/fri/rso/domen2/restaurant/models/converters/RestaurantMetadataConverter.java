package si.fri.rso.domen2.restaurant.models.converters;

import si.fri.rso.domen2.restaurant.lib.RestaurantMetadata;
import si.fri.rso.domen2.restaurant.models.entities.RestaurantMetadataEntity;

public class RestaurantMetadataConverter {

    public static RestaurantMetadata toDto(RestaurantMetadataEntity entity) {

        RestaurantMetadata dto = new RestaurantMetadata();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLat(entity.getLat());
        dto.setLng(entity.getLng());
        dto.setStreetNumber(entity.getStreetNumber());
        dto.setStreetName(entity.getStreetName());
        dto.setPostalCode(entity.getPostalCode());
        dto.setCountry(entity.getCountry());
        dto.setCreated(entity.getCreated());
        dto.setCity(entity.getCity());

        return dto;

    }

    public static RestaurantMetadataEntity toEntity(RestaurantMetadata dto) {

        RestaurantMetadataEntity entity = new RestaurantMetadataEntity();
        entity.setLat(dto.getLat());
        entity.setName(dto.getName());
        entity.setLng(dto.getLng());
        entity.setStreetNumber(dto.getStreetNumber());
        entity.setStreetName(dto.getStreetName());
        entity.setPostalCode(dto.getPostalCode());
        entity.setCountry(dto.getCountry());
        entity.setCreated(dto.getCreated());
        entity.setCity(dto.getCity());

        return entity;

    }

}
