package edu.school21.Annotation.Models;

import edu.school21.Annotation.Annotations.HtmlForm;
import edu.school21.Annotation.Annotations.HtmlInput;

@HtmlForm(fileName = "car_form.html", action = "/cars", method = "post")
public class CarForm {
    @HtmlInput(type = "text", name = "priora", placeholder = "Enter the model of the car")
    private String model;
    @HtmlInput(type = "text", name = "ilon maks", placeholder = "Enter the name of the owner")
    private String owner;
    @HtmlInput(type = "mileage", name = "77", placeholder = "Enter the mileage of the car")
    private String mileage;
}
