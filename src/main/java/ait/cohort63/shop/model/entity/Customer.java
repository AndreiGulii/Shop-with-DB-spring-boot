package ait.cohort63.shop.model.entity;

import java.util.Objects;

public class Customer {
    private Long id;
    private String Name;
    private boolean Active;

    public Customer() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public boolean isActive() {
        return Active;
    }

    @Override
    public String toString() {
        return String.format("Customer: id - %d, name - %s, active - %s" ,  id, Name, Active ? "Yes" : "No");
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        return Active == customer.Active && Objects.equals(id, customer.id) && Objects.equals(Name, customer.Name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(Name);
        result = 31 * result + Boolean.hashCode(Active);
        return result;
    }
}
