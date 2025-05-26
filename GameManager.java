import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class GameManager {
    static Scanner in = new Scanner(System.in);
    // public static boolean gameStart = false;
    // 출력 천천히 나오게 하는 메서드 : 블로그 참조 -> https://okky.kr/questions/554033
    public static void slowPrint(String message, long millisPerChar) {
        for (int i = 0; i < message.length(); i++) {
            System.out.print(message.charAt(i));
            try {
                Thread.sleep(millisPerChar);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void choiceJob() {
        MyCharacter[] characters = {
                MyCharacter.createWarrior(),
                MyCharacter.createThief(),
                MyCharacter.createMage()
        };
//        String loding = "Game Loding...\n" + "3\n" + "2\n" + "1\n";
//        String Starting = "Game Starting...\n" + "3\n" + "2\n" + "1\n";
//        if(!gameStart) {
//            slowPrint(loding, 300);
//            System.out.println("Success!!");
//            slowPrint(Starting, 300);
//            System.out.println("Success!!");
//            gameStart = true;
//        }
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
        System.out.print(">> ");
        try {
            int choice = in.nextInt();
            in.nextLine();
            if(choice >= 1 && choice <= 3) {
                MyCharacter selected = characters[choice - 1]; // GPT 이용
                // characters[choice - 1].characterInfo(); // 구문이 너무 길어 GPT 이용
                selected.characterInfo();
                System.out.print("이 직업을 선택하시겠습니까? (Y | N) : ");
                try {
                    char check = in.next().charAt(0);
                    if(check == 'Y' || check == 'y') {
                        System.out.println(selected.getJob() + "를 선택하셨습니다.");
                        User.currentUser.setMyCharacter(selected);
                        if (!User.currentUser.hasCharacter()) {
                            GameManager.choiceJob();
                        } else {
                            GameManager.GameStart();
                        }
                        GameManager.GameStart();
                    } else if(check == 'N' || check == 'n') {
                        System.out.println("직업 선택를 취소했습니다.");
                        in.nextLine();
                        GameManager.choiceJob();
                    }
                    else System.out.println("잘못된 입력입니다."); GameManager.choiceJob();
                } catch(InputMismatchException e) {
                    System.out.println("잘못된 입력입니다! 숫자만 입력해주세요!");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("잘못된 입력입니다! 숫자만 입력해주세요!");
            in.nextLine();
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
        System.out.print(">> ");
        try { // 숫자와 문자 둘 다 입력 받기 : GPT 이용
            while(true) {
                String input = in.nextLine();
                if(input.length() == 1) {
                    char ch = input.charAt(0);
                    if(Character.isDigit(ch)) {
                        int check = Character.getNumericValue(ch);
                        switch (check) {
                            case 1 -> {
                                // System.out.println("스테이지 1번");
                                Stage.stage1();
                                return;
                            }
                            case 2 -> {
                                Stage.stage2();
                                return;
                            }
                            case 3 -> {
                                System.out.println("스테이지 3번");
                                return;
                            }
                            case 4 -> {
                                System.out.println("스테이지 4번");
                                return;
                            }
                            case 5 -> {
                                System.out.println("스테이지 5번");
                                return;
                            }
                            case 6 -> {
                                System.out.println("스테이지 6번");
                                return;
                            }
                            case 7 -> {
                                System.out.println("스테이지 7번");
                                return;
                            }
                            case 8 -> {
                                System.out.println("스테이지 8번");
                                return;
                            }
                            case 9 -> {
                                System.out.println("스테이지 9번");
                                return;
                            }
                            case 10 -> {
                                System.out.println("스테이지 10번");
                                return;
                            }
                            default -> System.out.println("1부터 10까지의 숫자 하나만 입력해주세요!");
                        }
                    } else {
                        switch (ch) {
                            case 'c' -> {
                                User.currentUser.getMyCharacter().state();
                                GameManager.GameStart();
                            }
                            case 'e' -> {
                                System.out.println("적 정보");
                                Enemy.showAllEnemies();
                                GameManager.GameStart();
                                return;
                            }
                            case 's' -> {
                                Store.store();
                                return;
                            }
                            case 'l' -> {
                                User.currentUser.setLogin(false);
                                User.currentUser = null;
                                System.out.println("로그아웃 되었습니다.");
                                Main.mainMenu();
                                return;
                            }
                            case 'x' -> {
                                System.out.println("게임을 종료합니다.");
                                System.exit(0);
                            }
                            default -> System.out.println("다시 입력해주세요.");
                        }
                    }
                }
            }
        } catch(InputMismatchException e) {
            System.out.println("잘못된 입력입니다! 숫자만 입력해주세요!");
            in.nextLine();
            Main.mainMenu();
        }
    }
}
