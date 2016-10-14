package by.alekskvetko.client.web;

import by.alekskvetko.client.model.Employee;
import by.alekskvetko.client.model.SearchDateDTO;
import by.alekskvetko.client.model.views.EmployeeInfoView;
import by.alekskvetko.client.service.DepartmentService;
import by.alekskvetko.client.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Controller which works with jsp pages and {@link Employee} and {@link EmployeeInfoView} objects
 *
 * @author ALEKSANDR KVETKO
 */
@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    /**
     * Custom initialization of the WebDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {

        /** convert empty/whitespace strings to null */
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        /** bind Date objects from strings with pattern dd-MM-yyyy */
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * Returns employees page with 2 attributes:
     *      list of all employee views
     *      new searchDateDTO object used for search
     *
     * @return forwards to employees page
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllEmployees(Model model) {
        try {
            List<EmployeeInfoView> employees = employeeService.readAllEmployeeInfoViews();
            model.addAttribute("employees", employees);
            model.addAttribute("searchDateDTO", new SearchDateDTO());
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /employees/ :",e);
        }
        return "employees";
    }

    /**
     * Returns employee add page with 2 attributes:
     *      new employee object
     *      list of all available departments
     *
     * @return forwards to employee form
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String createEmployee(Model model) {
        try {
            model.addAttribute("employee", new Employee());
            model.addAttribute("departments", departmentService.readAllDepartments());
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /employees/add :",e);
        }
        return "employeeForm";
    }

    /**
     * Saves employee retrieved from a employee form
     *
     * @param employee
     *        employee to be saved
     *
     * @return redirects to employees page if employee was successfully saved
     *         or forwards to employee form if there are any validation errors
     */
    @RequestMapping(method = RequestMethod.POST)
    public String saveEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("departments", departmentService.readAllDepartments());
                return "employeeForm";
            }
            employeeService.saveEmployee(employee);
        } catch (Exception e) {
            logger.error("Error while processing request [POST] /employees/ :",e);
        }
        return "redirect:/employees";
    }

    /**
     * Returns employee edit page for employee with specified id with 2 attributes:
     *      employee object
     *      list of all available departments
     *
     * @return forwards to employee form
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editEmployee(@PathVariable("id") int id, Model model) {
        try {
            Employee employee = employeeService.readEmployee(id);
            model.addAttribute("employee", employee);
            model.addAttribute("departments", departmentService.readAllDepartments());
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /employees/edit/"+id+" :",e);
        }
        return "employeeForm";
    }


    /**
     * Deletes employee with specified id
     *
     * @return redirects to employees page
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteEmployee(@PathVariable("id") int id) {
        try {
            employeeService.deleteEmployee(id);
        } catch (Exception e) {
            logger.error("Error while processing request [POST] /employees/delete/"+id+" :",e);
        }
        return "redirect:/employees";
    }

    /**
     * Returns page with list of employee views with specified department id
     * Contains 2 attributes:
     *      list of employee views
     *      department object
     *
     * @return forwards to employees by department page
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public String readEmployeesByDepartmentId(@PathVariable("id") int id, Model model) {
        try {
            model.addAttribute("employees", employeeService.readEmployeeInfoViewsByDepartmentId(id));
            model.addAttribute("department", departmentService.readDepartment(id));
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /employees/department/"+id+" :",e);
        }
        return "employeesByDep";
    }
    /**
     * Returns page with list of employee which date of birth satisfies
     * query parameters retrieved from searchDateDTO object
     * Contains 2 attributes:
     *      list of employee views
     *      action variable which indicates that we pass search results
     *
     * @return forwards to employees page with search results
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchEmployee(@ModelAttribute("searchDateDTO") SearchDateDTO searchDateDTO, Model model) {
        try {
            List<EmployeeInfoView> employees = employeeService.searchEmployeeByDate(searchDateDTO);
            model.addAttribute("employees", employees);
            model.addAttribute("action", "showResults");
        } catch (Exception e) {
            logger.error("Error while processing request [POST] /employees/search/ with parameters"+ searchDateDTO+ ":",e);
        }
        return "employees";
    }


}
