package ru.clevertec.house.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import ru.clevertec.house.constant.Sex;
import ru.clevertec.house.listener.PersonListener;
import ru.clevertec.house.model.entity.parent.LogModel;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EntityListeners(PersonListener.class)
public class Person extends LogModel {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Embedded
    private Passport passport;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "house_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_home_resident_id_to_id")
    )
    private House home;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "house_owner",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "house_id"),
            foreignKey = @ForeignKey(name = "fk_owner_house_id_to_id")
    )
    private List<House> houses;
}
