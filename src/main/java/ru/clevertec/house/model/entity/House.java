package ru.clevertec.house.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import ru.clevertec.house.model.entity.parent.UuidModel;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class House extends UuidModel {

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private int number;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "home", cascade = CascadeType.PERSIST)
    private List<Person> residents;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "house_owner",
            joinColumns = @JoinColumn(name = "house_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"),
            foreignKey = @ForeignKey(name = "fk_house_owner_id_to_id")
    )
    private List<Person> owners;
}
