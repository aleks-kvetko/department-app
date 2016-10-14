package by.alekskvetko.client.model.views;

/**
 * View entity for an department with additional field - averageSalary
 *
 *      id - id of a department
 *      name - name of a department
 *      averageSalary - average salary of a department
 *
 * @author ALEKSANDR KVETKO
 */

public class SalaryView {

    private Long id;
    private String departmentName;
    private Long averageSalary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(Long averageSalary) {
        this.averageSalary = averageSalary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalaryView that = (SalaryView) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (departmentName != null ? !departmentName.equals(that.departmentName) : that.departmentName != null)
            return false;
        return averageSalary != null ? averageSalary.equals(that.averageSalary) : that.averageSalary == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (departmentName != null ? departmentName.hashCode() : 0);
        result = 31 * result + (averageSalary != null ? averageSalary.hashCode() : 0);
        return result;
    }
}
