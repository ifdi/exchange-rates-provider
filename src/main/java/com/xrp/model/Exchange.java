package com.xrp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "exchange")
@NoArgsConstructor
public class Exchange {

    @Id
    @GeneratedValue
    private long id;

    private LocalDateTime timestamp;

    private LocalDate date;

    private String currency;

    private double rate;

    public Exchange(LocalDateTime timestamp, LocalDate date, String currency, double rate) {
        this.timestamp = timestamp;
        this.date = date;
        this.currency = currency;
        this.rate = rate;
    }
}
