package by.alekskvetko.client.web;

import by.alekskvetko.client.model.Department;
import by.alekskvetko.client.model.views.SalaryView;
import by.alekskvetko.client.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller which works with jsp pages and {@link Department} and {@link SalaryView} objects
 *
 * @author ALEKSANDR KVETKO
 */
@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private DepartmentService departmentService;

    /**
     * Custom initialization of the WebDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {

        /** convert empty/whitespace strings to null */
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    /**
     * Returns departments page with attribute:
     *      list of all departments
     *
     * @return forwards to departments page
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllDepartments(Model model) {
        try {
            List<Department> departments = departmentService.readAllDepartments();
            model.addAttribute("departments", departments);
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /departments/ :",e);
        }
        return "departments";
    }

    /**
     * Deletes department with specified id
     *
     * @return redirects to departments page
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteDepartmentById(@PathVariable("id") int id) {
        try {
            departmentService.deleteDepartment(id);
        } catch (Exception e) {
            logger.error("Error while processing request [POST] /departments/delete/"+id+" :",e);
        }
        return "redirect:/departments";
    }

    /**
     * Returns department add page with attribute:
     *      new department object
     *
     * @return forwards to department form
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String createDepartment(Model model) {
        try {
            model.addAttribute("department", new Department());
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /departments/add :",e);
        }
        return "departmentForm";
    }

    /**
     * Saves department retrieved from a employee form
     *
     * @param department
     *        department to be saved
     *
     * @return redirects to departments page if department was successfully saved
     *         or forwards to department form if there are any validation errors
     */
    @RequestMapping(method = RequestMethod.POST)
    public String saveDepartment(@ModelAttribute("department") @Valid Department department, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "departmentForm";
            }
            departmentService.saveDepartment(department);
        } catch (Exception e) {
            logger.error("Error while processing request [POST] /departments/ :",e);
        }
        return "redirect:/departments";
    }

    /**
     * Returns department edit page for department with specified id with attribute:
     *      department object
     *
     * @return forwards to department form
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editDepartment(@PathVariable("id") int id, Model model) {
        try {
            Department department = departmentService.readDepartment(id);
            model.addAttribute("department", department);
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /departments/edit/"+id+" :",e);
        }
        return "departmentForm";
    }

    /**
     * Returns page with average salary for each department with attribute
     *      list of all salary views
     *
     * @return forwards to salary page
     */
    @RequestMapping(value = "/salary", method = RequestMethod.GET)
    public String getAverageSalary(Model model) {
        try {
            List<SalaryView> salaryView = departmentService.readAverageSalary();
            model.addAttribute("salaryView", salaryView);
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /departments/salary :",e);
        }
        return "salary";
    }

}
