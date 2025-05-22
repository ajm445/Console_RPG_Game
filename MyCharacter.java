public class MyCharacter {
    private String job;
    private int hp;
    private int atk;
    private int def;

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
        return atk;
    }

    public int getDef() {
        return def;
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

    public void plusAtk(int atk) {
        this.atk += atk;
    }

    public void plusDef(int Def) {
        this.def += def;
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
        System.out.println("직업 : " + User.currentUser.getMyCharacter().getJob());
        System.out.println("HP : " + User.currentUser.getMyCharacter().getHp());
        System.out.println("공격력 : " + User.currentUser.getMyCharacter().getAtk());
        System.out.println("방어력 : " + User.currentUser.getMyCharacter().getDef());
    }
}
