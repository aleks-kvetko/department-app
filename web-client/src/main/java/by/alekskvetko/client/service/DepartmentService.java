package by.alekskvetko.client.service;

import by.alekskvetko.client.model.Department;
import by.alekskvetko.client.model.views.SalaryView;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface which provides methods for interaction
 * with REST web-service for {@link Department} objects
 *
 * @author ALEKSANDR KVETKO
 */
@Service
public interface DepartmentService {

    /**
     * Saves new or existing department
     *
     * @param department
     *        department to be saved
     */
    void saveDepartment(Department department);

    /**
     * Reads a department with a specified id
     *
     * @param id
     *        id of a department
     *
     * @return {@link Department} object
     */
    Department readDepartment(long id);

    /**
     * Deletes a department with a specified id
     *
     * @param id
     *        id of a department to be deleted
     */
    void deleteDepartment(long id);

    /**
     * Reads all departments
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
