import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Stage {
    public static boolean gameClear = false;
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

    public static void stage1() { // 용 한마리만 나오는 경우
        Enemy enemy = Enemy.getEnemyByIndex(0); // 약해용
        MyCharacter player = User.currentUser.getMyCharacter();
        System.out.println("스테이지 1 시작! " + Objects.requireNonNull(enemy).getName() +"이 나타났다!");

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\n--- 당신의 턴 ---");
            System.out.println("1. 공격");
            System.out.print("선택: ");
            int input = getUserInput();

            if (input == 1) {
                player.attack(enemy);
            } else {
                System.out.println("잘못된 선택입니다. 다시 입력하세요.");
                continue;
            }

            if (!enemy.isAlive()) {
                System.out.print("\n");
                System.out.println(enemy.getName() + "을(를) 쓰러뜨렸다!");
                // 골드 보상 지급
                User.currentUser.addGold(10);
                System.out.println("10골드를 획득했습니다! 현재 골드: " + User.currentUser.getGold());
                Stage.gameClear = true;

                // 변경사항 저장
                UserManager.saveUser(User.currentUser);
                UserManager.saveCharacter(User.currentUser.getId(), player);
                break;
            }

            System.out.println("\n--- 적의 턴 ---");
            enemy.attack(player);
            if (!player.isAlive()) {
                System.out.println(player.getJob() + "이(가) 쓰러졌다...");
                User.currentUser.returnUserState();
                System.out.println("용에게 당했습니다...");

                // 사망 시에도 변경사항 저장
                UserManager.saveUser(User.currentUser);
                UserManager.saveCharacter(User.currentUser.getId(), player);
                break;
            }

            System.out.println("\n[전투 상태]");
            player.fightState();
            System.out.println("적 HP : " + enemy.getHp());

            // 전투 중 체력 변화 저장
            UserManager.saveCharacter(User.currentUser.getId(), player);
        }

        GameManager.GameStart();
    }

    public static void stage2() { // 다른 용이 나오는 경우
        MyCharacter player = User.currentUser.getMyCharacter();
        Enemy[] enemies = {
                Enemy.getEnemyByIndex(0), // 약해용
                Enemy.getEnemyByIndex(1) // 느려용
        };

        System.out.println("\n스테이지 2 시작!");
        for (Enemy enemy : enemies) {
            System.out.println(Objects.requireNonNull(enemy).getName() + "이 나타났다!");

            while (player.isAlive() && enemy.isAlive()) {
                System.out.println("\n--- 당신의 턴 ---");
                System.out.println("1. 공격");
                System.out.print("선택: ");
                int input = getUserInput();

                if (input == 1) {
                    player.attack(enemy);
                } else {
                    System.out.println("잘못된 선택입니다. 다시 입력하세요.");
                    continue;
                }

                if (!enemy.isAlive()) {
                    System.out.println(enemy.getName() + "을(를) 쓰러뜨렸다!");
                    break;
                }

                System.out.println("\n--- 적의 턴 ---");
                enemy.attack(player);
                if (!player.isAlive()) {
                    System.out.println(player.getJob() + "이(가) 쓰러졌다...");
                    User.currentUser.returnUserState();
                    System.out.println("용에게 당했습니다...");
                    GameManager.GameStart();
                    return;
                }

                System.out.println("\n[전투 상태]");
                player.fightState();
                System.out.println("적 HP : " + enemy.getHp());
            }
        }

        System.out.println("모든 적을 처치했습니다!");
        Stage.gameClear = true;
        User.currentUser.addGold(40);
        System.out.println("40골드를 획득했습니다! 현재 골드: " + User.currentUser.getGold());

        // 골드 변경사항 저장
        UserManager.saveUser(User.currentUser);
        GameManager.GameStart();
    }

    public static void stage3() { // 같은 용이 중복해서 나오는 경우
        MyCharacter player = User.currentUser.getMyCharacter();
        int dragon = 2;
        int count = 0;
        System.out.println("\n스테이지 3 시작!");

        while(count < dragon) {
            Enemy enemy = Enemy.getEnemyByIndex(1); // 느려용
            System.out.println(Objects.requireNonNull(enemy).getName() + "이 나타났다!");

            while (player.isAlive() && Objects.requireNonNull(enemy).isAlive()) {
                System.out.println("\n--- 당신의 턴 ---");
                System.out.println("1. 공격");
                System.out.print("선택: ");
                int input = getUserInput();

                if (input == 1) {
                    player.attack(enemy);
                } else {
                    System.out.println("잘못된 선택입니다. 다시 입력하세요.");
                    continue;
                }

                if (!enemy.isAlive()) {
                    System.out.println(enemy.getName() + "을(를) 쓰러뜨렸다!");
                    count++;
                    break;
                }

                System.out.println("\n--- 적의 턴 ---");
                enemy.attack(player);
                if (!player.isAlive()) {
                    System.out.println(player.getJob() + "이(가) 쓰러졌다...");
                    User.currentUser.returnUserState();
                    System.out.println("용에게 당했습니다...");
                    GameManager.GameStart();
                    return;
                }

                System.out.println("\n[전투 상태]");
                player.fightState();
                System.out.println("적 HP : " + enemy.getHp());
            }
        }

        System.out.println("모든 적을 처치했습니다!");
        Stage.gameClear = true;
        User.currentUser.addGold(20);
        System.out.println("20골드를 획득했습니다! 현재 골드: " + User.currentUser.getGold());

        // 골드 변경사항 저장
        UserManager.saveUser(User.currentUser);
        GameManager.GameStart();
    }
}
