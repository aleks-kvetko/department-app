package by.alekskvetko.client.service;

import by.alekskvetko.client.model.Department;
import by.alekskvetko.client.model.views.SalaryView;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DepartmentServiceImplTest {

    private String REST_DEPARTMENT_ENDPOINT = "http://localhost:8080/rest-service/department/";
    private MockRestServiceServer mockServer;
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockServer = MockRestServiceServer.createServer(restTemplate);
        objectMapper=new ObjectMapper();
    }

    @Test
    public void testCreateDepartment() throws Exception {
        // Given
        Department department= new Department();
        department.setName("foo");
        mockServer.expect(requestTo(REST_DEPARTMENT_ENDPOINT))
                .andExpect(content().string(objectMapper.writeValueAsString(department)))
                .andExpect(method(HttpMethod.POST)).andRespond(withSuccess());
        // When
        departmentService.saveDepartment(department);
        // Then
        mockServer.verify();
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        // Given
        Department department= new Department();
        department.setId(1L);
        department.setName("foo");
        mockServer.expect(requestTo(REST_DEPARTMENT_ENDPOINT))
                .andExpect(content().string(objectMapper.writeValueAsString(department)))
                .andExpect(method(HttpMethod.PUT)).andRespond(withSuccess());
        // When
        departmentService.saveDepartment(department);
        // Then
        mockServer.verify();
    }

    @Test
    public void testReadDepartment() throws Exception {
        // Given
        Department department= new Department();
        department.setId(1L);
        department.setName("foo");
        String response=objectMapper.writeValueAsString(department);
        mockServer.expect(requestTo(REST_DEPARTMENT_ENDPOINT+1L))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
        // When
        Department actual=departmentService.readDepartment(1L);
        // Then
        assertEquals(department,actual);
        mockServer.verify();

    }


    @Test
    public void testDeleteDepartment() throws Exception {
        // Given
        mockServer.expect(requestTo(REST_DEPARTMENT_ENDPOINT+1L))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess());
        // When
        departmentService.deleteDepartment(1L);
        // Then
        mockServer.verify();
    }

    @Test
    public void testReadAllDepartments() throws Exception {
        // Given
        Department department1= new Department();
        department1.setId(1L);
        department1.setName("foo");
        Department department2= new Department();
        department2.setId(1L);
        department2.setName("bar");
        List<Department> departments= Arrays.asList(department1,department2);
        String response=objectMapper.writeValueAsString(departments);
        mockServer.expect(requestTo(REST_DEPARTMENT_ENDPOINT))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
        // When
        List<Department> actual=departmentService.readAllDepartments();
        // Then
        assertEquals(departments,actual);
        mockServer.verify();

    }

    @Test
    public void testReadAverageSalary() throws Exception {
        // Given
        SalaryView department1= new SalaryView();
        department1.setId(1L);
        department1.setDepartmentName("foo");
        department1.setAverageSalary(1000L);
        SalaryView department2= new SalaryView();
        department2.setId(1L);
        department2.setDepartmentName("bar");
        department2.setAverageSalary(2000L);
        List<SalaryView> departments= Arrays.asList(department1,department2);
        String response=objectMapper.writeValueAsString(departments);
        mockServer.expect(requestTo(REST_DEPARTMENT_ENDPOINT+"average"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
        // When
        List<SalaryView> actual=departmentService.readAverageSalary();
        // Then
        assertEquals(departments,actual);
        mockServer.verify();
    }
}