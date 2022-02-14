package ua.com.alevel.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "groups_table")
public class Group extends BaseEntity {

    private String name;

    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER, mappedBy = "group")
    private List<Student> students;

    public Group() {
        super();
        this.students = Collections.emptyList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Group group = (Group) o;

        if (name != group.name) return false;
        return Objects.equals(students, group.students);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (students != null ? students.hashCode() : 0);
        return result;
    }
}