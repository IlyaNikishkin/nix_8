package ua.com.alevel.view.dto.request;

public class GroupRequestDto extends RequestDto {

    private String name;
    private Integer grade;

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
