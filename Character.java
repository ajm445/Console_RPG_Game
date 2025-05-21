public class Character {
    private String job;
    private int hp;
    private int atk;
    private int def;

    // 생성자
    public Character(String job, int hp, int atk, int def) {
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
        return atk;
    }

    public int getDef() {
        return def;
    }

    // method
    public static Character createWarrior() {
        return new Character("전사",100,20,50);
    }

    public static Character createThief() {
        return new Character("도적",80,30,30);
    }

    public static Character createMage() {
        return new Character("마법사",50,40,20);
    }

    public void characterInfo() {
        System.out.println("***********************************");
        System.out.println("직업 : " + job);
        System.out.println("HP : " + hp);
        System.out.println("공격력 : " + atk);
        System.out.println("방어력 : " + def);
        System.out.println("***********************************");
    }
}
