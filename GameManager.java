import java.util.Scanner;

public class GameManager {
    static Scanner in = new Scanner(System.in); // 사용자 입력 Scanner

    /**
     * slowPrint - 문자열을 천천히 출력하는 효과
     * @param message 출력할 메시지
     * @param millisPerChar 문자 하나당 딜레이(ms)
     */
    public static void slowPrint(String message, long millisPerChar) {
        for (int i = 0; i < message.length(); i++) {
            System.out.print(message.charAt(i));
            try {
                Thread.sleep(millisPerChar);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * choiceJob - 캐릭터 직업 선택 메뉴
     * 사용자에게 직업(전사, 도적, 마법사) 중 하나를 선택하게 하고, 선택된 캐릭터를 저장한다.
     */
    public static void choiceJob() {
        // 선택 가능한 직업들
        MyCharacter[] characters = {
                MyCharacter.createWarrior(),
                MyCharacter.createThief(),
                MyCharacter.createMage()
        };

        // 로딩 애니메이션
        String loding = "Game Loding...\n" + "3\n" + "2\n" + "1\n";
        String Starting = "Game Starting...\n" + "3\n" + "2\n" + "1\n";

        slowPrint(loding, 300);
        System.out.println("Success!!");
        slowPrint(Starting, 300);
        System.out.println("Success!!");

        // 직업 선택 메뉴
        System.out.print("""
                ***********************************
                          Console RPG Game
                        == 직업을 선택하세요 ==
                ***********************************
                              1. 전사
                              2. 도적
                             3. 마법사
                ***********************************
                Enter number
                """);

        int choice = safeReadInt(in, ">> ");

        // 직업 선택 처리
        if (choice >= 1 && choice <= 3) {
            MyCharacter selected = characters[choice - 1];
            selected.characterInfo();

            System.out.print("이 직업을 선택하시겠습니까? (Y | N) : ");
            String input = in.next();

            if (input.length() > 0) {
                char check = input.charAt(0);

                if (check == 'Y' || check == 'y') {
                    // 직업 선택 완료
                    System.out.println(selected.getJob() + "를 선택하셨습니다.");
                    in.nextLine(); // flush

                    // 장비 초기화
                    User.currentUser.setStoredAtkItem(-1);
                    User.currentUser.setStoredDefItem(-1);
                    User.currentUser.setMyCharacter(selected);

                    // 캐릭터 및 유저 저장
                    UserManager.saveCharacter(User.currentUser.getId(), selected);
                    UserManager.saveUser(User.currentUser);

                    // 게임 시작
                    GameStart();
                } else if (check == 'N' || check == 'n') {
                    // 선택 취소
                    System.out.println("직업 선택을 취소했습니다.");
                    in.nextLine();
                    choiceJob(); // 다시 선택
                } else {
                    System.out.println("잘못된 입력입니다.");
                    choiceJob(); // 다시 선택
                }
            } else {
                System.out.println("입력이 감지되지 않았습니다.");
                choiceJob(); // 다시 선택
            }
        } else {
            System.out.println("1부터 3까지의 숫자만 입력해주세요!");
            choiceJob(); // 다시 선택
        }
    }

    /**
     * GameStart - 게임 시작 후 스테이지 선택 및 메뉴 처리
     */
    public static void GameStart() {
        // 게임 메뉴 출력
        System.out.print("""
                ***********************************
                          Console RPG Game
                       == 스테이지를 선택하세요 ==
                ***********************************
                """);
        System.out.println("           * 보유 골드 : " + User.currentUser.getGold());
        System.out.print("""
                           *  캐릭터 상태(c)
                             * 적 정보(e)
                              * 상점(s)
                            *  로그아웃(l)
                            *  종료하기(x)
                ***********************************
                                       
                      stage 1         stage 2
                      stage 3         stage 4
                      stage 5         stage 6
                      stage 7         stage 8
                      stage 9         stage 10
                                            
                ***********************************
                Enter number
                """);

        // 사용자 입력 처리 루프
        while (true) {
            System.out.print(">> ");
            String input = in.nextLine().trim();

            // 스테이지 번호 입력 처리
            if (input.matches("[1-9]")) {
                int stageNum = Integer.parseInt(input);
                switch (stageNum) {
                    case 1 -> Stage.stage1();
                    case 2 -> Stage.stage2();
                    case 3 -> Stage.stage3();
                    case 4 -> Stage.stage4();
                    case 5 -> Stage.stage5();
                    case 6 -> Stage.stage6();
                    case 7 -> Stage.stage7();
                    case 8 -> Stage.stage8();
                    case 9 -> Stage.stage9();
                }
                return; // 스테이지 입장 후 종료
            } else if (input.equals("10")) {
                Stage.stage10();
                return;
            }

            // 기타 명령어 입력 처리
            switch (input) {
                case "c" -> {
                    // 캐릭터 상태 보기
                    User.currentUser.getMyCharacter().state();
                    GameStart(); // 다시 메뉴로
                }
                case "e" -> {
                    // 적 정보 보기
                    System.out.println("적 정보");
                    Enemy.showAllEnemies();
                    GameStart(); // 다시 메뉴로
                }
                case "s" -> {
                    // 상점 입장
                    Store.store();
                }
                case "l" -> {
                    // 로그아웃 처리
                    User.currentUser.setLogin(false);
                    User.currentUser = null;
                    System.out.println("로그아웃 되었습니다.");
                    Main.mainMenu();
                    return;
                }
                case "x" -> {
                    // 게임 종료
                    System.out.println("게임을 종료합니다.");
                    System.exit(0);
                }
                default -> System.out.println("올바른 입력을 해주세요!");
            }
        }
    }

    /**
     * safeReadInt - 숫자 입력을 안전하게 받는 유틸리티
     * @param in Scanner
     * @param prompt 출력 메시지
     * @return 입력된 정수값
     */
    private static int safeReadInt(Scanner in, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return in.nextInt();
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다! 숫자만 입력해주세요!");
                in.nextLine(); // 입력 버퍼 비우기
            }
        }
    }
}
