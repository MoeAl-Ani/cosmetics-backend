package com.infotamia.pojos.entities;

import com.infotamia.pojos.enums.Permission;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Mohammed Al-Ani
 */
@Entity
@Table(name = "permission")
public class PermissionEntity {
    private Integer id;
    private Permission name;

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
    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    public Permission getName() {
        return name;
    }

    @Enumerated(EnumType.STRING)
    public void setName(Permission name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionEntity that = (PermissionEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
