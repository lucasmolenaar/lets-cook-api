package nl.lucas.letscookapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column
    private String apiKey;

    @Column(nullable = false)
    private String email;

    @OneToMany(targetEntity = Authority.class, mappedBy = "username", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<Recipe> ownedRecipes;

//    @OneToMany(mappedBy = "owner")
//    @JsonManagedReference
//    private List<Comment> ownedComments;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }

    public void addOwnedRecipe(Recipe recipe) {
        ownedRecipes.add(recipe);
    }

    public List<Recipe> getOwnedRecipes() {
        return ownedRecipes;
    }

    public void setOwnedRecipes(List<Recipe> ownedRecipes) {
        this.ownedRecipes = ownedRecipes;
    }

//    public List<Comment> getOwnedComments() {
//        return ownedComments;
//    }
//
//    public void setOwnedComments(List<Comment> ownedComments) {
//        this.ownedComments = ownedComments;
//    }
}
