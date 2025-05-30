public class ItemManager {
    // ──────────────── [고정 아이템 목록] ────────────────
    // 각 아이템은 (이름, 공격력, 방어력, 체력 회복량)으로 구성됨
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

    // ──────────────── [아이템 가격 정보] ────────────────
    // items 배열과 인덱스 일치
    private static final int[] itemPrices = {
            200,1000,3000, 200,1000,3000, 200,1000,3000,5000,
            200,1000,2000,6000,
            50,150
    };

    // ──────────────── [Getter] ────────────────

    /** 아이템 배열 반환 */
    public static Item[] getItems() {
        return items;
    }

    /** 특정 인덱스의 아이템 가격 반환 */
    public static int getPrice(int index) {
        return itemPrices[index];
    }

    // ──────────────── [아이템 목록 출력] ────────────────

    /** 아이템 전체를 보기 좋게 2열로 출력 */
    public static void showAllItems() {
        System.out.println("************************************************************");
        System.out.println("*                      [아이템 목록]                        *");
        System.out.println("************************************************************");

        // 2개씩 출력 (좌우 정렬)
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

    // ──────────────── [출력 포맷 생성 메서드] ────────────────

    /**
     * 아이템 정보를 문자열로 포맷
     * @param item 아이템 객체
     * @return 이름 + 효과 + 가격 형식의 문자열
     */
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

    /**
     * 아이템 배열에서 해당 아이템의 인덱스 반환
     * @param item 찾고자 하는 아이템
     * @return 인덱스 (없으면 -1)
     */
    private static int getItemIndex(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].getName().equals(item.getName())) return i;
        }
        return -1;
    }

    /**
     * 문자열을 오른쪽 여백을 포함해 길이 맞춰 정렬
     * @param str 원본 문자열
     * @param length 총 길이
     * @return 오른쪽 패딩 포함 문자열
     */
    private static String padRight(String str, int length) {
        if (str.length() >= length) return str;
        return str + " ".repeat(length - str.length());
    }
}
