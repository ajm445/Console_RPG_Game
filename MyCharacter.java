public class MyCharacter {
    // ──────────────── [캐릭터 기본 속성] ────────────────
    private String job;           // 직업 이름
    private int hp;               // 현재 체력
    private int atk;              // 기본 공격력
    private int def;              // 기본 방어력
    private int maxHp;            // 현재 최대 체력 (포션 등으로 증가 가능)
    private int baseMaxHp;        // 초기 최대 체력 (직업 기준)

    private int atkItemIndex = -1; // 장착된 무기 인덱스 (-1이면 미장착)
    private int defItemIndex = -1; // 장착된 방어구 인덱스

    // ──────────────── [생성자] ────────────────

    /** 캐릭터 생성자 (기본 버전) */
    public MyCharacter(String job, int hp, int atk, int def) {
        this.job = job;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.maxHp = hp;
        this.baseMaxHp = hp;
    }

    /** 캐릭터 생성자 (장비 정보 포함) */
    public MyCharacter(String job, int hp, int atk, int def, int storedAtk, int storedDef) {
        this.job = job;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.atkItemIndex = storedAtk;
        this.defItemIndex = storedDef;
        this.maxHp = hp;
        this.baseMaxHp = hp;
    }

    // ──────────────── [Getter / Setter] ────────────────
    public String getJob() { return job; }
    public int getHp() { return hp; }

    /** 장착 무기 포함한 공격력 반환 */
    public int getAtk() {
        int bonus = 0;
        if (atkItemIndex != -1) {
            bonus = ItemManager.getItems()[atkItemIndex].getAtk();
        }
        return atk + bonus;
    }

    /** 장착 방어구 포함한 방어력 반환 */
    public int getDef() {
        int bonus = 0;
        if (defItemIndex != -1) {
            bonus = ItemManager.getItems()[defItemIndex].getDef();
        }
        return def + bonus;
    }

    public int getMaxHp() { return maxHp; }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }

    public int getBaseMaxHp() { return baseMaxHp; }
    public void setBaseMaxHp(int baseMaxHp) { this.baseMaxHp = baseMaxHp; }

    public int getAtkItemIndex() { return atkItemIndex; }
    public void setAtkItemIndex(int atkItemIndex) { this.atkItemIndex = atkItemIndex; }

    public int getDefItemIndex() { return defItemIndex; }
    public void setDefItemIndex(int defItemIndex) { this.defItemIndex = defItemIndex; }

    // ──────────────── [직업별 캐릭터 생성] ────────────────

    public static MyCharacter createWarrior() {
        return new MyCharacter("전사", 100, 20, 20);
    }

    public static MyCharacter createThief() {
        return new MyCharacter("도적", 80, 30, 10);
    }

    public static MyCharacter createMage() {
        return new MyCharacter("마법사", 60, 50, 0);
    }

    // ──────────────── [HP 조작 메서드] ────────────────

    /** 체력 회복 (최대 체력 초과 방지) */
    public void addHp(int heal) {
        this.hp += heal;
        if (hp > maxHp) hp = maxHp;
    }

    /** 데미지를 받아 체력 감소 */
    public void loseHp(int damage) {
        this.hp -= damage;
        if (hp < 0) hp = 0;
    }

    /** 체력 완전 회복 */
    public void reHp() {
        this.hp = this.maxHp;
    }

    // ──────────────── [출력용 메서드] ────────────────

    /** 캐릭터 생성 직후 기본 정보 출력 */
    public void characterInfo() {
        System.out.println("***********************************");
        System.out.println("직업 : " + job);
        System.out.println("HP : " + hp);
        System.out.println("공격력 : " + atk);
        System.out.println("방어력 : " + def);
        System.out.println("***********************************");
    }

    /** 캐릭터 현재 상태 출력 (장비 포함) */
    public void state() {
        System.out.println("***********************************");
        System.out.println("직업 : " + job);
        System.out.println("HP : " + getHp() + " | " + getMaxHp());

        if (atkItemIndex != -1) {
            Item weapon = ItemManager.getItems()[atkItemIndex];
            System.out.println("공격력 : " + atk + "(+" + weapon.getAtk() + ") | " + weapon.getName());
        } else {
            System.out.println("공격력 : " + atk);
        }

        if (defItemIndex != -1) {
            Item armor = ItemManager.getItems()[defItemIndex];
            System.out.println("방어력 : " + def + "(+" + armor.getDef() + ") | " + armor.getName());
        } else {
            System.out.println("방어력 : " + def);
        }
    }

    /** 전투 중 HP 출력 */
    public void fightState() {
        System.out.println("내 Hp : " + getHp());
        System.out.println("--------------");
    }

    // ──────────────── [행동 관련 메서드] ────────────────

    /** 적에게 공격 수행 */
    public void attack(Enemy enemy) {
        int damage = Math.max(1, this.getAtk() - enemy.getDef()); // 최소 1 피해
        System.out.println(job + "의 공격! " + enemy.getName() + "에게 " + damage + "의 피해!");
        enemy.setHp(enemy.getHp() - damage);
    }

    /** 캐릭터가 생존 상태인지 확인 */
    public boolean isAlive() {
        return hp > 0;
    }
}
