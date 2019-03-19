package WebApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User extends  AbstractEntity  {

    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "verify",nullable = false)
    private Boolean verify;

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

    public User(String email, String password, Boolean verify, List<Organization> organization, List<Reservation> reservations, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.verify = verify;
        this.organization = organization;
        this.reservations = reservations;
        this.roles = roles;
    }

    public User(String email, String password, Boolean verify, List<Organization> organization, List<Reservation> reservations) {
        this.email = email;
        this.password = password;
        this.verify = verify;
        this.organization = organization;
        this.reservations = reservations;
        this.roles = Collections.singleton(Role.USER);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getVerify() {
        return verify;
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
}
