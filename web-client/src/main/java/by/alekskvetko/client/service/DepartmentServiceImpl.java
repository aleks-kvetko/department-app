package by.alekskvetko.client.service;

import by.alekskvetko.client.model.Department;
import by.alekskvetko.client.model.views.SalaryView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * An implementation of a {@link DepartmentService} which works
 * with REST web-service through RestTemplate
 *
 * @author ALEKSANDR KVETKO
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    private String REST_DEPARTMENT_ENDPOINT = "http://localhost:8080/rest-service/department/";
    @Autowired
    private RestTemplate restTemplate;

    public void saveDepartment(Department department) {
        try {
            if (department.getId() == null)
                restTemplate.postForObject(REST_DEPARTMENT_ENDPOINT, department, Department.class);
            else restTemplate.put(REST_DEPARTMENT_ENDPOINT, department);
        } catch (RestClientException e) {
            logger.error("Error while processing request to "
                    + REST_DEPARTMENT_ENDPOINT + " with code " + e.getMessage());
        }
    }

    public Department readDepartment(long id) {
        try {
            Department department = restTemplate.getForObject(REST_DEPARTMENT_ENDPOINT + id, Department.class);
            return department;
        } catch (RestClientException e) {
            logger.error("Error while processing request [GET] to "
                    + REST_DEPARTMENT_ENDPOINT + id + " with code " + e.getMessage());
            return null;
        }
    }


    public void deleteDepartment(long id) {
        try {
            restTemplate.delete(REST_DEPARTMENT_ENDPOINT + id);
        } catch (RestClientException e) {
            logger.error("Error while processing request [DELETE] to "
                    + REST_DEPARTMENT_ENDPOINT + id + " with code " + e.getMessage());
        }
    }

    public List<Department> readAllDepartments() {
        try {
            ResponseEntity<List<Department>> departments = restTemplate.exchange(REST_DEPARTMENT_ENDPOINT, HttpMethod.GET,
                    null, new ParameterizedTypeReference<List<Department>>() {
                    });
            return departments.getBody();
        } catch (RestClientException e) {
            logger.error("Error while processing request [GET] to "
                    + REST_DEPARTMENT_ENDPOINT + " with code " + e.getMessage());
            return null;
        }

    }

    public List<SalaryView> readAverageSalary() {
        try {
            ResponseEntity<List<SalaryView>> salaryViews = restTemplate.exchange(REST_DEPARTMENT_ENDPOINT + "average", HttpMethod.GET,
                    null, new ParameterizedTypeReference<List<SalaryView>>() {
                    });
            return salaryViews.getBody();
        } catch (RestClientException e) {
            logger.error("Error while processing request [GET] to "
                    + REST_DEPARTMENT_ENDPOINT + "average" + " with code " + e.getMessage());
            return null;
        }
    }
}