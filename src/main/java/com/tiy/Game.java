package com.tiy;

import javax.persistence.*;

/**
 * Created by jfabiano on 9/15/2016.
 */
@Entity//the Game class is an entity connected to the games name value in the DB
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue
    int id;//identity/serial

    @ManyToOne//(optional== false) is a 1 to many relationship
    User user;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String platform;

    @Column(nullable = false)
    String genre;

    @Column(nullable = false)
    int releaseYear;

    public Game() {
    }

    public Game(String name, String platform, String genre, int releaseYear, User user) {
        this.name = name;
        this.platform = platform;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.user = user;
    }
}

