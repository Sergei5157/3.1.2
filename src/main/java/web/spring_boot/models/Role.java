package web.spring_boot.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "role")
    private String name;
    @Transient
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }


    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }
}
