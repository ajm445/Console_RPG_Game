import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Stage {
    public static boolean[] stageClear = new boolean[10];
    static Scanner in = new Scanner(System.in);

    public static int getUserInput() {
        while (true) {
            System.out.print("선택: ");
            try {
                int input = in.nextInt();
                in.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력하세요!");
                in.nextLine();
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

            UserManager.saveCharacter(User.currentUser.getId(), player);
        }
        return true;
    }

    public static void stage1() {
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

        User.currentUser.setStageClear(Stage.stageClear);
        UserManager.saveUser(User.currentUser);
        UserManager.saveCharacter(User.currentUser.getId(), player);
        GameManager.GameStart();
    }

    public static void stage2() {
        MyCharacter player = User.currentUser.getMyCharacter();
        Enemy[] enemies = {
                Enemy.getEnemyByIndex(0),
                Enemy.getEnemyByIndex(1)
        };
        int totalReward = 0;
        boolean auto = autoMode(1);
        if(Stage.stageClear[0]) {
            System.out.println("\n스테이지 2 시작!");
            for (Enemy enemy : enemies) {
                boolean success = fight(enemy, player, auto,50);
                if (!success) return;
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

    public static void stage3() {
        if(Stage.stageClear[1]) {
            MyCharacter player = User.currentUser.getMyCharacter();
            int totalReward = 0;
            boolean auto = autoMode(2);

            System.out.println("\n스테이지 3 시작!");

            for (int i = 0; i < 2; i++) {
                Enemy enemy = Enemy.getEnemyByIndex(1);
                boolean success = fight(enemy, player, auto, 80);
                if (!success) return;
                totalReward += Objects.requireNonNull(enemy).getReward();
            }

            Enemy enemy = Enemy.getEnemyByIndex(2);
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

    public static void stage4() {
        if(Stage.stageClear[2]) {
            MyCharacter player = User.currentUser.getMyCharacter();
            boolean auto = autoMode(3);
            int totalReward = 0;

            System.out.println("\n스테이지 4 시작!");

            Enemy enemy1 = Enemy.getEnemyByIndex(2);
            if (!fight(enemy1, player, auto, 85)) return;
            totalReward += Objects.requireNonNull(enemy1).getReward();

            Enemy enemy2 = Enemy.getEnemyByIndex(1);
            if (!fight(enemy2, player, auto, 85)) return;
            totalReward += Objects.requireNonNull(enemy2).getReward();

            Enemy enemy3 = Enemy.getEnemyByIndex(2);
            if (!fight(enemy3, player, auto, 85)) return;
            totalReward += Objects.requireNonNull(enemy3).getReward();

            Enemy enemy4 = Enemy.getEnemyByIndex(1);
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

    public static void stage5() {
        MyCharacter player = User.currentUser.getMyCharacter();
        Enemy[] enemies = {
                Enemy.getEnemyByIndex(1),
                Enemy.getEnemyByIndex(2),
                Enemy.getEnemyByIndex(3)
        };
        int totalReward = 0;
        boolean auto = autoMode(4);
        if(Stage.stageClear[3]) {
            System.out.println("\n스테이지 5 시작!");
            for (Enemy enemy : enemies) {
                boolean success = fight(enemy, player, auto,200);
                if (!success) return;
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
            MyCharacter player = User.currentUser.getMyCharacter();
            int totalReward = 0;
            boolean auto = autoMode(5);

            System.out.println("\n스테이지 6 시작!");

            for (int i = 0; i < 2; i++) {
                Enemy enemy = Enemy.getEnemyByIndex(3);
                boolean success = fight(enemy, player, auto, 250);
                if (!success) return;
                totalReward += Objects.requireNonNull(enemy).getReward();
            }

            System.out.println("모든 적을 처치했습니다!");
            Stage.stageClear[5] = true;
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

    public static void stage7() {
        if(Stage.stageClear[5]) {
            MyCharacter player = User.currentUser.getMyCharacter();
            int totalReward = 0;
            boolean auto = autoMode(6);

            System.out.println("\n스테이지 7 시작!");

            for (int i = 0; i < 2; i++) {
                Enemy enemy = Enemy.getEnemyByIndex(3);
                boolean success = fight(enemy, player, auto, 250);
                if (!success) return;
                totalReward += Objects.requireNonNull(enemy).getReward();
            }

            Enemy enemy = Enemy.getEnemyByIndex(4);
            boolean success = fight(enemy, player, auto, 500);
            if (!success) return;
            totalReward += Objects.requireNonNull(enemy).getReward();

            System.out.println("모든 적을 처치했습니다!");
            Stage.stageClear[6] = true;
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

    public static void stage8() {
        if(Stage.stageClear[6]) {
            MyCharacter player = User.currentUser.getMyCharacter();
            int totalReward = 0;
            boolean auto = autoMode(7);

            System.out.println("\n스테이지 8 시작!");

            for (int i = 0; i < 2; i++) {
                Enemy enemy = Enemy.getEnemyByIndex(4);
                boolean success = fight(enemy, player, auto, 800);
                if (!success) return;
                totalReward += Objects.requireNonNull(enemy).getReward();
            }

            System.out.println("모든 적을 처치했습니다!");
            Stage.stageClear[7] = true;
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

    public static void stage9() {
        MyCharacter player = User.currentUser.getMyCharacter();
        Enemy[] enemies = {
                Enemy.getEnemyByIndex(0),
                Enemy.getEnemyByIndex(1),
                Enemy.getEnemyByIndex(2),
                Enemy.getEnemyByIndex(3),
                Enemy.getEnemyByIndex(3),
                Enemy.getEnemyByIndex(4),
                Enemy.getEnemyByIndex(4),
                Enemy.getEnemyByIndex(4)
        };
        int totalReward = 0;
        boolean auto = autoMode(8);
        if(Stage.stageClear[7]) {
            System.out.println("\n스테이지 9 시작!");
            for (Enemy enemy : enemies) {
                boolean success = fight(enemy, player, auto,1200);
                if (!success) return;
                totalReward += Objects.requireNonNull(enemy).getReward();
            }

            System.out.println("모든 적을 처치했습니다!");
            Stage.stageClear[8] = true;
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

    public static void stage10() {
        if (Stage.stageClear[8]) {
            MyCharacter player = User.currentUser.getMyCharacter();
            Enemy enemy = Enemy.getEnemyByIndex(5);
            boolean auto = autoMode(9);

            System.out.println("스테이지 10 시작!");
            System.out.println("마지막 전투다!");

            boolean success = fight(enemy, player, auto, 1000);
            if (success) {
                int reward = Objects.requireNonNull(enemy).getReward();
                User.currentUser.addGold(reward);
                System.out.println(reward + "골드를 획득했습니다! 현재 골드: " + User.currentUser.getGold());
            }

            UserManager.saveUser(User.currentUser);
            UserManager.saveCharacter(User.currentUser.getId(), player);
            GameManager.GameStart();
        } else {
            System.out.println("이전 스테이지를 클리어 해주세요!");
            GameManager.GameStart();
        }
    }
}
