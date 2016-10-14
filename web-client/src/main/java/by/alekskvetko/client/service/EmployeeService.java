package by.alekskvetko.client.service;

import by.alekskvetko.client.model.Employee;
import by.alekskvetko.client.model.SearchDateDTO;
import by.alekskvetko.client.model.views.EmployeeInfoView;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface which provides methods for interaction
 * with REST web-service for {@link Employee} objects
 *
 * @author ALEKSANDR KVETKO
 */
@Service
public interface EmployeeService {

    /**
     * Saves new or existing employee
     *
     * @param employee
     *        an employee to be saved
     */
    void saveEmployee(Employee employee);

    /**
     * Reads an employee with a specified id
     *
     * @param id
     *        id of an employee
     *
     * @return {@link Employee} object
     */
    Employee readEmployee(long id);

    /**
     * Deletes an employee with a specified id
     *
     * @param id
     *        id of a employee to be deleted
     */
    void deleteEmployee(long id);

    /**
     * Reads all employee views
     *
     * @return list of {@link EmployeeInfoView} objects
     */
    List<EmployeeInfoView> readAllEmployeeInfoViews();

    /**
     * Reads all employee views with specified department id
     *
     * @param id
     *        id of the department
     *
     * @return list of {@link EmployeeInfoView} objects
     */
    List<EmployeeInfoView> readEmployeeInfoViewsByDepartmentId(long id);

    /**
     * Searches employees with date of birth
     * which satisfies parameters stored in {@link SearchDateDTO} object
     *
     * @param searchDateDTO
     *        object with date parameters
     *
     * @return list of {@link EmployeeInfoView} objects
     *
     *
     *
     */
    List<EmployeeInfoView> searchEmployeeByDate(SearchDateDTO searchDateDTO);
}
