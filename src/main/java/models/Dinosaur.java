package models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "dinosaurs")
public class Dinosaur {
    private String species;
    private UUID id;
    private Paddock paddock;
    private int hunger;

    public Dinosaur(){};

    public Dinosaur(String species, Paddock paddock) {
        this.species = species;
        this.paddock = paddock;
        this.hunger = 50;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Column(name = "species")
    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    @ManyToOne(targetEntity = Paddock.class)
    public Paddock getPaddock() {
        return paddock;
    }

    public void setPaddock(Paddock paddock) {
        this.paddock = paddock;
    }

    @Column(name = "hunger")
    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void feed() {
        if(this.hunger + 10 <= 100) this.hunger += 10;
        else this.hunger = 100;
    }
}
