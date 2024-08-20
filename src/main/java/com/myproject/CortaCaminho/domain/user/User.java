package com.myproject.CortaCaminho.domain.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myproject.CortaCaminho.domain.Profile.Profile;
import com.myproject.CortaCaminho.domain.Url.Url;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String login;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Url> urls;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Profile profile;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return login;  // Use the actual login
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Adjust as needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Adjust as needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Adjust as needed
    }

    @Override
    public boolean isEnabled() {
        return true;  // Adjust as needed
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "User{" +
                "profile=" + profile.toString() +
                ", login='" + login + '\'' +
                ", id=" + id +
                '}';
    }
}
