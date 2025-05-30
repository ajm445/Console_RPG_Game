import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Stage {
    // private static boolean gameClear = false;
    public static boolean[] stageClear = new boolean[10];
    static Scanner in = new Scanner(System.in);

    public static int getUserInput() {
        while (true) {
            System.out.print("선택: ");
            try {
                int input = in.nextInt();
                in.nextLine(); // 입력 버퍼 비우기
                return input;
            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력하세요!");
                in.nextLine(); // 잘못된 입력 버퍼 비우기
            }
        }
    }

    private static boolean autoMode(int stageNumber) {
        if(Stage.stageClear[stageNumber]) {
            System.out.println("\n자동 전투를 하시겠습니까? (1. 예 / 2. 아니오)");
            int input = getUserInput();
            return input == 1;
        }
        return false;
    }

    private static boolean fight(Enemy enemy, MyCharacter player, boolean autoMode, int lostGold) {
        System.out.println(Objects.requireNonNull(enemy).getName() + "이 나타났다!");

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\n--- 당신의 턴 ---");
            System.out.println("1. 공격");

            int input = autoMode ? 1 : getUserInput();

            if (input == 1) {
                player.attack(enemy);
            } else {
                System.out.println("잘못된 선택입니다. 다시 입력하세요.");
                continue;
            }

            if (!enemy.isAlive()) {
                System.out.println(enemy.getName() + "을(를) 쓰러뜨렸다!\n");
                return true;
            }

            System.out.println("\n--- 적의 턴 ---");
            enemy.attack(player);
            if (!player.isAlive()) {
                System.out.println(player.getJob() + "이(가) 쓰러졌다...");
                User.currentUser.returnUserState();
                System.out.println("용에게 당했습니다...");
                User.currentUser.loseGold(lostGold);
                System.out.println("도망치다 돈을 잃었습니다...");
                UserManager.saveUser(User.currentUser);
                UserManager.saveCharacter(User.currentUser.getId(), player);
                GameManager.GameStart();
                return false;
            }

            System.out.println("\n[전투 상태]");
            player.fightState();
            System.out.println("적 HP : " + enemy.getHp());

            // 상태 저장
            UserManager.saveCharacter(User.currentUser.getId(), player);
        }
        return true; // 적 처치 성공
    }

    public static void stage1() { // 약해용 1
        MyCharacter player = User.currentUser.getMyCharacter();
        Enemy enemy = Enemy.getEnemyByIndex(0);
        boolean auto = autoMode(0);

        System.out.println("스테이지 1 시작!");

        boolean success = fight(enemy, player, auto, 30);
        if (success) {
            int reward = Objects.requireNonNull(enemy).getReward();
            User.currentUser.addGold(reward);
            System.out.println(reward + "골드를 획득했습니다! 현재 골드: " + User.currentUser.getGold());
            Stage.stageClear[0] = true;
        }

        // 저장 및 복귀
        User.currentUser.setStageClear(Stage.stageClear); // 스테이지 클리어 정보 저장
        UserManager.saveUser(User.currentUser);
        UserManager.saveCharacter(User.currentUser.getId(), player);
        GameManager.GameStart();
    }

    public static void stage2() { // 약해용 1, 단단해용 1
        MyCharacter player = User.currentUser.getMyCharacter();
        Enemy[] enemies = {
                Enemy.getEnemyByIndex(0), // 약해용
                Enemy.getEnemyByIndex(1)  // 단단해용
        };
        int totalReward = 0;
        boolean auto = autoMode(1);
        if(Stage.stageClear[0]) {
            System.out.println("\n스테이지 2 시작!");
            for (Enemy enemy : enemies) {
                boolean success = fight(enemy, player, auto,50);
                if (!success) return; // 전투 실패 (플레이어 사망)
                totalReward += Objects.requireNonNull(enemy).getReward();
            }

            System.out.println("모든 적을 처치했습니다!");
            Stage.stageClear[1] = true;
            User.currentUser.addGold(totalReward);
            System.out.println(totalReward + "골드를 획득했습니다! 현재 골드: " + User.currentUser.getGold());

            UserManager.saveCharacter(User.currentUser.getId(), player);
            UserManager.saveUser(User.currentUser);
            GameManager.GameStart();
        } else {
            System.out.println("이전 스테이지를 클리어 해주세요!");
            GameManager.GameStart();
        }
    }

    public static void stage3() { // 단단해용 2, 나쌔용
        if(Stage.stageClear[1]) {
            MyCharacter player = User.currentUser.getMyCharacter();
            int totalReward = 0;
            boolean auto = autoMode(2);

            System.out.println("\n스테이지 3 시작!");

            // 단단해용 2마리
            for (int i = 0; i < 2; i++) {
                Enemy enemy = Enemy.getEnemyByIndex(1); // 단단해용
                boolean success = fight(enemy, player, auto, 80);
                if (!success) return;
                totalReward += Objects.requireNonNull(enemy).getReward();
            }

            // 나쌔용 1마리
            Enemy enemy = Enemy.getEnemyByIndex(2); // 나쌔용
            boolean success = fight(enemy, player, auto, 90);
            if (!success) return;
            totalReward += Objects.requireNonNull(enemy).getReward();

            System.out.println("모든 적을 처치했습니다!");
            Stage.stageClear[2] = true;
            User.currentUser.addGold(totalReward);
            System.out.println(totalReward + "골드를 획득했습니다! 현재 골드: " + User.currentUser.getGold());

            UserManager.saveCharacter(User.currentUser.getId(), player);
            UserManager.saveUser(User.currentUser);
            GameManager.GameStart();
        } else {
            System.out.println("이전 스테이지를 클리어 해주세요!");
            GameManager.GameStart();
        }
    }

    public static void stage4() { // 나쌔용 1, 단단해용 1, 나쌔용 1, 단단해용 1
        if(Stage.stageClear[2]) {
            MyCharacter player = User.currentUser.getMyCharacter();
            boolean auto = autoMode(3);
            int totalReward = 0;

            System.out.println("\n스테이지 4 시작!");

            Enemy enemy1 = Enemy.getEnemyByIndex(2); // 나쌔용
            if (!fight(enemy1, player, auto, 85)) return;
            totalReward += Objects.requireNonNull(enemy1).getReward();

            Enemy enemy2 = Enemy.getEnemyByIndex(1); // 단단해용
            if (!fight(enemy2, player, auto, 85)) return;
            totalReward += Objects.requireNonNull(enemy2).getReward();

            Enemy enemy3 = Enemy.getEnemyByIndex(2); // 나쌔용
            if (!fight(enemy3, player, auto, 85)) return;
            totalReward += Objects.requireNonNull(enemy3).getReward();

            Enemy enemy4 = Enemy.getEnemyByIndex(1); // 단단해용
            if (!fight(enemy4, player, auto, 85)) return;
            totalReward += Objects.requireNonNull(enemy4).getReward();

            System.out.println("모든 적을 처치했습니다!");
            Stage.stageClear[3] = true;
            User.currentUser.addGold(totalReward);
            System.out.println(totalReward + "골드를 획득했습니다! 현재 골드: " + User.currentUser.getGold());

            UserManager.saveCharacter(User.currentUser.getId(), player);
            UserManager.saveUser(User.currentUser);
            GameManager.GameStart();
        } else {
            System.out.println("이전 스테이지를 클리어 해주세요!");
            GameManager.GameStart();
        }
    }


    public static void stage5() { // 단단해용 1, 나쌔용 1, 화나용 1
        MyCharacter player = User.currentUser.getMyCharacter();
        Enemy[] enemies = {
                Enemy.getEnemyByIndex(1), // 단단해용
                Enemy.getEnemyByIndex(2),  // 나쌔용
                Enemy.getEnemyByIndex(3) // 화나용
        };
        int totalReward = 0;
        boolean auto = autoMode(4);
        if(Stage.stageClear[3]) {
            System.out.println("\n스테이지 5 시작!");
            for (Enemy enemy : enemies) {
                boolean success = fight(enemy, player, auto,200);
                if (!success) return; // 전투 실패 (플레이어 사망)
                totalReward += Objects.requireNonNull(enemy).getReward();
            }

            System.out.println("모든 적을 처치했습니다!");
            Stage.stageClear[4] = true;
            User.currentUser.addGold(totalReward);
            System.out.println(totalReward + "골드를 획득했습니다! 현재 골드: " + User.currentUser.getGold());

            UserManager.saveCharacter(User.currentUser.getId(), player);
            UserManager.saveUser(User.currentUser);
            GameManager.GameStart();
        } else {
            System.out.println("이전 스테이지를 클리어 해주세요!");
            GameManager.GameStart();
        }
    }

    public static void stage6() {
        if(Stage.stageClear[4]) {
            Stage.stageClear[5] = true;
        } else {
            System.out.println("이전 스테이지를 클리어 해주세요!");
            GameManager.GameStart();
        }
    }

    public static void stage7() {
        if(Stage.stageClear[5]) {
            Stage.stageClear[6] = true;
        } else {
            System.out.println("이전 스테이지를 클리어 해주세요!");
            GameManager.GameStart();
        }
    }

    public static void stage8() {
        if(Stage.stageClear[6]) {
            Stage.stageClear[7] = true;
        } else {
            System.out.println("이전 스테이지를 클리어 해주세요!");
            GameManager.GameStart();
        }
    }
    public static void stage9() {
        if(Stage.stageClear[7]) {
            Stage.stageClear[8] = true;
        } else {
            System.out.println("이전 스테이지를 클리어 해주세요!");
            GameManager.GameStart();
        }
    }

    public static void stage10() { // 보스에용 1
        if (Stage.stageClear[8]) {
            MyCharacter player = User.currentUser.getMyCharacter();
            Enemy enemy = Enemy.getEnemyByIndex(5);
            boolean auto = autoMode(9);

            System.out.println("스테이지 10 시작!");

            boolean success = fight(enemy, player, auto, 1000);
            if (success) {
                int reward = Objects.requireNonNull(enemy).getReward();
                User.currentUser.addGold(reward);
                System.out.println(reward + "골드를 획득했습니다! 현재 골드: " + User.currentUser.getGold());
                // Stage.gameClear = true;
            }

            // 저장 및 복귀
            UserManager.saveUser(User.currentUser);
            UserManager.saveCharacter(User.currentUser.getId(), player);
            GameManager.GameStart();
        } else {
            System.out.println("이전 스테이지를 클리어 해주세요!");
            GameManager.GameStart();
        }
    }
}