import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in); // 사용자 입력용 Scanner

    /** 메인 메뉴 화면 */
    public static void mainMenu() {
        while (true) {
            // 로그인 여부에 따라 메뉴 화면 분기
            if (User.currentUser == null || !User.currentUser.getLogin()) {
                // 비로그인 시 메뉴
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
                // 로그인 후 메뉴
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

            int check = safeReadInt(in); // 숫자 입력 받기

            switch (check) {
                case 0 -> {
                    // 프로그램 종료
                    System.out.println("게임을 종료합니다.");
                    System.exit(0);
                }
                case 1 -> {
                    if (User.currentUser == null || !User.currentUser.getLogin()) {
                        // 비로그인 상태: 회원가입
                        System.out.println("[회원가입]");
                        User.signUp();
                    } else {
                        // 로그인 상태: 게임 시작 또는 직업 선택
                        if (!User.currentUser.hasCharacter()) {
                            GameManager.choiceJob(); // 직업 선택
                        } else {
                            GameManager.GameStart(); // 기존 캐릭터로 게임 시작
                        }
                        return; // 메뉴 루프 탈출 (게임 시작)
                    }
                }
                case 2 -> {
                    if (User.currentUser == null || !User.currentUser.getLogin()) {
                        // 비로그인 상태: 로그인
                        System.out.println("[로그인]");
                        User.signIn();
                    } else {
                        // 로그인 상태: 마이페이지 진입
                        System.out.println("[마이페이지]");
                        User.myPage();
                    }
                }
                case 3 -> {
                    // 로그아웃 처리
                    if (User.currentUser != null && User.currentUser.getLogin()) {
                        System.out.println("로그아웃 되었습니다.");
                        User.currentUser.setLogin(false);
                        User.currentUser = null;
                    } else {
                        System.out.println("잘못된 입력입니다.");
                    }
                }
                case 4 -> {
                    // (디버그용) 전체 유저 목록 출력
                    UserManager.getInstance().showAllUsers();
                }
                default -> {
                    // 잘못된 숫자 입력 시 안내
                    if (User.currentUser == null || !User.currentUser.getLogin()) {
                        System.out.println("0부터 2까지의 숫자 하나만 입력해주세요!");
                    } else {
                        System.out.println("0부터 3까지의 숫자 하나만 입력해주세요!");
                    }
                }
            }
        }
    }

    /** 안전한 숫자 입력 처리 */
    private static int safeReadInt(Scanner in) {
        while (true) {
            System.out.print(">> ");
            String input = in.nextLine();
            try {
                return Integer.parseInt(input); // 숫자로 변환
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다! 숫자만 입력해주세요!");
            }
        }
    }

    /** 프로그램 시작 지점 */
    public static void main(String[] args) {
        System.out.println("    Welcome to Console RPG Game"); // 시작 인사 출력
        mainMenu(); // 메인 메뉴 진입
    }
}
