package WebApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User extends  AbstractEntity  {

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = true)
    private String password;

    @ElementCollection(targetClass = State.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_state",joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<State> states;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Organization> organization;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Reservation> reservations;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User()  {
    }

    public User(String email, String password, List<Organization> organization, List<Reservation> reservations) {
        this.email = email;
        this.password = password;
        this.states = Collections.singleton(State.ACTIVE);
        this.organization = organization;
        this.reservations = reservations;
        this.roles = Collections.singleton(Role.USER);
    }

    public User(User user) {
        this.email = user.email;
        this.password = user.password;
        this.states = user.states;
        this.organization = user.organization;
        this.reservations = user.reservations;
        this.roles = user.roles;
    }


    public User(String email, String password, Set<State> states, List<Organization> organization, List<Reservation> reservations, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.states = states;
        this.organization = organization;
        this.reservations = reservations;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public Set<State> getStates() {
        return states;
    }

    public List<Organization> getOrganization() {
        return organization;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public void setOrganization(List<Organization> organization) {
        this.organization = organization;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
