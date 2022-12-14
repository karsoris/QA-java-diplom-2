package Api.Model;

import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    public static User getRandomUser() {
            return new User(
            RandomStringUtils.randomAlphabetic(10) + "@test.com",
            "P@ssw0rd",
            RandomStringUtils.randomAlphabetic(10)
    );
}
    public static User getRandomUserWithoutAnyField(String email, String password, String name) {
        return new User(
                email,
                password,
                name
        );
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
