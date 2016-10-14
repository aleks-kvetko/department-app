package by.alekskvetko.client.web;

import by.alekskvetko.client.model.Department;
import by.alekskvetko.client.model.Employee;
import by.alekskvetko.client.model.SearchDateDTO;
import by.alekskvetko.client.model.views.EmployeeInfoView;
import by.alekskvetko.client.service.DepartmentService;
import by.alekskvetko.client.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context-test.xml")
@WebAppConfiguration
public class EmployeeControllerTest {

    private MockMvc mockMvc;
    private InternalResourceViewResolver viewResolver;
    private ObjectMapper objectMapper;
    private SimpleDateFormat sdf;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private EmployeeController employeeController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setViewResolvers(viewResolver)
                .build();
        objectMapper = new ObjectMapper();
        sdf=new SimpleDateFormat("dd-MM-yyyy");

    }

    @Test
    public void testGetAllEmployees() throws Exception {
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
        when(employeeService.readAllEmployeeInfoViews()).thenReturn(employees);
        // When
        mockMvc.perform(get("/employees/"))
                // Then
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/employees.jsp"))
                .andExpect(view().name("employees"))
                .andExpect(model().attribute("searchDateDTO",notNullValue()))
                .andExpect(model().attribute("employees", hasSize(2)))
                .andExpect(model().attribute("employees", hasItem(
                        allOf(
                                hasProperty("id", is(employee1.getId())),
                                hasProperty("fullName", is(employee1.getFullName())),
                                hasProperty("dateOfBirth", is(employee1.getDateOfBirth())),
                                hasProperty("salary", is(employee1.getSalary())),
                                hasProperty("departmentName", is(employee1.getDepartmentName()))
                        )
                )))
                .andExpect(model().attribute("employees", hasItem(
                        allOf(
                                hasProperty("id", is(employee2.getId())),
                                hasProperty("fullName", is(employee2.getFullName())),
                                hasProperty("dateOfBirth", is(employee2.getDateOfBirth())),
                                hasProperty("salary", is(employee2.getSalary())),
                                hasProperty("departmentName", is(employee2.getDepartmentName()))
                        )
                )));
        verify(employeeService, times(1)).readAllEmployeeInfoViews();
        verifyNoMoreInteractions(employeeService);

    }

    @Test
    public void testCreateEmployee() throws Exception {
        // Given
        Department department1 = new Department();
        department1.setId(1L);
        department1.setName("dep1");
        Department department2 = new Department();
        department2.setId(2L);
        department2.setName("dep2");
        when(departmentService.readAllDepartments()).thenReturn(Arrays.asList(department1, department2));
        // When
        mockMvc.perform(get("/employees/add"))
                // Then
                .andExpect(view().name("employeeForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/employeeForm.jsp"))
                .andExpect(model().attribute("departments", hasSize(2)))
                .andExpect(model().attribute("employee", notNullValue()));
        verify(departmentService,times(1)).readAllDepartments();
        verifyNoMoreInteractions(departmentService);
        verifyNoMoreInteractions(employeeService);

    }

    @Test
    public void testSaveEmployee() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFullName("John Doe");
        employee.setDateOfBirth(sdf.parse("01-01-1981"));
        employee.setSalary(1000L);
        employee.setDepartmentId(1L);
        // When
        mockMvc.perform(post("/employees/")
                .param("id","1")
                .param("fullName","John Doe")
                .param("dateOfBirth","01-01-1981")
                .param("salary","1000")
                .param("departmentId","1"))
                // Then
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/employees"));
        verify(employeeService, times(1)).saveEmployee(employee);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testSaveEmployee_() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFullName("Jo");
        employee.setDateOfBirth(sdf.parse("51-91-1981"));
        employee.setSalary(-1000L);
        employee.setDepartmentId(1L);
        // When
        mockMvc.perform(post("/employees/")
                .param("id","1")
                .param("fullName","Jo")
                .param("dateOfBirth","51-91-1981")
                .param("salary","-1000")
                .param("departmentId","1"))
                // Then
                .andExpect(model().hasErrors())
                .andExpect(forwardedUrl("/WEB-INF/jsp/employeeForm.jsp"))
                .andExpect(view().name("employeeForm"))
                .andExpect(model().hasErrors());
        verify(employeeService, times(0)).saveEmployee(employee);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testEditEmployee() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFullName("John Doe");
        employee.setDateOfBirth(sdf.parse("01-01-1981"));
        employee.setSalary(1000L);
        employee.setDepartmentId(1L);
        when(employeeService.readEmployee(1L)).thenReturn(employee);
        // When
        mockMvc.perform(get("/employees/edit/1"))
                // Then
                .andExpect(view().name("employeeForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/employeeForm.jsp"))
                .andExpect(model().attribute("employee", hasProperty("id", is(employee.getId()))))
                .andExpect(model().attribute("employee", hasProperty("fullName", is(employee.getFullName()))))
                .andExpect(model().attribute("employee", hasProperty("dateOfBirth", is(employee.getDateOfBirth()))))
                .andExpect(model().attribute("employee", hasProperty("salary", is(employee.getSalary()))))
                .andExpect(model().attribute("employee", hasProperty("departmentId", is(employee.getDepartmentId()))));
        verify(employeeService, times(1)).readEmployee(1L);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        // When
        mockMvc.perform(post("/employees/delete/1"))
                // Then
                .andExpect(redirectedUrl("/employees"));
        verify(employeeService, times(1)).deleteEmployee(1L);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testReadEmployeesByDepartmentId() throws Exception {
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
        Department department=new Department();
        department.setId(1L);
        department.setName("dep1");
        when(employeeService.readEmployeeInfoViewsByDepartmentId(1L)).thenReturn(employees);
        when(departmentService.readDepartment(1L)).thenReturn(department);
        // When
        mockMvc.perform(get("/employees/department/1"))
                // Then
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/employeesByDep.jsp"))
                .andExpect(view().name("employeesByDep"))
                .andExpect(model().attribute("department", hasProperty("id", is(department.getId()))))
                .andExpect(model().attribute("department", hasProperty("name", is(department.getName()))))
                .andExpect(model().attribute("employees", hasSize(2)))
                .andExpect(model().attribute("employees", hasItem(
                        allOf(
                                hasProperty("id", is(employee1.getId())),
                                hasProperty("fullName", is(employee1.getFullName())),
                                hasProperty("dateOfBirth", is(employee1.getDateOfBirth())),
                                hasProperty("salary", is(employee1.getSalary())),
                                hasProperty("departmentName", is(employee1.getDepartmentName()))
                        )
                )))
                .andExpect(model().attribute("employees", hasItem(
                        allOf(
                                hasProperty("id", is(employee2.getId())),
                                hasProperty("fullName", is(employee2.getFullName())),
                                hasProperty("dateOfBirth", is(employee2.getDateOfBirth())),
                                hasProperty("salary", is(employee2.getSalary())),
                                hasProperty("departmentName", is(employee2.getDepartmentName()))
                        )
                )));
        verify(employeeService, times(1)).readEmployeeInfoViewsByDepartmentId(1L);
        verify(departmentService,times(1)).readDepartment(1L);
        verifyNoMoreInteractions(employeeService);
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    public void testSearchEmployee() throws Exception {
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
        SearchDateDTO query=new SearchDateDTO("01-01-1980","01-01-1981",null);
        when(employeeService.searchEmployeeByDate(query)).thenReturn(employees);
        // When
        mockMvc.perform(post("/employees/search")
                .param("startDate",query.getStartDate())
                .param("endDate",query.getEndDate()))
                // Then
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/employees.jsp"))
                .andExpect(view().name("employees"))
                .andExpect(model().attribute("employees", hasSize(2)))
                .andExpect(model().attribute("employees", hasItem(
                        allOf(
                                hasProperty("id", is(employee1.getId())),
                                hasProperty("fullName", is(employee1.getFullName())),
                                hasProperty("dateOfBirth", is(employee1.getDateOfBirth())),
                                hasProperty("salary", is(employee1.getSalary())),
                                hasProperty("departmentName", is(employee1.getDepartmentName()))
                        )
                )))
                .andExpect(model().attribute("employees", hasItem(
                        allOf(
                                hasProperty("id", is(employee2.getId())),
                                hasProperty("fullName", is(employee2.getFullName())),
                                hasProperty("dateOfBirth", is(employee2.getDateOfBirth())),
                                hasProperty("salary", is(employee2.getSalary())),
                                hasProperty("departmentName", is(employee2.getDepartmentName()))
                        )
                )));
        verify(employeeService, times(1)).searchEmployeeByDate(query);
        verifyNoMoreInteractions(employeeService);

    }
}