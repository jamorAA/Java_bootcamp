package edu.school21.Annotation.Models;

import edu.school21.Annotation.Annotations.HtmlForm;
import edu.school21.Annotation.Annotations.HtmlInput;

@HtmlForm(fileName = "user_form.html", action = "/users", method = "post")
public class UserForm {
    @HtmlInput(type = "text", name = "vlastelin", placeholder = "Enter First Name")
    private String firstName;
    @HtmlInput(type = "text", name = "chorniy", placeholder = "Enter Last Name")
    private String lastName;
    @HtmlInput(type = "password", name = "34433443", placeholder = "Enter Password")
    private String password;
}
