package WebApp.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User extends  AbstractEntity {

    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = true, length = 50)
    private String password;

    @Column(name = "verify",nullable = false)
    private Boolean verify;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Organization> organization;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User() {
    }

    public User(String email, String password, Boolean verify, List<Organization> organization, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.verify = verify;
        this.organization = organization;
        this.roles = roles;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.verify = false;
        this.organization = null;
        this.roles = Collections.singleton(Role.USER);;
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

    public Set<Role> getRoles() {
        return roles;
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

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
