package by.alekskvetko.rest.dao;

import by.alekskvetko.rest.model.Employee;
import by.alekskvetko.rest.model.SearchDateDTO;
import by.alekskvetko.rest.model.views.EmployeeInfoView;

import java.util.List;

/**
 * DAO interface for work with {@link Employee} objects
 *
 * @author ALEKSANDR KVETKO
 */
public interface EmployeeDAO {

    /**
     * Saves new employee into a database
     *
     * @param employee
     *        employee to be saved
     */
    void createEmployee(Employee employee);

    /**
     * Reads an employee with a specified id from a database
     *
     * @param id
     *        id of an employee
     *
     * @return {@link Employee} object
     */
    Employee readEmployee(long id);

    /**
     * Updates an employee in a database
     *
     * @param employee
     *        an employee to be updated
     */
    void updateEmployee(Employee employee);

    /**
     * Deletes an employee with a specified id from a database
     *
     * @param id
     *        id of an employee to be deleted
     */
    void deleteEmployee(long id);

    /**
     * Reads all employee views from a database
     *
     * @return list of {@link EmployeeInfoView} objects
     */
    List<EmployeeInfoView> readAllEmployeeInfoViews();

    /**
     * Reads all employee views with specified department id from a database
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
     */
    List<EmployeeInfoView> searchEmployeeByDate(SearchDateDTO searchDateDTO);

}
