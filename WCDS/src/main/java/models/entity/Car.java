package models.entity;

import models.entity.base.BaseEntity;

import javax.persistence.*;

@Entity
public class Car extends BaseEntity {

    private String brand;
    private String model;
    private String year;
    private EngineType engineType;
    private User user;

    @Column(nullable = false)
    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(nullable = false)
    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(nullable = false)
    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public EngineType getEngineType() {
        return this.engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
