package by.alekskvetko.client.web;

import by.alekskvetko.client.model.Department;
import by.alekskvetko.client.model.views.SalaryView;
import by.alekskvetko.client.service.DepartmentService;
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
public class DepartmentControllerTest {

    private MockMvc mockMvc;


    private InternalResourceViewResolver viewResolver;
    private ObjectMapper objectMapper;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
                .setViewResolvers(viewResolver)
                .build();
        objectMapper = new ObjectMapper();

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
        when(departmentService.readAllDepartments()).thenReturn(Arrays.asList(department1, department2));
        // When
        mockMvc.perform(get("/departments/"))
                // Then
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/departments.jsp"))
                .andExpect(view().name("departments"))
                .andExpect(model().attribute("departments", hasSize(2)))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("name", is("foo"))
                        )
                )))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("name", is("bar"))
                        )
                )));
        verify(departmentService, times(1)).readAllDepartments();
        verifyNoMoreInteractions(departmentService);


    }

    @Test
    public void testDeleteDepartmentById() throws Exception {
        // When
        mockMvc.perform(post("/departments/delete/1"))
                // Then
                .andExpect(redirectedUrl("/departments"));
        verify(departmentService, times(1)).deleteDepartment(1L);
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    public void testCreateDepartment() throws Exception {
        // When
        mockMvc.perform(get("/departments/add"))
                // Then
                .andExpect(view().name("departmentForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/departmentForm.jsp"))
                .andExpect(model().attribute("department", notNullValue()));
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    public void testSaveDepartment() throws Exception {
        // Given
        Department department = new Department();
        department.setId(1L);
        department.setName("foo");
        // When
        mockMvc.perform(post("/departments/")
                .param("id","1")
                .param("name","foo"))
                // Then
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/departments"));
        verify(departmentService, times(1)).saveDepartment(department);
        verifyNoMoreInteractions(departmentService);

    }

    @Test
    public void testSaveDepartment_() throws Exception {
        // Given
        Department department = new Department();
        department.setId(1L);
        department.setName("f");
        // When
        mockMvc.perform(post("/departments/")
                .param("id","1")
                .param("name","f"))
                // Then
                .andExpect(forwardedUrl("/WEB-INF/jsp/departmentForm.jsp"))
                .andExpect(view().name("departmentForm"))
                .andExpect(model().hasErrors());
        verify(departmentService, times(0)).saveDepartment(department);
        verifyNoMoreInteractions(departmentService);

    }

    @Test
    public void testEditDepartment() throws Exception {
        // Given
        Department department = new Department();
        department.setId(1L);
        department.setName("foo");
        when(departmentService.readDepartment(1L)).thenReturn(department);
        // When
        mockMvc.perform(get("/departments/edit/1"))
                // Then
                .andExpect(view().name("departmentForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/departmentForm.jsp"))
                .andExpect(model().attribute("department", hasProperty("id", is(department.getId())) ))
                .andExpect(model().attribute("department", hasProperty("name", is(department.getName()))));
        verify(departmentService, times(1)).readDepartment(1L);
        verifyNoMoreInteractions(departmentService);

    }

    @Test
    public void testGetAverageSalary() throws Exception {
        // Given
        SalaryView salaryView1=new SalaryView();
        salaryView1.setId(1L);
        salaryView1.setDepartmentName("Dep1");
        salaryView1.setAverageSalary(1000L);
        SalaryView salaryView2=new SalaryView();
        salaryView2.setId(1L);
        salaryView2.setDepartmentName("Dep2");
        salaryView2.setAverageSalary(8000L);
        List<SalaryView> salaryViews=Arrays.asList(salaryView1,salaryView2);
        when(departmentService.readAverageSalary()).thenReturn(salaryViews);
        // When
        mockMvc.perform(get("/departments/salary"))
                // Then
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/salary.jsp"))
                .andExpect(view().name("salary"))
                .andExpect(model().attribute("salaryView", hasSize(2)))
                .andExpect(model().attribute("salaryView", hasItem(
                        allOf(
                                hasProperty("id", is(salaryView1.getId())),
                                hasProperty("departmentName", is(salaryView1.getDepartmentName())),
                                hasProperty("averageSalary", is(salaryView1.getAverageSalary()))
                        )
                )))
                .andExpect(model().attribute("salaryView", hasItem(
                        allOf(
                                hasProperty("id", is(salaryView2.getId())),
                                hasProperty("departmentName", is(salaryView2.getDepartmentName())),
                                hasProperty("averageSalary", is(salaryView2.getAverageSalary()))
                        )
                )));
        verify(departmentService, times(1)).readAverageSalary();
        verifyNoMoreInteractions(departmentService);


    }
}