package WebApp.entity;


import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "login", nullable = false, length = 50)
    private String login;

    @Column(name = "password", nullable = true, length = 50)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "verify",nullable = false)
    private Boolean verify;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    public User() {
    }

    public User(String login, String password, String email, Boolean verify, Set<Role> roles) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.verify = verify;
        this.roles = roles;
    }

    public User(String login, String password, String email, Set<Role> roles) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.verify = false;
        this.roles = roles;
    }

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.verify = false;
        this.roles = Collections.singleton(Role.USER);
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getVerify() {
        return verify;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
