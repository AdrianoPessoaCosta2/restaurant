package br.com.restaurants.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class MenuItemEntity {
    private Long id;
    private Long restaurantId;
    private RestaurantEntity restaurant;
    private UUID publicId;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean dineInOnly;
    private String photoPath;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdatedDate;


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getRestaurantId() { return restaurantId; }

    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

    public RestaurantEntity getRestaurant() { return restaurant; }

    public void setRestaurant(RestaurantEntity restaurant) { this.restaurant = restaurant; }

    public UUID getPublicId() { return publicId; }

    public void setPublicId(UUID publicId) { this.publicId = publicId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public Boolean getDineInOnly() { return dineInOnly; }

    public void setDineInOnly(Boolean dineInOnly) { this.dineInOnly = dineInOnly; }

    public String getPhotoPath() { return photoPath; }

    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public LocalDateTime getCreateDate() { return createDate; }

    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }

    public LocalDateTime getLastUpdatedDate() { return lastUpdatedDate; }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }
}