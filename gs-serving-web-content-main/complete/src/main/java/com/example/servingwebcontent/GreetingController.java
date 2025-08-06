package com.example.servingwebcontent;

import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.servingwebcontent.Data.Customer;


@Controller
public class GreetingController {

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		ArrayList<Customer> cus = new ArrayList<Customer>();
		model.addAttribute("name", name);
		return "greeting";
	}

}
