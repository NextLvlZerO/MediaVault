package org.example.mediavaultbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
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

    @Column(name = "title")
    @Length(max = 500)
    private String title;

    @Column(name = "description")
    @Length(max = 1000)
    private String description;

    @Column(name = "is_adult")
    private boolean isAdult;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ElementCollection
    @CollectionTable(
            name = "media_genres",
            joinColumns = @JoinColumn(name = "media_id")
    )
    @Column(name = "genre")
    private List<String> genres;

    @Column(name = "poster")
    private String poster;

    @Column(name = "amount")
    private int amount;

    @Column(name = "price")
    private double price;

    @Column(name = "averageRating")
    @Min(1)
    @Max(5)
    private double averageRating;

    @OneToMany(mappedBy = "media")
    private List<CurrentlyLending> lendings;



}
