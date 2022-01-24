package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Group;

public class GroupResponseDto extends ResponseDto {

    private String name;
    private Integer grade;

    public GroupResponseDto() {
    }

    public GroupResponseDto(Group group) {
        this.name = group.getName();
        this.grade = group.getGrade();
        super.setId(group.getId());
        super.setCreated(group.getCreated());
        super.setUpdated(group.getUpdated());
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
