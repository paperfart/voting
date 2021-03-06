package ru.homeproject.voting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id"),
        @NamedQuery(name = Restaurant.ALL_SORTED, query = "SELECT r FROM Restaurant r LEFT JOIN FETCH r.menu ORDER BY r.created, r.name"),
})
@Entity
@Table(name = "restaurants")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Restaurant extends AbstractNamedEntity {

    public static final String DELETE = "Restaurant.delete";
    public static final String ALL_SORTED = "Restaurant.getAll";

    @Column(name = "created", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime created;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    private List<Dish> menu;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, LocalDateTime created, List<Dish> menu) {
        super(id, name);
        this.created = created;
        this.menu = menu;
    }

    public Restaurant(Integer id, String name, LocalDateTime created, Dish... dishes) {
        super(id, name);
        this.created = created;
        this.menu = Arrays.asList(dishes);
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @JsonManagedReference
    public List<Dish> getMenu() {
        return menu;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", menu=" + menu +
                '}';
    }

}
