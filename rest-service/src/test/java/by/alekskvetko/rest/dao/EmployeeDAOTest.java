package by.alekskvetko.rest.dao;

import by.alekskvetko.rest.model.Employee;
import by.alekskvetko.rest.model.SearchDateDTO;
import by.alekskvetko.rest.model.views.EmployeeInfoView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EmployeeDAOTest {

    @Autowired
    private EmployeeDAO employeeDAO;

    private SimpleDateFormat sdf;

    @Before
    public void setUp() throws Exception {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    @Rollback(value = true)
    public void testCreateEmployee() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setFullName("John Doe");
        employee.setDateOfBirth(sdf.parse("1980-01-01"));
        employee.setSalary(1000L);
        // When
        employeeDAO.createEmployee(employee);
        // Then
        assertEquals(8, employeeDAO.readAllEmployeeInfoViews().size());
    }

    @Test
    @Rollback(value = true)
    public void testReadEmployee() throws Exception {
        // When
        Employee employee = employeeDAO.readEmployee(1L);
        // Then
        assertThat(employee.getId(), is(1L));
        assertThat(employee.getFullName(), is("Mark Selby"));
        assertThat(employee.getDateOfBirth(), is(sdf.parse("1983-06-19")));
        assertThat(employee.getSalary(), is(7000L));
        assertThat(employee.getDepartmentId(), is(1L));

    }

    @Test
    @Rollback(value = true)
    public void testUpdateEmployee() throws Exception {
        // Given
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFullName("John Doe");
        employee.setDateOfBirth(sdf.parse("1980-01-01"));
        employee.setSalary(1000L);
        employee.setDepartmentId(2L);
        // When
        employeeDAO.updateEmployee(employee);
        Employee actual = employeeDAO.readEmployee(1L);
        // Then
        assertEquals(actual, employee);


    }

    @Test
    @Rollback(value = true)
    public void testDeleteEmployee() throws Exception {
        // When
        employeeDAO.deleteEmployee(1L);
        // Then
        assertEquals(6, employeeDAO.readAllEmployeeInfoViews().size());

    }

    @Test
    @Rollback(value = true)
    public void testReadAllEmployeeInfoViews() throws Exception {
        assertEquals(7, employeeDAO.readAllEmployeeInfoViews().size());
    }

    @Test
    @Rollback(value = true)
    public void testReadEmployeeInfoViewsByDepartmentId() throws Exception {
        assertEquals(3, employeeDAO.readEmployeeInfoViewsByDepartmentId(1L).size());
        assertEquals(4, employeeDAO.readEmployeeInfoViewsByDepartmentId(2L).size());

    }

    @Test
    @Rollback(value = true)
    public void testSearchEmployeeByDate_CertainDate() throws Exception {
        // Given
        SearchDateDTO searchQuery = new SearchDateDTO(null, null, "1977-09-11");
        // When
        List<EmployeeInfoView> employees = employeeDAO.searchEmployeeByDate(searchQuery);
        // Then
        assertEquals(searchQuery.getCertainDate(), employees.get(0).getDateOfBirth());

    }

    @Test
    @Rollback(value = true)
    public void testSearchEmployeeByDate_StartDate() throws Exception {
        // Given
        SearchDateDTO searchQuery = new SearchDateDTO("1982-11-11", null, null);
        // When
        List<EmployeeInfoView> employees = employeeDAO.searchEmployeeByDate(searchQuery);
        // Then
        Date queryFrom, result;
        queryFrom = sdf.parse(searchQuery.getStartDate());
        result = sdf.parse(employees.get(0).getDateOfBirth());
        assertTrue(result.after(queryFrom) || result.equals(queryFrom));
        result = sdf.parse(employees.get(1).getDateOfBirth());
        assertTrue(result.after(queryFrom) || result.equals(queryFrom));
    }

    @Test
    @Rollback(value = true)
    public void testSearchEmployeeByDate_EndDate() throws Exception {
        // Given
        SearchDateDTO searchQuery = new SearchDateDTO(null, "1978-01-01", null);
        // When
        List<EmployeeInfoView> employees = employeeDAO.searchEmployeeByDate(searchQuery);
        // Then
        Date queryTo, result;
        queryTo = sdf.parse(searchQuery.getEndDate());
        result = sdf.parse(employees.get(0).getDateOfBirth());
        assertTrue(result.before(queryTo) || result.equals(queryTo));
        result = sdf.parse(employees.get(1).getDateOfBirth());
        assertTrue(result.before(queryTo) || result.equals(queryTo));
    }

    @Test
    @Rollback(value = true)
    public void testSearchEmployeeByDate_StartDate_And_EndDate() throws Exception {
        // Given
        SearchDateDTO searchQuery = new SearchDateDTO("1981-01-01", "1983-01-01", null);
        // When
        List<EmployeeInfoView> employees = employeeDAO.searchEmployeeByDate(searchQuery);
        // Then
        Date queryFrom, queryTo, result;
        queryFrom = sdf.parse(searchQuery.getStartDate());
        queryTo = sdf.parse(searchQuery.getEndDate());
        result = sdf.parse(employees.get(0).getDateOfBirth());
        assertTrue(result.after(queryFrom) || result.equals(queryFrom));
        assertTrue(result.before(queryTo) || result.equals(queryTo));
        result = sdf.parse(employees.get(1).getDateOfBirth());
        assertTrue(result.after(queryFrom) || result.equals(queryFrom));
        assertTrue(result.before(queryTo) || result.equals(queryTo));
    }
}