package org.example.mediavaultbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.Year;
import java.util.List;

@Data
@Entity
@Table(name = "media")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    @Length(max = 500)
    private String title;

    @Column(name = "is_adult")
    private boolean isAdult;

    @Column(name = "start_year")
    private Year startYear;

    @Column(name = "runtime_minutes")
    private Integer runtimeMinutes;

    @ElementCollection
    @CollectionTable(
            name = "media_genres",
            joinColumns = @JoinColumn(name = "media_id")
    )
    @Column(name = "genre")
    private List<String> genres;

    @Column(name = "amount")
    private int amount;

    @Column(name = "price")
    private double price;

    @OneToMany(mappedBy = "media")
    private List<CurrentlyLending> lendings;



}
