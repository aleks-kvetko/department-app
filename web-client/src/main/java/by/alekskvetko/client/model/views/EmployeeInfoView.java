package by.alekskvetko.client.model.views;

import java.util.Date;

/**
 * View entity for an employee with field departmentName(String) instead of departmentId(Long)
 *
 *      id - id of an employee
 *      fullName - full name of an employee
 *      dateOfBirth - date of birth of an employee
 *      salary - salary of an employee
 *      departmentName - name of an department
 *
 * @author ALEKSANDR KVETKO
 */
public class EmployeeInfoView {

    private Long id;
    private String fullName;
    private Date dateOfBirth;
    private Long salary;
    private String departmentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeInfoView that = (EmployeeInfoView) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null) return false;
        if (salary != null ? !salary.equals(that.salary) : that.salary != null) return false;
        return departmentName != null ? departmentName.equals(that.departmentName) : that.departmentName == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + (departmentName != null ? departmentName.hashCode() : 0);
        return result;
    }
}
