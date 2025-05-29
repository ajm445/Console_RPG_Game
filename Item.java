public class Item {
    private String name;
    private int atk;
    private int def;
    private int hp;

    public Item(String name, int atk, int def, int hp) {
        this.name = name;
        this.atk = atk;
        this.def = def;
        this.hp = hp;
    }

    // Getter, Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAtk() { return atk; }
    public void setAtk(int atk) { this.atk = atk; }

    public int getDef() { return def; }
    public void setDef(int def) { this.def = def; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public boolean isWeapon() {
        return atk > 0 && def == 0 && hp == 0;
    }

    public boolean isArmor() {
        return def > 0 && atk == 0 && hp == 0;
    }

    public boolean isPotion() {
        return hp > 0;
    }
}
