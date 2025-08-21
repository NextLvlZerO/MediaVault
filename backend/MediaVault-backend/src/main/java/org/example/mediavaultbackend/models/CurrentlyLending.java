package org.example.mediavaultbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "currently_lending")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentlyLending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lending_id")
    private Long lendingID;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "media_id", referencedColumnName = "media_id")
    private Media media;

    @Column(name = "date")
    private Date date;

}
