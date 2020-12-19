package com.nibir.hossain.brewery.domain.security;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/*
 * Created by Nibir Hossain on 19.12.20
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    @ManyToMany(mappedBy = "authorities")
    private Set<CustomUser> users;
}
