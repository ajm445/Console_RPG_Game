public class Item {
    // ──────────────── [필드: 아이템 정보] ────────────────
    private String name; // 아이템 이름
    private int atk;     // 공격력 증가 수치 (무기 전용)
    private int def;     // 방어력 증가 수치 (방어구 전용)
    private int hp;      // 체력 회복 수치 (포션 전용)

    // ──────────────── [생성자] ────────────────
    /**
     * 아이템 객체 생성자
     * @param name 아이템 이름
     * @param atk 공격력 증가 수치
     * @param def 방어력 증가 수치
     * @param hp 체력 회복 수치
     */
    public Item(String name, int atk, int def, int hp) {
        this.name = name;
        this.atk = atk;
        this.def = def;
        this.hp = hp;
    }

    // ──────────────── [Getter / Setter] ────────────────
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAtk() { return atk; }
    public void setAtk(int atk) { this.atk = atk; }

    public int getDef() { return def; }
    public void setDef(int def) { this.def = def; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    // ──────────────── [아이템 타입 확인 메서드] ────────────────

    /**
     * 이 아이템이 무기인지 확인 (공격력만 존재)
     * @return 무기이면 true
     */
    public boolean isWeapon() {
        return atk > 0 && def == 0 && hp == 0;
    }

    /**
     * 이 아이템이 방어구인지 확인 (방어력만 존재)
     * @return 방어구이면 true
     */
    public boolean isArmor() {
        return def > 0 && atk == 0 && hp == 0;
    }

    /**
     * 이 아이템이 포션인지 확인 (체력 회복 아이템)
     * @return 포션이면 true
     */
    public boolean isPotion() {
        return hp > 0;
    }
}
