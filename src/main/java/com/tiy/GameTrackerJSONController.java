package com.tiy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jfabiano on 9/19/2016.
 */
@RestController //this tells spring this controller is going to have end points that are going to take json, and return json specifically
public class GameTrackerJSONController{

    @Autowired//says take the interface, create a concrete implementation that can speak to this repository
            GameRepository games;//postgress db

    @RequestMapping(path = "/toggleGame.json", method = RequestMethod.GET)
    public ArrayList<Game> toggleGame(int gameID) {
        System.out.println("toggling game with ID " + gameID);
        Game game = games.findOne(gameID);
        game.name = "**" + game.name;
        games.save(game);

        return addGame();
    }

    @RequestMapping(path = "/addGame.json", method = RequestMethod.POST)
    public ArrayList<Game> addGame(HttpSession session, @RequestBody Game game) throws Exception {//this changes the object from json to java game object
        //seeeion is added in because the method can't be called without a user being logged in
        User user = (User)session.getAttribute("user");

        if (user == null) {
            throw new Exception("Unable to add game without an active user in the session");
        }
        game.user = user;

        games.save(game);

        return addGame();
    }



    @RequestMapping(path = "/games.json", method = RequestMethod.GET)//this can be any url... not just .json
    public ArrayList<Game> addGame() {
//        return new Game(name, platform, genre, releaseYear, user);
//    }

        ArrayList<Game> gameList = new ArrayList<Game>();
        Iterable<Game> allGames = games.findAll();//
        //the games repo hibernate ORM(object relational mapping) maps an object to a table sql db(relational db), takes java objects and maps them to relational db
        for (Game game : allGames) {
            gameList.add(game);



        }
        try
        {
            System.out.println("catching a nap");
            Thread.sleep(3000);
            System.out.println("I feel better now");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return gameList;
    }}

