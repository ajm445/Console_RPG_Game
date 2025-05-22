public class Enemy {
    private String name;
    private int hp;
    private int atk;
    private int def;
    private Enemy[] enemies = {
            new Enemy("약해용",50,10,0), // 약한 적
            new Enemy("느려용",200,10,30), // 체력 많고 느린 적
            new Enemy("빨라용",50,50,20), // 체력 적고 빠른 적
            new Enemy("적당해용",100,30,50), // 벨런스 형 적
            new Enemy("보스에용",1000,100,100) // 보스
    };

    // 생성자
    public Enemy(String name, int hp, int atk, int def) {
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
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

    // method
    public void enemyInfo() {
        for (Enemy enemy : enemies) {
            System.out.println(enemy);
        }
    }
}
