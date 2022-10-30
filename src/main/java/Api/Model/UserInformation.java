package Api.Model;

import lombok.Data;

@Data
public class UserInformation {
    private String email;
    private String name;
    private String password;

    public UserInformation(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
    public static UserInformation from(User user) {
        return new UserInformation(user.getEmail(), user.getName(), user.getPassword());
    }
}
