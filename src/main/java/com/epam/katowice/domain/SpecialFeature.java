package com.epam.katowice.domain;

public enum SpecialFeature {
    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");

    private String name;

    SpecialFeature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SpecialFeature fromName(String name) {
        for (SpecialFeature eachSpecialFeature : SpecialFeature.values()) {
            if (eachSpecialFeature.getName().equalsIgnoreCase(name)) {
                return eachSpecialFeature;
            }
        }
        throw new IllegalArgumentException("A SpecialFeature for provided name: " + name + " cannot be found");
    }

}
