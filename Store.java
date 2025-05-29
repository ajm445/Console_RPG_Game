import java.util.Scanner;

public class Store {
    static Scanner in = new Scanner(System.in);

    public static void store() {
        String job = User.currentUser.getMyCharacter().getJob();
        System.out.print("""
                ***********************************
                          Console RPG Game
                           == 무기 상점 ==
                ***********************************
                            * 무기 정보(i)
                             * 뒤로가기(b)
                ***********************************
                            [공격력 아이템]
                        
                        1. 나무 막대기 : 100골드
                        2. 철검 : 500골드
                        3. 대검 : 1000골드
                        4. 나무 단검 : 100골드
                        5. 철 단검 : 500골드
                        6. 특화 단검 : 1000골드
                        7. 낡은 지팡이 : 100골드
                        8. 일반 지팡이 : 500골드
                        9. 고대 지팡이 : 10000골드
                        10. 고대 무기 : 2000골드
                
                ***********************************
                            [방어력 아이템]
                
                        11. 나무 갑옷 : 200골드
                        12. 철 갑옷 : 500골드
                        13. 잔해 갑옷 : 1000골드
                        14. 고대 갑옷 : 2000골드
                
                ***********************************
                              [체력 물약]
                    
                        15. 작은 물약 : 50골드
                        16. 큰 물약 : 150골드
                        
                ***********************************
                Enter number
                """);
        System.out.print(">> ");
        String input = in.nextLine();

        switch (input) {
            case "i" -> {
                System.out.println("[무기 정보]");
                ItemManager.showAllItems();
                store();
            }
            case "b" -> {
                System.out.println("[뒤로가기]");
                GameManager.GameStart();
            }
            default -> {
                try {
                    int choice = Integer.parseInt(input);
                    StoreManager.purchase(choice, job); // 구매 로직 위임
                } catch (NumberFormatException e) {
                    System.out.println("숫자 또는 i/b만 입력 가능합니다.");
                    store();
                }
            }
        }
    }
}
