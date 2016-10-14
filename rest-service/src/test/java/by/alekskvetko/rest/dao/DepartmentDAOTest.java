package by.alekskvetko.rest.dao;

import by.alekskvetko.rest.model.Department;
import by.alekskvetko.rest.model.views.SalaryView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional

public class DepartmentDAOTest {

    @Autowired
    private DepartmentDAO departmentDao;

    @Test
    @Rollback(value = true)
    public void testReadAllDepartments() {
        assertEquals(2, departmentDao.readAllDepartments().size());
    }

    @Test
    @Rollback(value = true)
    public void testCreateDepartment() {
        // Given
        Department department = new Department();
        department.setName("Test Department");
        // When
        departmentDao.createDepartment(department);
        // Then
        assertEquals(3, departmentDao.readAllDepartments().size());
    }


    @Test
    @Rollback(value = true)
    public void testReadDepartment() {
        // When
        Department department = departmentDao.readDepartment(2L);
        // Then
        assertThat(department.getName(), is("Users"));
        assertThat(department.getId(), is(2L));
    }

    @Test
    @Rollback(value = true)
    public void testUpdateDepartment() {
        // Given
        Department department = new Department();
        department.setId(2L);
        department.setName("CEO");
        // When
        departmentDao.updateDepartment(department);
        Department updated = departmentDao.readDepartment(2L);
        // Then
        assertEquals(department, updated);

    }

    @Test
    @Rollback(value = true)
    public void testDeleteDepartment() {
        // When
        departmentDao.deleteDepartment(2L);
        // Then
        assertEquals(1, departmentDao.readAllDepartments().size());

    }

    @Test
    @Rollback(value = true)
    public void testReadAverageSalary() {
        // When
        List<SalaryView> salaryViews = departmentDao.readAverageSalary();
        // Then
        assertEquals(2, salaryViews.size());
        assertThat(salaryViews.get(0).getAverageSalary(), is(6000L));
        assertThat(salaryViews.get(1).getAverageSalary(), is(3500L));


    }


}