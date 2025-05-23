public class MyCharacter {
    private String job;
    private int hp;
    private int atk;
    private int def;
    private int atkItemIndex = -1; // -1이면 장착이 안된 상태
    private int defItemIndex = -1;

    // 생성자
    public MyCharacter(String job, int hp, int atk, int def) {
        this.job = job;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
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
            bonus = Item.getItems()[atkItemIndex].getAtk();
        }
        return atk + bonus;
    }

    public int getDef() {
        int bonus = 0;
        if(defItemIndex != - 1) {
            bonus = Item.getItems()[defItemIndex].getDef();
        }
        return def + bonus;
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
        return new MyCharacter("전사",100,20,50);
    }

    public static MyCharacter createThief() {
        return new MyCharacter("도적",80,30,30);
    }

    public static MyCharacter createMage() {
        return new MyCharacter("마법사",50,40,20);
    }

    public void addHp(int heal) {
        //myCharacter.hp += heal;
    }

    public void loseHp(int damage) {
        //myCharacter.hp -= damage;
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
        System.out.println("***********************************");
        System.out.println("직업 : " + job);
        System.out.println("HP : " + getHp());
        System.out.println("공격력 : " + getAtk());
        System.out.println("방어력 : " + getDef());
    }
}
