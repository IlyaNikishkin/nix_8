package ua.com.alevel.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups_table")
public class Group extends BaseEntity {

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private List<Student> students;

    public Group() {
        super();
        this.students = new ArrayList<>();
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
}