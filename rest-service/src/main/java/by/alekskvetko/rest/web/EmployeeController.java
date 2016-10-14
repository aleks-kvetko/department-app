package by.alekskvetko.rest.web;

import by.alekskvetko.rest.model.Employee;
import by.alekskvetko.rest.model.SearchDateDTO;
import by.alekskvetko.rest.model.views.EmployeeInfoView;
import by.alekskvetko.rest.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for work with {@link Employee} and {@link EmployeeInfoView} objects
 *
 * @author ALEKSANDR KVETKO
 */
@RestController
public class EmployeeController {

    private static final Logger logger= LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;


    /**
     * Reads employee with specified id and returns response with desired data and http response code
     *
     * @return employee and http code 200(OK) if employee with this id exists
     *         or http code 404(NOT FOUND) if employee with this id doesn't exist
     *         or http code 500(INTERNAL SERVER ERROR) in case if any exception occurs
     */
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> readEmployeeById(@PathVariable("id") long id) {
        try {
            Employee employee = employeeService.readEmployee(id);
            if (employee == null) return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Employee>(employee, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /employee/"+id+" :",e);
            return new ResponseEntity<Employee>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Reads list of all employee views and returns response with desired data and http response code
     *
     * @return list of all employee views and http code 200(OK) if there are at least one employee
     *         or http code 404(NOT FOUND) if there aren't any employees
     *         or http code 500(INTERNAL SERVER ERROR) in case if any exception occurs
     */
    @RequestMapping(value = "/employee/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeInfoView>> getAllEmployeeInfoViews() {
        try {
            List<EmployeeInfoView> employeesInfoViews = employeeService.readAllEmployeeInfoViews();
            if (employeesInfoViews.size() == 0) return new ResponseEntity<List<EmployeeInfoView>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<EmployeeInfoView>>(employeesInfoViews, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /employee/ :",e);
            return new ResponseEntity<List<EmployeeInfoView>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Saves new employee retrieved from request and returns response with http response code
     *
     * @return http code 201(CREATED) if employee was created
     *         or http code 500(INTERNAL SERVER ERROR) in case if any exception occurs
     */
    @RequestMapping(value = "/employee/", method = RequestMethod.POST)
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee) {
        try {
            employeeService.saveEmployee(employee);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error while processing request [POST] /employee/ :",e);
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Saves edited employee retrieved from request and returns response with http response code
     *
     * @return http code 200(OK) if employee was successfully saved
     *         or http code 404(NOT FOUND) if employee with this id doesn't exist
     *         or http code 500(INTERNAL SERVER ERROR) in case if any exception occurs
     */
    @RequestMapping(value = "/employee/", method = RequestMethod.PUT)
    public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee newEmployee) {
        try {
            Employee currentEmployee = employeeService.readEmployee(newEmployee.getId());
            if (currentEmployee == null) return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
            employeeService.saveEmployee(newEmployee);
            return new ResponseEntity<Employee>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while processing request [PUT] /employee/ :",e);
            return new ResponseEntity<Employee>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes employee with specified id and returns response with http response code
     *
     * @return 200(OK) if employee with this id was successfully deleted
     *         or http code 404(NOT FOUND) if employee with this id doesn't exist
     *         or http code 500(INTERNAL SERVER ERROR) in case if any exception occurs
     */
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable("id") long id) {
        try {
            Employee currentEmployee = employeeService.readEmployee(id);
            if (currentEmployee == null) return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
            employeeService.deleteEmployee(id);
            return new ResponseEntity<Employee>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while processing request [DELETE] /employee/"+id+" :",e);
            return new ResponseEntity<Employee>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Reads list of all employee views with specified id of department
     * and returns response with desired data and http response code
     *
     * @return list of all employee views and http code 200(OK)
     *         if there are at least one employee with this department id
     *         or http code 404(NOT FOUND) if there aren't any employees with this department id
     *         or http code 500(INTERNAL SERVER ERROR) in case if any exception occurs
     */
    @RequestMapping(value = "/employee/department/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeInfoView>> readEmployeeInfoViewByDepartmentId(@PathVariable("id") long id) {
        try {
            List<EmployeeInfoView> employeeInfoViews = employeeService.readEmployeeInfoViewsByDepartmentId(id);
            if (employeeInfoViews.size() == 0) return new ResponseEntity<List<EmployeeInfoView>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<EmployeeInfoView>>(employeeInfoViews, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /employee/department/"+id+" :",e);
            return new ResponseEntity<List<EmployeeInfoView>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Reads list of all employee views with date of birth
     * which satisfied parameters retrieved from request
     * and returns response with desired data and http response code
     *
     * @return list of all employee views and http code 200(OK)
     *         if there are at least one employee
     *         or http code 404(NOT FOUND) if there aren't any employees
     *         or http code 500(INTERNAL SERVER ERROR) in case if any exception occurs
     */
    @RequestMapping(value = "/employee/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeInfoView>> searchEmployeeByDate(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "certainDate", required = false) String certainDate
    ) {
        try {
            SearchDateDTO searchDateDTO = new SearchDateDTO(startDate, endDate, certainDate);
            List<EmployeeInfoView> employeeInfoViews = employeeService.searchEmployeeByDate(searchDateDTO);
            if (employeeInfoViews.size() == 0) return new ResponseEntity<List<EmployeeInfoView>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<EmployeeInfoView>>(employeeInfoViews, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /employee/search",e);
            return new ResponseEntity<List<EmployeeInfoView>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
