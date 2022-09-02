package com.project.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.project.sms.entity.Student;
import com.project.sms.service.StudentService;

@Controller
public class StudentController {

	private StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage() {
		return "home_page";
	}

	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public String listStudents(Model model) {
		model.addAttribute("students", studentService.getAllStudents());
		return "students";
	}

	@RequestMapping(value = "/students/new", method = RequestMethod.GET)
	public String createStudentForm(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return "create_student";
	}

	@RequestMapping(value = "/students", method = RequestMethod.POST)
	public String saveStudent(@ModelAttribute("student") Student student) {
		studentService.saveStudent(student);
		return "redirect:/students";
	}

	@RequestMapping(value = "/students/edit/{id}", method = RequestMethod.GET)
	public String editStudentForm(@PathVariable Long id, Model model) {
		model.addAttribute("student", studentService.getStudentById(id));
		return "edit_student";
	}

	@RequestMapping(value = "/students/{id}", method = RequestMethod.POST)
	public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student, Model model) {
		Student exs = studentService.getStudentById(id);
		exs.setId(id);
		exs.setFirstname(student.getFirstname());
		exs.setLastname(student.getLastname());
		exs.setEmail(student.getEmail());
		studentService.updateStudent(exs);
		return "redirect:/students";
	}

	@RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
	public String deleteStudent(@PathVariable Long id) {
		studentService.deleteStudent(id);
		return "redirect:/students";
	}
}
