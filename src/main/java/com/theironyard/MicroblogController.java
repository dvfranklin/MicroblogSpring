package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class MicroblogController {

    @Autowired
    MessageRepository messageRepo;

    @Autowired
    UserRepository userRepo;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){

        User user = (User)session.getAttribute("user");

        Iterable<Message> messages = messageRepo.findByAuthorOrderByIdAsc(user);


        model.addAttribute("user", user);
        model.addAttribute("messages", messages);

        return "home";
    }


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username){

        User user = userRepo.findFirstByUsername(username);

        session.setAttribute("user", user);
        return "redirect:/";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.GET)
    public String signUpPage(){
        return "signup";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String signUp(String username){
        User user = new User(username);
        userRepo.save(user);
        return "redirect:/";
    }

    @RequestMapping(path = "/add-message", method = RequestMethod.POST)
    public String addMessage(String messageText, HttpSession session){
        Message message = new Message(messageText, (User)session.getAttribute("user"));

        messageRepo.save(message);

        return "redirect:/";
    }

    @RequestMapping(path = "/delete-message", method = RequestMethod.POST)
    public String deleteMessage(Integer messageId){

        messageRepo.delete(messageId);

        return "redirect:/";
    }

    @RequestMapping(path = "/edit-message", method = RequestMethod.GET)
    public String editMessageView(Model model, Integer messageId){

        Message message = messageRepo.findOne(messageId);
        model.addAttribute("message", message);

        return "edit";
    }

    @RequestMapping(path = "/edit-message", method = RequestMethod.POST)
    public String editMessage(Integer messageId, String message){

        Message m = messageRepo.findOne(messageId);
        m.setMessage(message);
        messageRepo.save(m);

        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
