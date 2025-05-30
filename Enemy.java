import java.util.Random;

public class Enemy {
    // ──────────────── [인스턴스 변수] ────────────────
    private String name;  // 적 이름
    private int hp;       // 체력
    private int atk;      // 공격력
    private int def;      // 방어력
    private int reward;   // 처치 시 보상 골드

    // ──────────────── [적 목록 - 고정 배열] ────────────────
    private static final Enemy[] enemies = {
            new Enemy("약해용", 50, 10, 0, 10),        // 기본 약한 적
            new Enemy("단단해용", 150, 30, 0, 30),     // 체력이 많은 적
            new Enemy("나쌔용", 100, 50, 0, 50),       // 공격력이 높은 적
            new Enemy("화나용", 200, 80, 20, 100),     // 공격/방어 밸런스
            new Enemy("안아줘용", 300, 130, 30, 200),  // 중간 보스
            new Enemy("보스에용", 1000, 250, 100, 1400) // 최종 보스
    };

    // ──────────────── [생성자] ────────────────
    public Enemy(String name, int hp, int atk, int def, int reward) {
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.reward = reward;
    }

    // ──────────────── [Getter / Setter] ────────────────
    public String getName()            { return name; }
    public void setName(String name)   { this.name = name; }

    public int getHp()                 { return hp; }
    public void setHp(int hp)          { this.hp = hp; }

    public int getDef()                { return def; }
    public void setDef(int def)        { this.def = def; }

    public int getAtk()                { return atk; }
    public void setAtk(int atk)        { this.atk = atk; }

    public int getReward()            { return reward; }
    public void setReward(int reward) { this.reward = reward; }

    // ──────────────── [정보 출력] ────────────────

    /** 적의 상태 정보 출력 */
    public void enemiesInfo() {
        System.out.println("***********************************");
        System.out.println("           이름 : " + name);
        System.out.println("체력 : " + hp + " | 공격력 : " + atk + " | 방어력 : " + def);
    }

    /** 모든 적 정보 출력 */
    public static void showAllEnemies() {
        for (Enemy enemy : enemies) enemy.enemiesInfo();
    }

    // ──────────────── [행동 및 상태] ────────────────

    /** 적이 캐릭터를 공격하는 메서드 */
    public void attack(MyCharacter player) {
        int damage = Math.max(1, this.getAtk() - player.getDef()); // 최소 1 데미지
        System.out.println(name + "의 공격! " + player.getJob() + "에게 " + damage + "의 피해!");
        player.loseHp(damage);
    }

    /** 적이 살아있는지 확인 */
    public boolean isAlive() {
        return hp > 0;
    }

    // ──────────────── [적 생성 관련] ────────────────

    /** 인덱스를 기반으로 적 복사 생성 */
    public static Enemy getEnemyByIndex(int index) {
        if (index >= 0 && index < enemies.length) {
            Enemy e = enemies[index];
            // 복사본을 반환하여 원본 적 상태는 변경되지 않도록 함
            return new Enemy(e.getName(), e.getHp(), e.getAtk(), e.getDef(), e.getReward());
        } else {
            return null; // 인덱스 범위 초과 시 null 반환
        }
    }

    /** 랜덤한 적 생성 (보스 제외, 0~3번까지) */
    public static Enemy getRandomEnemy() {
        Random rand = new Random();
        int index = rand.nextInt(4); // 0 ~ 3 사이 적 선택
        Enemy e = enemies[index];
        return new Enemy(e.getName(), e.getHp(), e.getAtk(), e.getDef(), e.getReward());
    }
}
