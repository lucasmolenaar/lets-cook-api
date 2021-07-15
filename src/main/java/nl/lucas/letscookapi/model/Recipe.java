package nl.lucas.letscookapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private int calories;

    @Column
    private int timeInMinutes;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Step> steps;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Equipment> equipment;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments;

    @Lob
    private byte[] recipeImage;

    @ManyToOne
    @JoinColumn(name = "owner")
    @JsonIgnoreProperties("recipes")
    @JsonBackReference
    private User owner;

    public Recipe() {}

    public Recipe(Long id, String name, int calories, int timeInMinutes) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.timeInMinutes = timeInMinutes;
    }

    public Recipe(String name, int calories, int timeInMinutes) {
        this.name = name;
        this.calories = calories;
        this.timeInMinutes = timeInMinutes;
    }

    public Recipe(String name, int calories, int timeInMinutes, List<Ingredient> ingredients, List<Step> steps, List<Equipment> equipment) {
        this.name = name;
        this.calories = calories;
        this.timeInMinutes = timeInMinutes;
        this.ingredients = ingredients;
        this.steps = steps;
        this.equipment = equipment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public byte[] getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(byte[] recipePicture) {
        this.recipeImage = recipePicture;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public static class Builder {
        private final String name;
        private final int calories;
        private final int timeInMinutes;
        private List<Ingredient> ingredients;
        private List<Step> steps;
        private List<Equipment> equipment;

        public Builder(String name, int calories, int timeInMinutes) {
            this.name = name;
            this.calories = calories;
            this.timeInMinutes = timeInMinutes;
        }

        public Builder withIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Builder withSteps(List<Step> steps) {
            this.steps = steps;
            return this;
        }

        public Builder withEquipment(List<Equipment> equipment) {
            this.equipment = equipment;
            return this;
        }

        public Recipe build() {
            return new Recipe(name, calories, timeInMinutes, ingredients, steps, equipment);
        }

    }
}
