package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.User;

public class UserResponseDto extends ResponseDto {

    private String name;

    public UserResponseDto() {
    }

    public UserResponseDto(User user) {
        this.name = user.getName();
        super.setId(user.getId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
