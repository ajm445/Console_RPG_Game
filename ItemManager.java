public class ItemManager {
    private static final Item[] items = {
            new Item("나무 막대기",10,0,0),
            new Item("철 검",30,0,0),
            new Item("대검",90,0,0),
            new Item("나무 단검",20,0,0),
            new Item("철 단검",40,0,0),
            new Item("특화 단검",100,0,0),
            new Item("낡은 지팡이",40,0,0),
            new Item("일반 지팡이",70,0,0),
            new Item("고대 지팡이",120,0,0),
            new Item("고대 무기",250,0,0),
            new Item("나무 갑옷",0,20,0),
            new Item("철 갑옷",0,60,0),
            new Item("잔해 갑옷",0,100,0),
            new Item("고대 갑옷",0,200,100),
            new Item("작은 물약",0,0,30),
            new Item("큰 물약",0,0,70)
    };

    private static final int[] itemPrices = {
            200,1000,3000, 200,1000,3000, 200,1000,3000,5000,
            200,1000,2000,6000,
            50,150
    };

    public static Item[] getItems() {
        return items;
    }

    public static int getPrice(int index) {
        return itemPrices[index];
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

    private static String formatItemInfo(Item item) {
        StringBuilder sb = new StringBuilder();
        sb.append(item.getName()).append(" [");
        if (item.isWeapon()) {
            sb.append("공격력 +").append(item.getAtk());
        } else if (item.isArmor()) {
            sb.append("방어력 +").append(item.getDef());
        } else if (item.isPotion()) {
            sb.append("체력 회복 +").append(item.getHp());
        }
        sb.append("] - ").append(getPrice(getItemIndex(item))).append("G");
        return sb.toString();
    }

    private static int getItemIndex(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].getName().equals(item.getName())) return i;
        }
        return -1;
    }

    private static String padRight(String str, int length) {
        if (str.length() >= length) return str;
        return str + " ".repeat(length - str.length());
    }
}
