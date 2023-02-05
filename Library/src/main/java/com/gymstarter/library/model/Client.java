package com.gymstarter.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "image", "phone_number"}))
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @Size(min = 5, max = 15, message = "First name should have 5-15 characters")
    private String firstName;
    @Size(min = 5, max = 15, message = "Last name should have 5-15 characters")
    private String lastName;
    private String username;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;

    private String password;
    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private String image;

    @Column(name = "city")
    private String city;

    private String country;

    @OneToOne(mappedBy = "client")
    private ShoppingCart shoppingCart;
    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "clients_roles",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;
}
