//package com.tiy;
//
//import javax.persistence.*;
//
///**
// * Created by jfabiano on 9/15/2016.
// */
//@Entity//the Game class is an entity connected to the games name value in the DB
//@Table(name = "games")
//public class Game {
//    @Id
//    @GeneratedValue
//    int id;//identity/serial
//
//    @ManyToOne//(optional== false) is a 1 to many relationship
//    User user;
//
//    @Column(nullable = false)
//    String name;
//
//    @Column(nullable = false)
//    String platform;
//
//    @Column(nullable = false)
//    String genre;
//
//    @Column(nullable = false)
//    int releaseYear;
//
//    public Game() {
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPlatform() {
//        return platform;
//    }
//
//    public void setPlatform(String platform) {
//        this.platform = platform;
//    }
//
//    public String getGenre() {
//        return genre;
//    }
//
//    public void setGenre(String genre) {
//        this.genre = genre;
//    }
//
//    public int getReleaseYear() {
//        return releaseYear;
//    }
//
//    public void setReleaseYear(int releaseYear) {
//        this.releaseYear = releaseYear;
//    }
//
//    public Game(String name, String platform, String genre, int releaseYear, User user) {
//        this.name = name;
//        this.platform = platform;
//        this.genre = genre;
//        this.releaseYear = releaseYear;
//        this.user = user;
//    }
//}
//
package com.tiy;

import javax.persistence.*;

/**
 * Created by jfabiano on 9/15/2016.
 */
@Entity//the Game class is an entity connected to the games name value in the DB
@Table(name = "todos")
public class Todo {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Id
    @GeneratedValue

    int id;//identity/serial

    @ManyToOne//(optional== false) is a 1 to many relationship
            User user;

    @Column(nullable = false)//was previously false
    String name;

    @Column(nullable = false)
    boolean isDone = false;

    public Todo() {
    }

    public Todo(String name, User user) {
        this.name = name;
        this.user = user;

    }

    public Todo(String name, User user, boolean isDone) {
        this.name = name;
        this.user = user;
        this.isDone = isDone;

    }
}

