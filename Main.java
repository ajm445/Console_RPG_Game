import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);
    public static void mainMenu() {
        while(true) {
            if(!User.login) {
                System.out.print("""
                ***********************************
                          Console RPG Game
                ***********************************
                            0. 게임종료
                            1. 회원가입
                             2. 로그인
                           3. 아이디 변경
                ***********************************
                Enter number
                """);
            }
            else {
                System.out.print("""
                ***********************************
                          Console RPG Game
                ***********************************
                            0. 게임종료
                            1. 게임시작
                           2. 마이페이지
                            3. 로그아웃
                ***********************************
                Enter number
                """);
            }
            System.out.print(">> ");
            int check = in.nextInt();
            in.nextLine();
            switch (check) {
                case 0 -> {
                    System.out.println("게임을 종료합니다.");
                    System.exit(0);
                }
                case 1 -> {
                    if(!User.login) {
                        User.signUp();
                    }
                    else {
                        System.out.println("게임시작!");
                    }
                }
                case 2 -> {
                    if(!User.login) {
                        User.signIn();
                    }
                    else {
                        System.out.println("마이페이지!");
                    }
                }
                case 3 -> {
                    if(!User.login) {
                        User.modifyId();
                    }
                    User.login = false;
                    System.out.println("로그아웃 되었습니다.");
                    Main.mainMenu();
                }
                case 4 -> User.manager.showAllUsers();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("    Welcome to Console RPG Game");
        mainMenu();
    }
}
