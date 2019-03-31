package WebApp.security.Details;

import WebApp.entity.Role;
import WebApp.entity.State;
import WebApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserDetailsImpl implements UserDetails {


    @Autowired
    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    private final Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        for (GrantedAuthority role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getStates().equals(Collections.singleton(State.BANNED));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStates().equals(Collections.singleton(State.ACTIVE));
    }

    public User getUser() {
        return user;
    }
}
