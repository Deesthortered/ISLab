package Utility;

public enum UserRole {
    Admin(0), ViewManager(1), ImportManager(2), ExportManager(3);

    private final int value;

    UserRole(int i) {
        this.value = i;
    }

    @Override
    public String toString() {
        String res = null;
        switch (value) {
            case 0: res = "Admin";         break;
            case 1: res = "ViewManager";   break;
            case 2: res = "ImportManager"; break;
            case 3: res = "ExportManager"; break;
        }
        return res;
    }
}
