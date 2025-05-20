import java.util.Scanner;

public class User {
    private static userManager manager = new userManager();
    String id;
    String pw;

    // 생성자
    public User(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    // getter, setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    // method
    public void printInfo() {
        System.out.println("ID : " + id + " | PW: " + pw);
    }

    public static void signUp() {
        Scanner in = new Scanner(System.in);

        System.out.print("생성할 플레이어 아이디를 입력해주세요 : ");
        String id = in.nextLine();

        System.out.print("비밀번호를 설정해주세요 : ");
        String pw = in.nextLine();

        User user = new User(id, pw);
        manager.addUser(user);

        manager.showAllUsers();
    }
}
