package com.epam.katowice.domain;

public enum Rating {
    G("G"), PG("PG"), PG13("PG-13"), R("R"), NC17("NC-17");

    private String ratingCode;

    Rating(String ratingCode) {
        this.ratingCode = ratingCode;
    }

    public String getRatingCode() {
        return ratingCode;
    }

    public static Rating fromCode(String code) {
        for (Rating eachRating : Rating.values()) {
            if (eachRating.getRatingCode().equalsIgnoreCase(code)) {
                return eachRating;
            }
        }
        throw new IllegalArgumentException("A Rating for provided code: " + code + " cannot be found");
    }
}
