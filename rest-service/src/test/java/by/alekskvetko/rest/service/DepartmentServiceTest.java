package by.alekskvetko.rest.service;

import by.alekskvetko.rest.dao.DepartmentDAO;
import by.alekskvetko.rest.model.Department;
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
public class DepartmentServiceTest {

    @Mock
    private DepartmentDAO departmentDAO;
    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }


    @Test
    public void testCreateDepartment() throws Exception {
        // Given
        Department department = new Department();
        // When
        departmentService.saveDepartment(department);
        // Then
        verify(departmentDAO, times(1)).createDepartment(department);
        verify(departmentDAO, times(0)).updateDepartment(department);
        verifyNoMoreInteractions(departmentDAO);

    }

    @Test
    public void testUpdateDepartment() throws Exception {
        // Given
        Department department = new Department();
        department.setId(1L);
        // When
        departmentService.saveDepartment(department);
        // Then
        verify(departmentDAO, times(0)).createDepartment(department);
        verify(departmentDAO, times(1)).updateDepartment(department);
        verifyNoMoreInteractions(departmentDAO);

    }

    @Test
    public void testReadDepartment() throws Exception {
        // When
        departmentService.readDepartment(1L);
        // Then
        verify(departmentDAO, times(1)).readDepartment(1L);
        verifyNoMoreInteractions(departmentDAO);
    }


    @Test
    public void testDeleteDepartment() throws Exception {
        // When
        departmentService.deleteDepartment(1L);
        // Then
        verify(departmentDAO, times(1)).deleteDepartment(1L);
        verifyNoMoreInteractions(departmentDAO);
    }

    @Test
    public void testReadAllDepartments() throws Exception {
        // When
        departmentService.readAllDepartments();
        // Then
        verify(departmentDAO, times(1)).readAllDepartments();
        verifyNoMoreInteractions(departmentDAO);
    }

    @Test
    public void testReadAverageSalary() throws Exception {
        // When
        departmentService.readAverageSalary();
        // Then
        verify(departmentDAO, times(1)).readAverageSalary();
        verifyNoMoreInteractions(departmentDAO);
    }
}