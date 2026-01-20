package br.com.restaurants.core.entities;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class Restaurant {
    private Long id;
    private UUID publicId;
    private String name;
    private String cuisineType;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Address address;
    private User user;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdatedDate;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public UUID getPublicId() { return publicId; }

    public void setPublicId(UUID publicId) { this.publicId = publicId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCuisineType() { return cuisineType; }

    public void setCuisineType(String cuisineType) { this.cuisineType = cuisineType; }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public Address getAddress() { return address; }

    public void setAddress(Address address) { this.address = address; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreateDate() { return createDate; }

    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }

    public LocalDateTime getLastUpdatedDate() { return lastUpdatedDate; }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }
}