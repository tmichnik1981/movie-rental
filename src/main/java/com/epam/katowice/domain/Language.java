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
@Table(name = "language")
public class Language implements Serializable {

    public static final String NAME = "name";
    public static final String ID = "id";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "language_id")
    private Long id;

    @Column(name = "name",
        nullable = false)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update",
        nullable = false)
    private Date timeStamp;

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

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Language language = (Language) o;

        return new EqualsBuilder()
            .append(id, language.id)
            .append(name, language.name)
            .append(timeStamp, language.timeStamp)
            .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 31)
            .append(id)
            .append(name)
            .append(timeStamp)
            .toHashCode();
    }
}
