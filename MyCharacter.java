import java.util.Arrays;

public class MyCharacter {
    private String job;
    private int hp;
    private int atk;
    private int def;
    private int maxHp;
    private int atkItemIndex = -1; // -1이면 장착이 안된 상태
    private int defItemIndex = -1;

    // 생성자
    public MyCharacter(String job, int hp, int atk, int def) {
        this.job = job;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.maxHp = hp;
    }
    // 중복 정의
    public MyCharacter(String job, int hp, int atk, int def, int storedAtk, int storedDef) {
        this.job = job;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.atkItemIndex = storedAtk;
        this.defItemIndex = storedDef;
        this.maxHp = hp;
    }

    // getter
    public String getJob() {
        return job;
    }

    public int getHp() {
        return hp;
    }

    public int getAtk() {
        int bonus = 0;
        if(atkItemIndex != - 1) {
            bonus = ItemManager.getItems()[atkItemIndex].getAtk();
        }
        return atk + bonus;
    }

    public int getDef() {
        int bonus = 0;
        if(defItemIndex != - 1) {
            bonus = ItemManager.getItems()[defItemIndex].getDef();
        }
        return def + bonus;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getAtkItemIndex() {
        return atkItemIndex;
    }

    public void setAtkItemIndex(int atkItemIndex) {
        this.atkItemIndex = atkItemIndex;
    }

    public int getDefItemIndex() {
        return defItemIndex;
    }

    public void setDefItemIndex(int defItemIndex) {
        this.defItemIndex = defItemIndex;
    }

    // method
    public static MyCharacter createWarrior() {
        return new MyCharacter("전사",100,20,20);
    }

    public static MyCharacter createThief() {
        return new MyCharacter("도적",80,30,10);
    }

    public static MyCharacter createMage() {
        return new MyCharacter("마법사",60,50,0);
    }

    public void addHp(int heal) {
        this.hp += heal;
        if(hp > maxHp) hp = maxHp;
    }

    public void loseHp(int damage) {
        this.hp -= damage;
        if(hp < 0) hp = 0;
    }

    public void characterInfo() {
        System.out.println("***********************************");
        System.out.println("직업 : " + job);
        System.out.println("HP : " + hp);
        System.out.println("공격력 : " + atk);
        System.out.println("방어력 : " + def);
        System.out.println("***********************************");
    }

    public void state() {
        Item weapon = ItemManager.getItems()[atkItemIndex];
        Item armor = ItemManager.getItems()[defItemIndex];
        System.out.println("***********************************");
        System.out.println("직업 : " + job);
        System.out.println("HP : " + getHp());
        System.out.println("공격력 : " + atk + "(+" + weapon.getAtk() + ")");
        System.out.println("방어력 : " + def + "(+" + armor.getDef() + ")");
        // 착용 무기 정보 출력
        if (atkItemIndex != -1) {
            System.out.println("장착 무기 : " + weapon.getName() + " (공격력 +" + weapon.getAtk() + ")");
        } else {
            System.out.println("장착 무기 : 없음");
        }
        // 착용 방어구 정보 출력
        if (defItemIndex != -1) {
            System.out.println("장착 방어구 : " + armor.getName() + " (방어력 +" + armor.getDef() + ")");
        } else {
            System.out.println("장착 방어구 : 없음");
        }
    }

    public void attack(Enemy enemy) {
        int damage = Math.max(1, this.getAtk() - enemy.getDef()); // 최소 1 데미지
        System.out.println(job + "의 공격! " + enemy.getName() + "에게 " + damage + "의 피해!");
        enemy.setHp(enemy.getHp() - damage);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void reHp() {
        this.hp = this.maxHp;
    }

    public void fightState() {
        System.out.println("내 Hp : " + getHp());
        System.out.println("--------------");
    }
}
