package by.alekskvetko.rest.service;

import by.alekskvetko.rest.dao.EmployeeDAO;
import by.alekskvetko.rest.model.Employee;
import by.alekskvetko.rest.model.SearchDateDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeDAO employeeDAO;
    @InjectMocks
    private EmployeeServiceImpl employeeService;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testCreateEmployee() throws Exception {
        // Given
        Employee employee = new Employee();
        // When
        employeeService.saveEmployee(employee);
        // Then
        verify(employeeDAO, times(1)).createEmployee(employee);
        verify(employeeDAO, times(0)).updateEmployee(employee);
        verifyNoMoreInteractions(employeeDAO);
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setId(1L);
        // When
        employeeService.saveEmployee(employee);
        // Then
        verify(employeeDAO, times(0)).createEmployee(employee);
        verify(employeeDAO, times(1)).updateEmployee(employee);
        verifyNoMoreInteractions(employeeDAO);
    }

    @Test
    public void testReadEmployee() throws Exception {
        // When
        employeeService.readEmployee(1L);
        // Then
        verify(employeeDAO, times(1)).readEmployee(1L);
        verifyNoMoreInteractions(employeeDAO);
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        // When
        employeeService.deleteEmployee(1L);
        // Then
        verify(employeeDAO, times(1)).deleteEmployee(1L);
        verifyNoMoreInteractions(employeeDAO);
    }

    @Test
    public void testReadAllEmployeeInfoViews() throws Exception {
        // When
        employeeService.readAllEmployeeInfoViews();
        // Then
        verify(employeeDAO, times(1)).readAllEmployeeInfoViews();
        verifyNoMoreInteractions(employeeDAO);
    }

    @Test
    public void testReadEmployeeInfoViewsByDepartmentId() throws Exception {
        // When
        employeeService.readEmployeeInfoViewsByDepartmentId(1L);
        // Then
        verify(employeeDAO, times(1)).readEmployeeInfoViewsByDepartmentId(1L);
        verifyNoMoreInteractions(employeeDAO);
    }

    @Test
    public void testSearchEmployeeByDate() throws Exception {
        // Given
        SearchDateDTO searchDateDTO=new SearchDateDTO("1980-01-01","1981-01-01",null);
        // When
        employeeService.searchEmployeeByDate(searchDateDTO);
        // Then
        verify(employeeDAO, times(1)).searchEmployeeByDate(searchDateDTO);
        verifyNoMoreInteractions(employeeDAO);
    }
}