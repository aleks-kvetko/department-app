package by.alekskvetko.rest.service;

import by.alekskvetko.rest.dao.DepartmentDAO;
import by.alekskvetko.rest.model.Department;
import by.alekskvetko.rest.model.views.SalaryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * An implementation of {@link DepartmentService} interface
 *
 * @author ALEKSANDR KVETKO
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {


    @Autowired
    private DepartmentDAO departmentDAO;


    public Department readDepartment(long id) {
        return departmentDAO.readDepartment(id);
    }

    public void saveDepartment(Department department) {
        if (department.getId()==null) {
            departmentDAO.createDepartment(department);
        }
        else departmentDAO.updateDepartment(department);
    }

    public void deleteDepartment(long id) {
        departmentDAO.deleteDepartment(id);
    }

    public List<Department> readAllDepartments() {
        return departmentDAO.readAllDepartments();
    }

    public List<SalaryView> readAverageSalary() {
        return departmentDAO.readAverageSalary();
    }
}
