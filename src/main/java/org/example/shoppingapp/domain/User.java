package org.example.shoppingapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user_table")
public class User {
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String email;
    @Column(name="u_password")
    private String password;
    @Column
    private Integer role;
    @Column
    private String username;

    @JsonIgnore
    @OneToMany(mappedBy = "user", /*fetch = FetchType.EAGER,*/ cascade = CascadeType.ALL)
    private Set<Permission> permissions;

    @JsonIgnore
    @OneToMany(mappedBy = "user", /*fetch = FetchType.EAGER,*/ cascade = CascadeType.ALL)
    private List<Order> orders;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="watchlist",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="product_id")} )
    private Set<Product> products; // = new HashSet<Product>();

}
