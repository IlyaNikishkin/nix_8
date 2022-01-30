package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Group;
import ua.com.alevel.persistence.entity.Student;

public class StudentResponseDto extends ResponseDto {

    private String name;
    private Integer age;
    private Group group;

    public StudentResponseDto() {
    }

    public StudentResponseDto(Student student) {
        this.age = student.getAge();
        this.name = student.getName();
        if (student.getGroup() != null) {
            this.group = student.getGroup();
        }
        super.setId(student.getId());
        super.setCreated(student.getCreated());
        super.setUpdated(student.getUpdated());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
