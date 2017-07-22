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
@Table(name = "category")
public class Category implements Serializable {

    public static final String ID = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id",
        nullable = false)
    private Long id;

    @Column(name = "name",
        nullable = false)
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        Category category = (Category) o;

        return new EqualsBuilder()
            .append(id, category.id)
            .append(name, category.name)
            .append(lastUpdate, category.lastUpdate)
            .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 31)
            .append(id)
            .append(name)
            .append(lastUpdate)
            .toHashCode();
    }
}
