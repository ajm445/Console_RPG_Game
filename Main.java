import java.util.InputMismatchException;
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
            try {
                int check = in.nextInt();
                in.nextLine();
                switch (check) {
                    case 0 -> {
                        System.out.println("게임을 종료합니다.");
                        System.exit(0);
                    }
                    case 1 -> {
                        if(!User.login) {
                            System.out.println("[회원가입]");
                            User.signUp();
                        }
                        else {
                            GameManager.choiceJob();
                        }
                    }
                    case 2 -> {
                        if(!User.login) {
                            System.out.println("[로그인]");
                            User.signIn();
                        }
                        else {
                            User.myPage();
                        }
                    }
                    case 3 -> {
                        if(User.login) {
                            User.login = false;
                            System.out.println("로그아웃 되었습니다.");
                            Main.mainMenu();
                        } else {
                            System.out.println("0부터 2까지의 숫자 하나만 입력해주세요!");
                            Main.mainMenu();
                        }
                    }
                    case 4 -> User.userManager.showAllUsers();
                    default -> {
                        if(!User.login) {
                            System.out.println("0부터 2까지의 숫자 하나만 입력해주세요!");
                        }
                        else System.out.println("0부터 3까지의 숫자 하나만 입력해주세요!");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다! 숫자만 입력해주세요!");
                in.nextLine();
                Main.mainMenu();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("    Welcome to Console RPG Game");
        mainMenu();
    }
}
