package com.tiy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by jfabiano on 9/15/2016.
 */
public interface GameRepository extends CrudRepository<Game, Integer> {
    List<Game> findByGenre(String genre);//a special method name//if correct like this you dont have to implement this. it will do set string genre, number(int) used to search by genre in the db//this is good for regular crud stuff
    List<Game> findByReleaseYear(int year);//name of param dont matter, name of method is important here: in game, in release_year column(I think)

    // allow to search by user
    List<Game> findByUser(User user);

    @Query("SELECT g FROM Game g WHERE g.name LIKE ?1%")//we are not selecting from the table, but from the entity(Game and noot Games(table))(also name is the java name rathr than the sql deal)
    List<Game> findByNameStartsWith(String name);
}
