import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);

    public static void mainMenu() {
        while (true) {
            if (User.currentUser == null || !User.currentUser.getLogin()) {
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
            } else {
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

            int check = safeReadInt(in, ">> ");

            switch (check) {
                case 0 -> {
                    System.out.println("게임을 종료합니다.");
                    System.exit(0);
                }
                case 1 -> {
                    if (User.currentUser == null || !User.currentUser.getLogin()) {
                        System.out.println("[회원가입]");
                        User.signUp();
                    } else {
                        if(!User.currentUser.hasCharacter()) {
                            GameManager.choiceJob();
                        } else {
                            GameManager.GameStart();
                        } return;
                    }
                }
                case 2 -> {
                    if (User.currentUser == null || !User.currentUser.getLogin()) {
                        System.out.println("[로그인]");
                        User.signIn();
                    } else {
                        System.out.println("[마이페이지]");
                        User.myPage();
                    }
                }
                case 3 -> {
                    if (User.currentUser != null && User.currentUser.getLogin()) {
                        System.out.println("로그아웃 되었습니다.");
                        User.currentUser.setLogin(false);
                        User.currentUser = null;
                    } else {
                        System.out.println("잘못된 입력입니다.");
                    }
                }
                case 4 -> UserManager.getInstance().showAllUsers();
                default -> {
                    if(!User.currentUser.getLogin()) {
                        System.out.println("0부터 2까지의 숫자 하나만 입력해주세요!");
                    }
                    else System.out.println("0부터 3까지의 숫자 하나만 입력해주세요!");
                }
                // default -> System.out.println("0부터 3까지의 숫자만 입력해주세요!");
            }
        }
    }

    // 안전한 숫자 입력 유틸 함수
    private static int safeReadInt(Scanner in, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다! 숫자만 입력해주세요!");
                in.nextLine(); // 버퍼 비우기
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("    Welcome to Console RPG Game");
        mainMenu();
    }
}
