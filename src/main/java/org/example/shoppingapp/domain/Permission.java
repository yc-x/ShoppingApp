package org.example.shoppingapp.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="permission")
public class Permission {
    @Column(name="permission_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name="p_value")
    private String value;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
