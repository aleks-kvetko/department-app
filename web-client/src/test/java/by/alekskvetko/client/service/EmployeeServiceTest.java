package by.alekskvetko.client.service;

import by.alekskvetko.client.model.Employee;
import by.alekskvetko.client.model.SearchDateDTO;
import by.alekskvetko.client.model.views.EmployeeInfoView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeServiceTest {

    private String REST_EMPLOYEE_ENDPOINT = "http://localhost:8080/rest-service/employee/";
    private MockRestServiceServer mockServer;
    private ObjectMapper objectMapper;
    private SimpleDateFormat sdf;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    RestTemplate restTemplate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockServer = MockRestServiceServer.createServer(restTemplate);
        objectMapper=new ObjectMapper();
        sdf=new SimpleDateFormat("dd-MM-yyyy");
    }

    @Test
    public void testCreateEmployee() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setFullName("John Doe");
        employee.setDateOfBirth(sdf.parse("01-01-1981"));
        employee.setSalary(1000L);
        employee.setDepartmentId(1L);
        mockServer.expect(requestTo(REST_EMPLOYEE_ENDPOINT))
                .andExpect(content().string(objectMapper.writeValueAsString(employee)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess());
        // When
        employeeService.saveEmployee(employee);
        // Then
        mockServer.verify();
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFullName("John Doe");
        employee.setDateOfBirth(sdf.parse("01-01-1981"));
        employee.setSalary(1000L);
        employee.setDepartmentId(1L);
        mockServer.expect(requestTo(REST_EMPLOYEE_ENDPOINT))
                .andExpect(content().string(objectMapper.writeValueAsString(employee)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess());
        // When
        employeeService.saveEmployee(employee);
        // Then
        mockServer.verify();
    }

    @Test
    public void testReadEmployee() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFullName("John Doe");
        employee.setDateOfBirth(sdf.parse("01-01-1981"));
        employee.setSalary(1000L);
        employee.setDepartmentId(1L);
        String response=objectMapper.writeValueAsString(employee);
        mockServer.expect(requestTo(REST_EMPLOYEE_ENDPOINT+1L))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
        // When
        Employee actual=employeeService.readEmployee(1L);
        // Then
        assertEquals(employee,actual);
        mockServer.verify();
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        // Given
        mockServer.expect(requestTo(REST_EMPLOYEE_ENDPOINT+1L))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess());
        // When
        employeeService.deleteEmployee(1L);
        // Then
        mockServer.verify();
    }

    @Test
    public void testReadAllEmployeeInfoViews() throws Exception {
        // Given
        EmployeeInfoView employee1 = new EmployeeInfoView();
        employee1.setId(1L);
        employee1.setFullName("John Doe");
        employee1.setDateOfBirth(sdf.parse("01-01-1981"));
        employee1.setSalary(1000L);
        employee1.setDepartmentName("dep1");
        EmployeeInfoView employee2 = new EmployeeInfoView();
        employee2.setId(2L);
        employee2.setFullName("Jane Doe");
        employee2.setDateOfBirth(sdf.parse("01-01-1980"));
        employee2.setSalary(2000L);
        employee2.setDepartmentName("dep1");
        List<EmployeeInfoView> employees = Arrays.asList(employee1, employee2);
        String response=objectMapper.writeValueAsString(employees);
        mockServer.expect(requestTo(REST_EMPLOYEE_ENDPOINT))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
        // When
        List<EmployeeInfoView> actual=employeeService.readAllEmployeeInfoViews();
        // Then
        assertEquals(employees,actual);
        mockServer.verify();
    }

    @Test
    public void testReadEmployeeInfoViewsByDepartmentId() throws Exception {
        // Given
        EmployeeInfoView employee1 = new EmployeeInfoView();
        employee1.setId(1L);
        employee1.setFullName("John Doe");
        employee1.setDateOfBirth(sdf.parse("01-01-1981"));
        employee1.setSalary(1000L);
        employee1.setDepartmentName("dep1");
        EmployeeInfoView employee2 = new EmployeeInfoView();
        employee2.setId(2L);
        employee2.setFullName("Jane Doe");
        employee2.setDateOfBirth(sdf.parse("01-01-1980"));
        employee2.setSalary(2000L);
        employee2.setDepartmentName("dep1");
        List<EmployeeInfoView> employees = Arrays.asList(employee1, employee2);
        String response=objectMapper.writeValueAsString(employees);
        mockServer.expect(requestTo(REST_EMPLOYEE_ENDPOINT+"department/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
        // When
        List<EmployeeInfoView> actual=employeeService.readEmployeeInfoViewsByDepartmentId(1L);
        // Then
        assertEquals(employees,actual);
        mockServer.verify();
    }

    @Test
    public void testSearchEmployeeByDate() throws Exception {
        // Given
        EmployeeInfoView employee1 = new EmployeeInfoView();
        employee1.setId(1L);
        employee1.setFullName("John Doe");
        employee1.setDateOfBirth(sdf.parse("01-01-1981"));
        employee1.setSalary(1000L);
        employee1.setDepartmentName("dep1");
        EmployeeInfoView employee2 = new EmployeeInfoView();
        employee2.setId(2L);
        employee2.setFullName("Jane Doe");
        employee2.setDateOfBirth(sdf.parse("01-01-1980"));
        employee2.setSalary(2000L);
        employee2.setDepartmentName("dep1");
        List<EmployeeInfoView> employees = Arrays.asList(employee1, employee2);
        SearchDateDTO searchDateDTO=new SearchDateDTO("01-01-1979","01-01-1989",null);
        String response=objectMapper.writeValueAsString(employees);
        String query = UriComponentsBuilder.fromUriString(REST_EMPLOYEE_ENDPOINT + "search")
                .queryParam("startDate",searchDateDTO.getStartDate())
                .queryParam("endDate",searchDateDTO.getEndDate()).toUriString();
        mockServer.expect(requestTo(query))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
        // When
        List<EmployeeInfoView> actual=employeeService.searchEmployeeByDate(searchDateDTO);
        // Then
        assertEquals(employees,actual);
        mockServer.verify();

    }
}