package com.tiy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfabiano on 9/15/2016.
 */
@Controller
public class GameTrackerController {

    @Autowired//says take the interface, create a concrete implementation that can speak to this repository
    GameRepository games;

    @Autowired
    UserRepository users;

    @RequestMapping(path = "/games", method = RequestMethod.GET)
    public String games(Model model, HttpSession session) {
        return "games";
    }

@RequestMapping(path = "/", method = RequestMethod.GET)
public String home(Model model, HttpSession session, String genre, Integer releaseYear){//notice NOT an integer(int), but an Integer so it can be null rather than 0 initially. Using the Boxed type vs the primitive type

    if (session.getAttribute("user") != null) {
        model.addAttribute("user", session.getAttribute("user"));
    }

    List<Game> gameList = new ArrayList<Game>();
    if (genre != null) {
        gameList = games.findByGenre(genre);
    } else if (releaseYear != null) {
        gameList = games.findByReleaseYear(releaseYear);
    } else {
        User savedUser = (User)session.getAttribute("user");
        if (savedUser != null) {
            gameList = games.findByUser(savedUser);
        } else {
            Iterable<Game> allGames = games.findAll();
            for (Game game : allGames) {
                gameList.add(game);
            }
        }
    }
    model.addAttribute("games", gameList);
    return "home";
}

    @RequestMapping(path = "/add-game", method = RequestMethod.POST)//post submits data to be processed to a specified resource
    public String addGame(HttpSession session, String gameName, String gamePlatform, String gameGenre, int gameYear) {
        User user = (User) session.getAttribute("user");
        Game game = new Game(gameName, gamePlatform, gameGenre, gameYear, user);//entity
        System.out.println("My runtime repo = " + games.toString());
        games.save(game);//repository
        return "redirect:/";
    }

    @RequestMapping(path = "/searchByName", method = RequestMethod.GET)//get requests data from a specified resource
    public String queryGamesByName(Model model, String search) {
        System.out.println("Searching by ..." + search);
        List<Game> gameList = games.findByNameStartsWith(search);
        model.addAttribute("games", gameList);
        return "home";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deleteGame(Model model, Integer gameID) {
        if (gameID != null) {
            games.delete(gameID);//delete is from CRUD
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws Exception {
        User user = users.findFirstByName(userName);//goes to db, finds first record matching the query, and returns this
        if (user == null) {
            user = new User(userName, password);
            users.save(user);
        }
        else if (!password.equals(user.getPassword())) {
            throw new Exception("Incorrect password");
        }
        session.setAttribute("user", user);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
