import java.util.Scanner;

public class Stage {
    public static boolean gameClear = false;
    public static void stage1() {
        System.out.println("스테이지 1 시작! 약해용이 나타났다!");
        Enemy enemy = Enemy.getEnemyByIndex(0);  // 약해용 (Enemy 배열 0번)
        MyCharacter player = User.currentUser.getMyCharacter();

        Scanner scanner = new Scanner(System.in);

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\n--- 당신의 턴 ---");
            System.out.println("1. 공격");
            System.out.print("선택: ");

            int input = scanner.nextInt();

            if (input == 1) {
                player.attack(enemy);
            } else {
                System.out.println("잘못된 선택입니다. 다시 입력하세요.");
                continue;
            }

            if (!enemy.isAlive()) {
                System.out.println(enemy.getName() + "을(를) 쓰러뜨렸다!");
                // 골드 보상 지급
                User.currentUser.addGold(10);
                System.out.println("10골드를 획득했습니다! 현재 골드: " + User.currentUser.getGold());
                break;
            }

            System.out.println("\n--- 적의 턴 ---");
            enemy.attack(player);

            if (!player.isAlive()) {
                System.out.println(player.getJob() + "이(가) 쓰러졌다...");
                // 게임 초기화 호출
                User.currentUser.resetUserState();
                System.out.println("게임이 초기 상태로 리셋되었습니다.");
                break;
            }

            System.out.println("\n[전투 상태]");
            player.state();
            System.out.println("적 HP: " + enemy.getHp());
        }

        System.out.println("\n전투가 종료되었습니다.");
        GameManager.GameStart();
    }
}