package by.alekskvetko.rest.service;

import by.alekskvetko.rest.dao.EmployeeDAO;
import by.alekskvetko.rest.model.Employee;
import by.alekskvetko.rest.model.SearchDateDTO;
import by.alekskvetko.rest.model.views.EmployeeInfoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * An implementation of {@link EmployeeService} interface
 *
 * @author ALEKSANDR KVETKO
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    public void saveEmployee(Employee employee) {
        if (employee.getId() == null) {
            employeeDAO.createEmployee(employee);
        } else employeeDAO.updateEmployee(employee);
    }

    public Employee readEmployee(long id) {
        Employee employee = employeeDAO.readEmployee(id);
        return employee;
    }


    public void deleteEmployee(long id) {
        employeeDAO.deleteEmployee(id);
    }


    public List<EmployeeInfoView> readAllEmployeeInfoViews() {
        return employeeDAO.readAllEmployeeInfoViews();
    }


    public List<EmployeeInfoView> readEmployeeInfoViewsByDepartmentId(long id) {
        return employeeDAO.readEmployeeInfoViewsByDepartmentId(id);
    }

    public List<EmployeeInfoView> searchEmployeeByDate(SearchDateDTO searchDateDTO) {
        return employeeDAO.searchEmployeeByDate(searchDateDTO);
    }
}
