package Models;

public enum UserRoleEnum {
    USER (0),
    EMPLOYEE (1),
    ADMIN (2);

    int role;

    UserRoleEnum(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }
}
