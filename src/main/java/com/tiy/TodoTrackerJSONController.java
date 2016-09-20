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
public class TodoTrackerJSONController{

    @Autowired//says take the interface, create a concrete implementation that can speak to this repository
            TodoRepository todos;//postgress db

    @RequestMapping(path = "/toggleTodo.json", method = RequestMethod.GET)
    public ArrayList<Todo> toggleTodo(int todoID) {
        System.out.println("toggling todo with ID " + todoID);

        Todo todo = todos.findOne(todoID);
        todo.isDone = !todo.isDone;
        if(todo.isDone == true)
        {
            todo.name += "(Done!)";
        }
        else
        {
            todo.name = todo.name.substring(0, todo.name.length() - 7);
        }
        todos.save(todo);

        return addTodo();

    }

    @RequestMapping(path = "/addTodo.json", method = RequestMethod.POST)
    public ArrayList<Todo> addTodo(HttpSession session, @RequestBody Todo todo) throws Exception {//this changes the object from json to java game object
        //session is added in because the method can't be called without a user being logged in
        User user = (User)session.getAttribute("user");
        //todo.name = name;

        if (user == null) {
            throw new Exception("Unable to add game without an active user in the session");
        }
        todo.user = user;

        todos.save(todo);

        return addTodo();
    }



    @RequestMapping(path = "/todos.json", method = RequestMethod.GET)//this can be any url... not just .json
    public ArrayList<Todo> addTodo() {
//        return new Game(name, platform, genre, releaseYear, user);
//    }

        ArrayList<Todo> todoList = new ArrayList<Todo>();
        Iterable<Todo> allTodos = todos.findAll();//
        //the games repo hibernate ORM(object relational mapping) maps an object to a table sql db(relational db), takes java objects and maps them to relational db
        for (Todo todo : allTodos) {
            todoList.add(todo);



        }
//        try
//        {
//            System.out.println("catching a nap");
//            Thread.sleep(3000);
//            System.out.println("I feel better now");
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
        return todoList;
    }
}

