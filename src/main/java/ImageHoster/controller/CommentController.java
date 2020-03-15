package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


@Controller
public class CommentController {


    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;


    @RequestMapping(value ="/image/{imageId}/{imageTitle}/comments" , method = RequestMethod.POST)
    public String addComment(@PathVariable("imageId") Integer id, @PathVariable("imageTitle") String title, HttpSession session, @RequestParam("comment") String text, Comment comment, Model model) {

        User user= (User) session.getAttribute("loggeduser");
        comment.setCreatedDate(new Date());
        comment.setImage(imageService.getImage(id));
        comment.setText(text);
        comment.setUser(user);
        commentService.uploadComment(comment);
        model.addAttribute("image",imageService.getImage(id));
        List<Comment> comments=commentService.getAllComments(imageService.getImage(id));
        model.addAttribute("comments",comments);
        return "redirect:/images/" + id + "/" + title;
    }
}
