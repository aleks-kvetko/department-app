package by.alekskvetko.rest.dao;

import by.alekskvetko.rest.model.Department;
import by.alekskvetko.rest.model.views.SalaryView;

import java.util.List;

/**
 * DAO interface for work with {@link Department} objects
 *
 * @author ALEKSANDR KVETKO
 */
public interface DepartmentDAO {

    /**
     * Saves new department into a database
     *
     * @param department
     *        department to be saved
     *
     */
    void createDepartment(Department department);

    /**
     * Reads a department with a specified id from a database
     *
     * @param id
     *        id of a department
     *
     * @return {@link Department} object
     */
    Department readDepartment(long id);

    /**
     * Updates a department in a database
     *
     * @param department
     *        department to be updated
     */
    void updateDepartment(Department department);

    /**
     * Deletes a department with a specified id from a database
     *
     * @param id
     *        id of a department to be deleted
     */
    void deleteDepartment(long id);

    /**
     * Reads all departments from a database
     *
     * @return list of all departments
     */
    List<Department> readAllDepartments();

    /**
     * Reads list of all departments with their average salary
     *
     * @return list of {@link SalaryView} objects
     */
    List<SalaryView> readAverageSalary();
}
