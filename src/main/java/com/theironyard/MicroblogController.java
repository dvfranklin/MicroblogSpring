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

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){

        Iterable<Message> messages = messageRepo.findAllByOrderByIdAsc();


        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("messages", messages);



        return "home";
    }


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username){
        session.setAttribute("username", username);
        return "redirect:/";
    }

    @RequestMapping(path = "/add-message", method = RequestMethod.POST)
    public String addMessage(String messageText){
        Message message = new Message(messageText);

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

}
