package org.example.mediavaultbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "message")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messasgeId;

    @ManyToOne
    @JoinColumn(name = "account1_id", referencedColumnName = "account_id")
    private Account account1;

    @ManyToOne
    @JoinColumn(name = "account2_id", referencedColumnName = "account_id")
    private Account account2;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private LocalDate date;

}
