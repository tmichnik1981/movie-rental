package com.epam.katowice.dto;


import java.util.Set;

public class DetailedFilmTo extends FilmTo {

    private Set<CategoryTo> categories;
    private LanguageTo language;
    private Set<ActorTo> actors;
    private FilmDescriptionTo filmDescription;

    public Set<CategoryTo> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryTo> categories) {
        this.categories = categories;
    }

    public LanguageTo getLanguage() {
        return language;
    }

    public void setLanguage(LanguageTo language) {
        this.language = language;
    }

    public Set<ActorTo> getActors() {
        return actors;
    }

    public void setActors(Set<ActorTo> actors) {
        this.actors = actors;
    }

    public FilmDescriptionTo getFilmDescription() {
        return filmDescription;
    }

    public void setFilmDescription(FilmDescriptionTo filmDescription) {
        this.filmDescription = filmDescription;
    }
}
