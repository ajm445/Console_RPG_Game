import java.util.InputMismatchException;
import java.util.Scanner;

public class GameManager {
    public static boolean gameStart = false;
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
        Scanner in = new Scanner(System.in);
        Character[] characters = {
                Character.createWarrior(),
                Character.createThief(),
                Character.createMage()
        };
        String loding = "Game Loding...\n" + "3\n" + "2\n" + "1\n";
        String Starting = "Game Starting...\n" + "3\n" + "2\n" + "1\n";
        if(!gameStart) {
            slowPrint(loding, 300);
            System.out.println("Success!!");
            slowPrint(Starting, 300);
            System.out.println("Success!!");
            gameStart = true;
        }
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
                Character selected = characters[choice - 1]; // GPT 이용
                // characters[choice - 1].characterInfo(); // 구문이 너무 길어 GPT 이용
                selected.characterInfo();
                System.out.print("이 직업을 선택하시겠습니까? (Y | N) : ");
                try {
                    char check = in.next().charAt(0);
                    if(check == 'Y' || check == 'y') {
                        System.out.println(selected.getJob() + "를 선택하셨습니다.");
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
        }
    }
}
