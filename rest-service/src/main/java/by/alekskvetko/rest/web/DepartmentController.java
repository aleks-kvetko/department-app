package by.alekskvetko.rest.web;

import by.alekskvetko.rest.model.Department;
import by.alekskvetko.rest.model.views.SalaryView;
import by.alekskvetko.rest.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for work with {@link Department} and {@link SalaryView} objects
 *
 * @author ALEKSANDR KVETKO
 */
@RestController
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private DepartmentService departmentService;


    /**
     * Reads list of all departments and returns response with desired data and http response code
     *
     * @return list of all departments and http code 200(OK) if there are at least one department
     *         or http code 404(NOT FOUND) if there aren't any departments
     *         or http code 500(INTERNAL SERVER ERROR) in case if any exception occurs
     */
    @RequestMapping(value = "/department/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Department>> getAllDepartments() {
        try {
            List<Department> departments = departmentService.readAllDepartments();
            if (departments.size() == 0) return new ResponseEntity<List<Department>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Department>>(departments, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /department/ :",e);
            return new ResponseEntity<List<Department>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Reads department with specified id and returns response with desired data and http response code
     *
     * @return department and http code 200(OK) if department with this id exists
     *         or http code 404(NOT FOUND) if department with this id doesn't exist
     *         or http code 500(INTERNAL SERVER ERROR) in case if any exception occurs
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> readDepartmentById(@PathVariable("id") long id) {
        try {
            Department department = departmentService.readDepartment(id);
            if (department == null) return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Department>(department, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /department/"+id+" :",e);
            return new ResponseEntity<Department>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Saves new department retrieved from request and returns response with http response code
     *
     * @return http code 201(CREATED) if department was created
     *         or http code 500(INTERNAL SERVER ERROR) in case if any exception occurs
     */
    @RequestMapping(value = "/department/", method = RequestMethod.POST)
    public ResponseEntity<Void> createDepartment(@RequestBody Department department) {
        try {
            departmentService.saveDepartment(department);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error while processing request [POST] /department/ :",e);
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Saves edited department retrieved from request and returns response with http response code
     *
     * @return http code 200(OK) if department was successfully saved
     *         or http code 404(NOT FOUND) if department with this id doesn't exist
     *         or http code 500(INTERNAL SERVER ERROR) in case if any exception occurs
     */
    @RequestMapping(value = "/department/", method = RequestMethod.PUT)
    public ResponseEntity<Department> updateDepartmentById(@RequestBody Department newDepartment) {
        try {
            Department currentDepartment = departmentService.readDepartment(newDepartment.getId());
            if (currentDepartment == null) return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
            departmentService.saveDepartment(newDepartment);
            return new ResponseEntity<Department>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while processing request [PUT] /department/ :",e);
            return new ResponseEntity<Department>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes department with specified id and returns response with http response code
     *
     * @return 200(OK) if department with this id was successfully deleted
     *         or http code 404(NOT FOUND) if department with this id doesn't exist
     *         or http code 500(INTERNAL SERVER ERROR) in case if any exception occurs
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Department> deleteDepartmentById(@PathVariable("id") long id) {
        try {
            Department currentDepartment = departmentService.readDepartment(id);
            if (currentDepartment == null) return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
            departmentService.deleteDepartment(id);
            return new ResponseEntity<Department>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while processing request [DELETE] /department/"+id+" :",e);
            return new ResponseEntity<Department>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Reads list of all salary views and returns response with desired data and http response code
     *
     * @return list of all salary views and http code 200(OK) if there are at least one department
     *         or http code 404(NOT FOUND) if there aren't any departments
     *         or http code 500(INTERNAL SERVER ERROR) in case if any exception occurs
     */
    @RequestMapping(value = "/department/average", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SalaryView>> readAverageSalary() {
        try {
            List<SalaryView> salaryViews = departmentService.readAverageSalary();
            return new ResponseEntity<List<SalaryView>>(salaryViews, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while processing request [GET] /department/average :",e);
            return new ResponseEntity<List<SalaryView>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
