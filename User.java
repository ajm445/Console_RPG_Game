import java.util.Scanner;

public class User {
    public static UserManager manager = new UserManager();
    public static User currentUser;
    static int cnt = 0;
    public static boolean login = false;
    private String id;
    private String pw;

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
        for(User player : manager.Users()) {
            if(player != null && player.getId().equals(id)) {
                System.out.println("이미 존재하는 아이디 입니다.");
                Main.mainMenu();
                return;
            }
        }
        System.out.print("비밀번호를 설정해주세요 : ");
        String pw = in.nextLine();
        User user = new User(id, pw);
        manager.addUser(user);
        System.out.println("계정 생성 완료!");
    }

    public static void signIn() {
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.print("로그인할 플레이어 아이디를 입력해주세요 : ");
            String id = in.nextLine();
            System.out.print("비밀번호를 입력해주세요 : ");
            String pw = in.nextLine();
            for(User user : manager.Users()) {
                if(user != null && user.getId().equals(id)) {  // 플레이어가 null 아니고 가져오는 이름과 같으면 성립
                    if(user.getPw().equals(pw)) {  // 플레이어의 비밀번호가 가져오는 비밀번호와 같으면 성립
                        currentUser = user;
                        System.out.println(id + " 계정 로그인 성공!");
                        User.login = true;
                        Main.mainMenu();
                        break;
                    }
                }
            } System.out.println("로그인 실패!!"); Main.mainMenu(); return;
        }
    }

    public static void modifyId() {  // 아이디 변경하기
        Scanner in = new Scanner(System.in);
        System.out.print("바꿀 아이디를 입력하세요 : ");
        String id = in.nextLine();
        System.out.print("비밀번호를 입력하세요 : ");
        String pw = in.nextLine();
        for(User user : manager.Users()) {
            if(user != null && user.getId().equals(id)) {
                System.out.println("이미 존재하는 아이디 입니다.");
                Main.mainMenu();
                return;
            }
            if(user != null && user.getPw().equals(pw)) {
                currentUser.setId(id);
                System.out.println("아이디가 성공적으로 변경되었습니다.");
                Main.mainMenu();
            } else {
                System.out.println("비밀번호가 맞지 않습니다.");
                Main.mainMenu();
                return;
            }
        }
    }
}
