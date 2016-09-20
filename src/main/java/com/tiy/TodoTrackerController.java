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
public class TodoTrackerController {

    @Autowired//says take the interface, create a concrete implementation that can speak to this repository
    TodoRepository todos;

    @Autowired
    UserRepository users;

    @RequestMapping(path = "/todos", method = RequestMethod.GET)//the login page, and the todo page is all we are using here for the assignment, the add, toggle, and delete, and the find all method
    public String todos(Model model, HttpSession session) {
        return "todos";
    }

@RequestMapping(path = "/", method = RequestMethod.GET)
public String home(Model model, HttpSession session){//notice NOT an integer(int), but an Integer so it can be null rather than 0 initially. Using the Boxed type vs the primitive type

    if (session.getAttribute("user") != null) {
        model.addAttribute("user", session.getAttribute("user"));
    }

    List<Todo> todoList = new ArrayList<Todo>();

        User savedUser = (User)session.getAttribute("user");
        if (savedUser != null) {
            todoList = todos.findByUser(savedUser);
        } else {
            Iterable<Todo> allTodos = todos.findAll();
            for (Todo todo : allTodos) {
                todoList.add(todo);
            }
    }
    model.addAttribute("todos", todoList);
    return "home";
}

    @RequestMapping(path = "/add-todo", method = RequestMethod.POST)//post submits data to be processed to a specified resource
    public String addTodo(HttpSession session, String todoName) {
        User user = (User) session.getAttribute("user");
        Todo todo = new Todo(todoName, user);//entity
        System.out.println("My runtime repo = " + todo.toString());
        todos.save(todo);//repository
        return "redirect:/";
    }

    @RequestMapping(path = "/searchByName", method = RequestMethod.GET)//get requests data from a specified resource
    public String queryTodosByName(Model model, String search) {
        System.out.println("Searching by ..." + search);
        List<Todo> todoList = todos.findByNameStartsWith(search);
        model.addAttribute("todos", todoList);
        return "home";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deleteTodo(Model model, Integer todoID) {
        if (todoID != null) {
            todos.delete(todoID);//delete is from CRUD
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
