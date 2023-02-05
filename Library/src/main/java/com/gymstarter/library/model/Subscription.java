package com.gymstarter.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "cost"})})
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long id;
    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String cost;
    private boolean is_deleted;
    private boolean is_activated;

    public Subscription(String name, String cost) {
        this.name = name;
        this.cost = cost;
        this.is_activated = true;
        this.is_deleted = false;
    }

}
