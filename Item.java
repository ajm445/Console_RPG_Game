public class Item {
    private String name;
    private int atk;
    private int def;
    private int hp;
    private static Item[] items = {
            new Item("나무 막대기",10,0,0),
            new Item("철 검",30,0,0),
            new Item("대검",100,0,0),
            new Item("나무 단검",20,0,0),
            new Item("철 단검",40,0,0),
            new Item("특화 단검",100,0,0),
            new Item("낡은 지팡이",30,0,0),
            new Item("일반 지팡이",60,0,0),
            new Item("고대 지팡이",120,0,0),
            new Item("고대 무기",200,0,0),
            new Item("나무 갑옷",0,30,0),
            new Item("철 갑옷",0,60,0),
            new Item("잔해 갑옷",0,100,0),
            new Item("고대 갑옷",0,200,0),
            new Item("작은 물약",0,0,50),
            new Item("큰 물약",0,0,100)
    };

    private static int[] itemPrices = {  // GPT 이용
            100,500,1000, 100,500,1000, 100,500,10000,2000, // 0~9 공격
            200,500,1000,2000, // 10~13 방어
            50,150 // 14~15 회복
    };

    // 생성자
    public Item(String name, int atk, int def, int hp) {
        this.name = name;
        this.atk = atk;
        this.def = def;
        this.hp = hp;
    }

    //getter, setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public static Item[] getItems() {
        return items;
    }

    //method
    private static String formatItemInfo(Item item) {  // 깔끔한 아이템 정보 보기 : GPT 이용
        StringBuilder sb = new StringBuilder();
        sb.append(item.getName()).append(" [");
        if (item.isWeapon()) {
            sb.append("공격력 +").append(item.getAtk());
        } else if (item.isArmor()) {
            sb.append("방어력 +").append(item.getDef());
        } else if (item.isPotion()) {
            sb.append("체력 회복 +").append(item.getHp());
        }
        sb.append("]");
        return sb.toString();
    }

    public static void showAllItems() {
        System.out.println("************************************************************");
        System.out.println("*                      [아이템 목록]                        *");
        System.out.println("************************************************************");

        for (int i = 0; i < items.length; i += 2) {
            String line = "* " + padRight(formatItemInfo(items[i]), 28);
            if (i + 1 < items.length) {
                line += "* " + padRight(formatItemInfo(items[i + 1]), 28);
            } else {
                line += padRight("", 28);
            }
            System.out.println(line);
        }

        System.out.println("************************************************************\n");
    }

    private static String padRight(String str, int length) {
        if (str.length() >= length) return str;
        return str + " ".repeat(length - str.length());
    }

    public static int getPrice(int index) {
        return itemPrices[index];
    }

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
