package com.infotamia.pojos.entities;


import javax.persistence.*;
import java.util.Objects;

/**
 * @author Mohammed Al-Ani
 */
@Entity
@Table(name = "language")
public class LanguageEntity {
    private Integer id;
    private String languageCode;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "language_code", nullable = false)
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguageEntity that = (LanguageEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(languageCode, that.languageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, languageCode);
    }
}
