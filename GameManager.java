import java.util.Scanner;

public class GameManager {
    static Scanner in = new Scanner(System.in);

    // 출력 천천히 나오게 하는 메서드 : 블로그 참조 -> https://okky.kr/questions/554033
//    public static void slowPrint(String message, long millisPerChar) {
//        for (int i = 0; i < message.length(); i++) {
//            System.out.print(message.charAt(i));
//            try {
//                Thread.sleep(millisPerChar);
//            }
//            catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static void choiceJob() {
        MyCharacter[] characters = {
                MyCharacter.createWarrior(),
                MyCharacter.createThief(),
                MyCharacter.createMage()
        };

//        String loding = "Game Loding...\n" + "3\n" + "2\n" + "1\n";
//        String Starting = "Game Starting...\n" + "3\n" + "2\n" + "1\n";
//
//        slowPrint(loding, 300);
//        System.out.println("Success!!");
//        slowPrint(Starting, 300);
//        System.out.println("Success!!");

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

        if (choice >= 1 && choice <= 3) {
            MyCharacter selected = characters[choice - 1];
            selected.characterInfo();

            System.out.print("이 직업을 선택하시겠습니까? (Y | N) : ");
            String input = in.next();

            if (input.length() > 0) {
                char check = input.charAt(0);

                if (check == 'Y' || check == 'y') {
                    System.out.println(selected.getJob() + "를 선택하셨습니다.");
                    in.nextLine();

                    // 아이템 슬롯 초기화 먼저
                    User.currentUser.setStoredAtkItem(-1);
                    User.currentUser.setStoredDefItem(-1);
                    User.currentUser.setMyCharacter(selected);

                    // 캐릭터와 유저 저장
                    UserManager.saveCharacter(User.currentUser.getId(), selected);
                    UserManager.saveUser(User.currentUser);

                    // 바로 게임 시작
                    GameManager.GameStart();
                } else if (check == 'N' || check == 'n') {
                    System.out.println("직업 선택을 취소했습니다.");
                    in.nextLine(); // flush
                    GameManager.choiceJob();
                } else {
                    System.out.println("잘못된 입력입니다.");
                    GameManager.choiceJob();
                }
            } else {
                System.out.println("입력이 감지되지 않았습니다.");
                GameManager.choiceJob();
            }

        } else {
            System.out.println("1부터 3까지의 숫자만 입력해주세요!");
            GameManager.choiceJob();
        }
    }

    public static void GameStart() {
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

        while (true) {
            System.out.print(">> ");
            String input = in.nextLine().trim(); // 공백 제거

            if (input.matches("[1-9]")) {
                int stageNum = Integer.parseInt(input);
                switch (stageNum) {
                    case 1 -> Stage.stage1();
                    case 2 -> Stage.stage2();
                    case 3 -> Stage.stage3();
                    case 4 -> Stage.stage4();
                    case 5 -> Stage.stage5();
                    case 6 -> System.out.println("스테이지 6번");
                    case 7 -> System.out.println("스테이지 7번");
                    case 8 -> System.out.println("스테이지 8번");
                    case 9 -> System.out.println("스테이지 9번");
                }
                return;
            } else if (input.equals("10")) {
                Stage.stage10();
                return;
            } else if (input.equals("c")) {
                User.currentUser.getMyCharacter().state();
                GameManager.GameStart();
            } else if (input.equals("e")) {
                System.out.println("적 정보");
                Enemy.showAllEnemies();
                GameManager.GameStart();
            } else if (input.equals("s")) {
                Store.store();
            } else if (input.equals("l")) {
                User.currentUser.setLogin(false);
                User.currentUser = null;
                System.out.println("로그아웃 되었습니다.");
                Main.mainMenu();
                return;
            } else if (input.equals("x")) {
                System.out.println("게임을 종료합니다.");
                System.exit(0);
            } else {
                System.out.println("올바른 입력을 해주세요.");
            }
        }
    }

    // 안전한 숫자 입력 유틸 함수
    private static int safeReadInt(Scanner in, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return in.nextInt();
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다! 숫자만 입력해주세요!");
                in.nextLine(); // flush
            }
        }
    }
}
