package by.alekskvetko.rest.web;

import by.alekskvetko.rest.model.Employee;
import by.alekskvetko.rest.model.SearchDateDTO;
import by.alekskvetko.rest.model.views.EmployeeInfoView;
import by.alekskvetko.rest.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class EmployeeControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private EmployeeController employeeController;

    private SimpleDateFormat sdf;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        objectMapper = new ObjectMapper();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
    }


    @Test
    public void testReadEmployee_WhenEmployeeExists() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setDepartmentId(1L);
        employee.setDateOfBirth(sdf.parse("1980-01-01"));
        employee.setFullName("John Doe");
        employee.setSalary(1000L);
        when(employeeService.readEmployee(1L)).thenReturn(employee);
        String content = objectMapper.writeValueAsString(employee);
        // When
        mockMvc.perform(get("/employee/1"))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().string(content));
        verify(employeeService, times(1)).readEmployee(1L);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testReadEmployee_WhenEmployeeDoesntExist() throws Exception {
        // Given
        when(employeeService.readEmployee(1L)).thenReturn(null);
        // When
        mockMvc.perform(get("/employee/1"))
                // Then
                .andExpect(status().isNotFound());
        verify(employeeService, times(1)).readEmployee(1L);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testGetAllEmployeeInfoViews() throws Exception {
        // Given
        EmployeeInfoView employee1 = new EmployeeInfoView();
        employee1.setId(1L);
        employee1.setFullName("John Doe");
        employee1.setDateOfBirth("1980-01-01");
        employee1.setSalary(1000L);
        employee1.setDepartmentName("dep1");
        EmployeeInfoView employee2 = new EmployeeInfoView();
        employee2.setId(2L);
        employee2.setFullName("Jane Doe");
        employee2.setDateOfBirth("1981-01-01");
        employee2.setSalary(2000L);
        employee2.setDepartmentName("dep1");
        List<EmployeeInfoView> employees = Arrays.asList(employee1, employee2);
        when(employeeService.readAllEmployeeInfoViews()).thenReturn(employees);
        String content = objectMapper.writeValueAsString(employees);
        // When
        mockMvc.perform(get("/employee/"))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().string(content));
        verify(employeeService, times(1)).readAllEmployeeInfoViews();
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testCreateEmployee() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setDepartmentId(1L);
        employee.setDateOfBirth(sdf.parse("1980-01-01"));
        employee.setFullName("John Doe");
        employee.setSalary(1000L);
        // When
        mockMvc.perform(post("/employee/")
                .content(objectMapper.writeValueAsString(employee))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isCreated());
        verify(employeeService, times(1)).saveEmployee(employee);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testUpdateEmployee_WhenEmployeeExists() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setDepartmentId(1L);
        employee.setDateOfBirth(sdf.parse("1980-01-01"));
        employee.setFullName("John Doe");
        employee.setSalary(1000L);
        when(employeeService.readEmployee(1L)).thenReturn(employee);
        // When
        mockMvc.perform(put("/employee/")
                .content(objectMapper.writeValueAsString(employee))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk());
        verify(employeeService, times(1)).readEmployee(1L);
        verify(employeeService, times(1)).saveEmployee(employee);
        verifyNoMoreInteractions(employeeService);

    }

    @Test
    public void testUpdateEmployee_WhenEmployeeDoesntExist() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setDepartmentId(1L);
        employee.setDateOfBirth(sdf.parse("1980-01-01"));
        employee.setFullName("John Doe");
        employee.setSalary(1000L);
        when(employeeService.readEmployee(1L)).thenReturn(null);
        // When
        mockMvc.perform(put("/employee/")
                .content(objectMapper.writeValueAsString(employee))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isNotFound());
        verify(employeeService, times(1)).readEmployee(1L);
        verify(employeeService, times(0)).saveEmployee(employee);
        verifyNoMoreInteractions(employeeService);

    }

    @Test
    public void testDeleteEmployeeById_WhenEmployeeExists() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setDepartmentId(1L);
        employee.setDateOfBirth(sdf.parse("1980-01-01"));
        employee.setFullName("John Doe");
        employee.setSalary(1000L);
        when(employeeService.readEmployee(1L)).thenReturn(employee);
        // When
        mockMvc.perform(delete("/employee/1")
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk());
        verify(employeeService, times(1)).readEmployee(1L);
        verify(employeeService, times(1)).deleteEmployee(1L);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testDeleteEmployeeById_WhenEmployeeDoesntExist() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setDepartmentId(1L);
        employee.setDateOfBirth(sdf.parse("1980-01-01"));
        employee.setFullName("John Doe");
        employee.setSalary(1000L);
        when(employeeService.readEmployee(1L)).thenReturn(null);
        // When
        mockMvc.perform(delete("/employee/1")
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isNotFound());
        verify(employeeService, times(1)).readEmployee(1L);
        verify(employeeService, times(0)).deleteEmployee(1L);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testReadEmployeeInfoViewByDepartmentId_WhenThereAreEmployeesInTheDepartment() throws Exception {
        // Given
        EmployeeInfoView employee1 = new EmployeeInfoView();
        employee1.setId(1L);
        employee1.setFullName("John Doe");
        employee1.setDateOfBirth("1980-01-01");
        employee1.setSalary(1000L);
        employee1.setDepartmentName("dep1");
        EmployeeInfoView employee2 = new EmployeeInfoView();
        employee2.setId(2L);
        employee2.setFullName("Jane Doe");
        employee2.setDateOfBirth("1981-01-01");
        employee2.setSalary(2000L);
        employee2.setDepartmentName("dep1");
        List<EmployeeInfoView> employees = Arrays.asList(employee1, employee2);
        when(employeeService.readEmployeeInfoViewsByDepartmentId(1L)).thenReturn(employees);
        String content = objectMapper.writeValueAsString(employees);
        // When
        mockMvc.perform(get("/employee/department/1"))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().string(content));
        verify(employeeService, times(1)).readEmployeeInfoViewsByDepartmentId(1L);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testReadEmployeeInfoViewByDepartmentId_WhenThereAreNotEmployeesInTheDepartment() throws Exception {
        // Given
        when(employeeService.readEmployeeInfoViewsByDepartmentId(1L)).thenReturn(new ArrayList<EmployeeInfoView>());
        // When
        mockMvc.perform(get("/employee/department/1"))
                // Then
                .andExpect(status().isNotFound());
        verify(employeeService, times(1)).readEmployeeInfoViewsByDepartmentId(1L);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testSearchEmployeeByDate() throws Exception {
        // Given
        EmployeeInfoView employee = new EmployeeInfoView();
        employee.setId(1L);
        employee.setFullName("John Doe");
        employee.setDateOfBirth("1980-01-01");
        employee.setSalary(1000L);
        employee.setDepartmentName("dep1");
        List<EmployeeInfoView> employees = Arrays.asList(employee);
        SearchDateDTO query = new SearchDateDTO("1979-01-01", "1981-01-01", null);
        when(employeeService.searchEmployeeByDate(query)).thenReturn(employees);
        // When
        String content = objectMapper.writeValueAsString(employees);
        mockMvc.perform(get("/employee/search")
                .param("startDate", "1979-01-01")
                .param("endDate", "1981-01-01"))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().string(content));
        verify(employeeService, times(1)).searchEmployeeByDate(query);
        verifyNoMoreInteractions(employeeService);

    }

    @Test
    public void testSearchEmployeeByDate_NotFoundAnyEmployees() throws Exception {
        // Given
        SearchDateDTO query = new SearchDateDTO(null, null,"1981-01-01");
        when(employeeService.searchEmployeeByDate(query)).thenReturn(new ArrayList<EmployeeInfoView>());
        // When
        mockMvc.perform(get("/employee/search")
                .param("certainDate", "1981-01-01"))
                // Then
                .andExpect(status().isNotFound());
        verify(employeeService, times(1)).searchEmployeeByDate(query);
        verifyNoMoreInteractions(employeeService);

    }
}