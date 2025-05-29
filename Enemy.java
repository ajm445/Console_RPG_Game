import java.util.Random;

public class Enemy {
    private String name;
    private int hp;
    private int atk;
    private int def;
    private int reward;
    private static final Enemy[] enemies = {
            new Enemy("약해용", 50, 10, 0, 10), // 기본 적
            new Enemy("단단해용", 150, 30, 0, 30), // 체력 많은 적
            new Enemy("나쌔용", 100, 50, 0, 50), // 공격력 높은 적
            new Enemy("화나용", 200, 70, 20,
100), // 밸런스 형 적 1
            new Enemy("안아줘용", 300, 100, 30, 200), // 벨런스 형 적 2
            new Enemy("보스에용", 1000, 250, 100, 700) // 보스
    };

    // 생성자
    public Enemy(String name, int hp, int atk, int def, int reward) {
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.reward = reward;
    }

    // getter, setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    // method
    public void enemiesInfo() {
        System.out.println("***********************************");
        System.out.println("           이름 : " + name);
        System.out.println("체력 : " + hp + " | 공격력 : " + atk + " | 방어력 : " + def);
    }

    public static void showAllEnemies() {
        for (Enemy enemy : enemies) enemy.enemiesInfo();
    }

    public void attack(MyCharacter player) {
        int damage = Math.max(1, this.getAtk() - player.getDef()); // 최소 1 데미지
        System.out.println(name + "의 공격! " + player.getJob() + "에게 " + damage + "의 피해!");
        player.loseHp(damage);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public static Enemy getEnemyByIndex(int index) {
        if (index >= 0 && index < enemies.length) {
            // 복사본 반환 (원본 유지하고 싶다면)
            Enemy e = enemies[index];
            return new Enemy(e.getName(), e.getHp(), e.getAtk(), e.getDef(), e.getReward());
        } else {
            return null;
        }
    }

    // 랜덤 적 생성 메서드
    public static Enemy getRandomEnemy() {
        Random rand = new Random();
        int index = rand.nextInt(4);
        Enemy e = enemies[index];
        return new Enemy(e.getName(), e.getHp(), e.getAtk(), e.getDef(), e.getReward());
    }
}
