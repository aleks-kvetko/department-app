package by.alekskvetko.rest.web;

import by.alekskvetko.rest.model.Department;
import by.alekskvetko.rest.model.views.SalaryView;
import by.alekskvetko.rest.service.DepartmentService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class DepartmentControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Mock
    private DepartmentService departmentService;
    @InjectMocks
    private DepartmentController departmentController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
        objectMapper = new ObjectMapper();

    }
    @Test
    public void testReadDepartment_WhenDepartmentExists() throws Exception {
        // Given
        Department department = new Department();
        department.setId(1L);
        department.setName("foo");
        when(departmentService.readDepartment(1L)).thenReturn(department);
        String content = objectMapper.writeValueAsString(department);
        // When
        mockMvc.perform(get("/department/1"))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().string(content));
        verify(departmentService, times(1)).readDepartment(1L);
        verifyNoMoreInteractions(departmentService);

    }

    @Test
    public void testReadDepartment_WhenDepartmentDoesntExist() throws Exception {
        // Given
        when(departmentService.readDepartment(1L)).thenReturn(null);
        // When
        mockMvc.perform(get("/department/1"))
                // Then
                .andExpect(status().isNotFound());
        verify(departmentService, times(1)).readDepartment(1L);
        verifyNoMoreInteractions(departmentService);

    }

    @Test
    public void testGetAllDepartments() throws Exception {
        // Given
        Department department1 = new Department();
        department1.setId(1L);
        department1.setName("foo");
        Department department2 = new Department();
        department2.setId(2L);
        department2.setName("bar");
        List<Department> departments = Arrays.asList(department1, department2);
        when(departmentService.readAllDepartments()).thenReturn(departments);
        String content = objectMapper.writeValueAsString(departments);
        // When
        mockMvc.perform(get("/department/"))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().string(content));

        verify(departmentService, times(1)).readAllDepartments();
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    public void testCreateDepartment() throws Exception {
        // Given
        Department department = new Department();
        department.setName("foo");
        // When
        mockMvc.perform(post("/department/")
                .content(objectMapper.writeValueAsString(department))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isCreated());
        verify(departmentService, times(1)).saveDepartment(department);
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    public void testUpdateDepartmentById_WhenDepartmentDoesntExist() throws Exception {
        // Given
        Department department = new Department();
        department.setId(1L);
        department.setName("foo");
        when(departmentService.readDepartment(1L)).thenReturn(null);
        // When
        mockMvc.perform(put("/department/")
                .content(objectMapper.writeValueAsString(department))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isNotFound());
        verify(departmentService, times(1)).readDepartment(1L);
        verify(departmentService, times(0)).saveDepartment(department);
        verifyNoMoreInteractions(departmentService);

    }

    @Test
    public void testUpdateDepartmentById_WhenDepartmentExists() throws Exception {
        // Given
        Department department = new Department();
        department.setId(1L);
        department.setName("foo");
        when(departmentService.readDepartment(1L)).thenReturn(department);
        // When
        mockMvc.perform(put("/department/")
                .content(objectMapper.writeValueAsString(department))
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk());
        verify(departmentService, times(1)).readDepartment(1L);
        verify(departmentService, times(1)).saveDepartment(department);
        verifyNoMoreInteractions(departmentService);

    }

    @Test
    public void testDeleteDepartmentById_WhenDepartmentDoesntExist() throws Exception {
        // Given
        when(departmentService.readDepartment(1L)).thenReturn(null);
        // When
        mockMvc.perform(delete("/department/1")
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isNotFound());
        verify(departmentService, times(1)).readDepartment(1L);
        verify(departmentService, times(0)).deleteDepartment(1L);
        verifyNoMoreInteractions(departmentService);

    }

    @Test
    public void testDeleteDepartmentById_WhenDepartmentExists() throws Exception {
        // Given
        Department department = new Department();
        department.setId(1L);
        department.setName("foo");
        when(departmentService.readDepartment(1L)).thenReturn(department);
        // When
        mockMvc.perform(delete("/department/1")
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk());
        verify(departmentService, times(1)).readDepartment(1L);
        verify(departmentService, times(1)).deleteDepartment(1L);
        verifyNoMoreInteractions(departmentService);

    }

    @Test
    public void testReadAverageSalary() throws Exception {
        // Given
        SalaryView salaryView1=new SalaryView();
        salaryView1.setId(1L);
        salaryView1.setDepartmentName("Dep1");
        salaryView1.setAverageSalary(1000L);
        SalaryView salaryView2=new SalaryView();
        salaryView1.setId(2L);
        salaryView1.setDepartmentName("Dep2");
        salaryView1.setAverageSalary(4000L);
        List<SalaryView> salaryViews=Arrays.asList(salaryView1,salaryView2);
        when(departmentService.readAverageSalary()).thenReturn(salaryViews);
        String content = objectMapper.writeValueAsString(salaryViews);
        // When
        mockMvc.perform(get("/department/average"))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().string(content));
        verify(departmentService, times(1)).readAverageSalary();
        verifyNoMoreInteractions(departmentService);

    }
}