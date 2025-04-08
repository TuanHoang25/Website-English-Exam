package com.example.DoAnJava.Controller;

import com.example.DoAnJava.models.Course;
import com.example.DoAnJava.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private CourseService courseService;
    @GetMapping
    public String index(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "home/index";
    }
    @GetMapping("/admin")
    public String homeAdmin() {

        return "home/index_admin";
    }
}
