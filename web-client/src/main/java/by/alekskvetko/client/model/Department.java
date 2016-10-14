package by.alekskvetko.client.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * An entity class for a department
 *
 *      id - id of a department
 *      name - name of a department
 *
 * @author ALEKSANDR KVETKO
 */
public class Department {

    private Long id;

    @NotNull(message = "{NotNull.department.name}")
    @Length(min=2, max=35, message = "{Length.department.name}")
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //rewrite equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(getClass() == obj.getClass()))
            return false;
        else {
            Department tmp = (Department) obj;
            if (tmp.getId().equals(this.id) && tmp.getName().equals(this.name))
                return true;
            else
                return false;
        }
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


}
