package by.alekskvetko.client.service;

import by.alekskvetko.client.model.Employee;
import by.alekskvetko.client.model.SearchDateDTO;
import by.alekskvetko.client.model.views.EmployeeInfoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * An implementation of a {@link EmployeeService} which works
 * with REST web-service through RestTemplate
 *
 * @author ALEKSANDR KVETKO
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private String REST_EMPLOYEE_ENDPOINT = "http://localhost:8080/rest-service/employee/";
    @Autowired
    private RestTemplate restTemplate;

    public void saveEmployee(Employee employee) {
        try {
            if (employee.getId() == null)
                restTemplate.postForObject(REST_EMPLOYEE_ENDPOINT, employee, Employee.class);
            else restTemplate.put(REST_EMPLOYEE_ENDPOINT, employee);
        } catch (RestClientException e) {
            logger.error("Error while processing request to "
                    + REST_EMPLOYEE_ENDPOINT + " with code " + e.getMessage());
        }
    }

    public Employee readEmployee(long id) {
        try {
            Employee employee = restTemplate.getForObject(REST_EMPLOYEE_ENDPOINT + id, Employee.class);
            return employee;
        } catch (RestClientException e) {
            logger.error("Error while processing request [GET] to "
                    + REST_EMPLOYEE_ENDPOINT + id + " with code " + e.getMessage());
            return null;
        }

    }


    public void deleteEmployee(long id) {
        try {
            restTemplate.delete(REST_EMPLOYEE_ENDPOINT + id);
        } catch (RestClientException e) {
            logger.error("Error while processing request [DELETE] to "
                    + REST_EMPLOYEE_ENDPOINT + id + " with code " + e.getMessage());
        }
    }


    public List<EmployeeInfoView> readAllEmployeeInfoViews() {
        try {
            ResponseEntity<List<EmployeeInfoView>> employees = restTemplate.exchange(REST_EMPLOYEE_ENDPOINT, HttpMethod.GET,
                    null, new ParameterizedTypeReference<List<EmployeeInfoView>>() {
                    });
            return employees.getBody();
        } catch (RestClientException e) {
            logger.error("Error while processing request [GET] to "
                    + REST_EMPLOYEE_ENDPOINT + " with code " + e.getMessage());
            return null;
        }

    }


    public List<EmployeeInfoView> readEmployeeInfoViewsByDepartmentId(long id) {
        try {
            ResponseEntity<List<EmployeeInfoView>> employees = restTemplate.exchange(REST_EMPLOYEE_ENDPOINT + "department/" + id, HttpMethod.GET,
                    null, new ParameterizedTypeReference<List<EmployeeInfoView>>() {
                    });
            return employees.getBody();
        } catch (RestClientException e) {
            logger.error("Error while processing request [GET] to "
                    + REST_EMPLOYEE_ENDPOINT + "department/" + id + " with code " + e.getMessage());
            return null;
        }
    }

    public List<EmployeeInfoView> searchEmployeeByDate(SearchDateDTO searchDateDTO) {

        UriComponentsBuilder queryBuilder = UriComponentsBuilder.fromUriString(REST_EMPLOYEE_ENDPOINT + "search");

        if (!(searchDateDTO.getCertainDate() == null)) {
            queryBuilder.queryParam("certainDate", searchDateDTO.getCertainDate());
        }
        if (!(searchDateDTO.getStartDate() == null)) {
            queryBuilder.queryParam("startDate", searchDateDTO.getStartDate());
        }
        if (!(searchDateDTO.getEndDate() == null)) {
            queryBuilder.queryParam("endDate", searchDateDTO.getEndDate());
        }
        String query = queryBuilder.build().toUriString();
        try {
            ResponseEntity<List<EmployeeInfoView>> employees = restTemplate.exchange(query, HttpMethod.GET,
                    null, new ParameterizedTypeReference<List<EmployeeInfoView>>() {
                    });
            return employees.getBody();
        } catch (RestClientException e) {
            logger.error("Error while processing request [GET] to "
                    + query + " with code " + e.getMessage());
            return null;
        }

    }
}
