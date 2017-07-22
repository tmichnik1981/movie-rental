package com.epam.katowice.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "actor")
public class Actor implements Serializable {

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "actor_id")
    private Long id;

    @Column(name = "first_name",
        nullable = false)
    private String firstName;

    @Column(name = "last_name",
        nullable = false)
    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update",
        nullable = false)
    private Date lastUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Actor actor = (Actor) o;

        return new EqualsBuilder()
            .append(id, actor.id)
            .append(firstName, actor.firstName)
            .append(lastName, actor.lastName)
            .append(lastUpdate, actor.lastUpdate)
            .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 31)
            .append(id)
            .append(firstName)
            .append(lastName)
            .append(lastUpdate)
            .toHashCode();
    }
}
