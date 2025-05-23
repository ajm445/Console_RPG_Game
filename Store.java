import java.util.Scanner;

public class Store {  // 직업별 무기 선택 가능 유무 구현 : GPT 이용
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
        if (input.equals("i")) {
            System.out.println("[무기 정보]");
            Item.showAllItems();
            Store.store();
            return;
        } else if (input.equals("b")) {
            System.out.println("[뒤로가기]");
            GameManager.GameStart();
            return;
        }
        try {
            int choice = Integer.parseInt(input);
            if(choice < 1 || choice > 16) {
                System.out.println("1부터 16까지의 번호만 입력 가능합니다.");
                Store.store();
                return;
            }
            Item selectedItem = Item.getItems()[choice - 1];
            int price = Item.getPrice(choice -1);
            boolean isAllowed = switch (job) {
                case "전사" -> choice >= 1 && choice <= 3;
                case "도적" -> choice >= 4 && choice <= 6;
                case "마법사" -> choice >= 7 && choice <= 9;
                default -> false;
            };
            if (choice >= 10 && choice <= 13) isAllowed = true;
            if (choice >= 14) isAllowed = true; // 회복 아이템은 누구나 가능
            if (!isAllowed) {
                System.out.println("해당 직업은 이 아이템을 사용할 수 없습니다.");
                Store.store();
                return;
            }
            if (User.currentUser.getGold() < price) {
                System.out.println("골드가 부족합니다. 현재 골드: " + User.currentUser.getGold());
                Store.store();
                return;
            }
            if (choice <= 13) { // 공격/방어 아이템
                if (User.currentUser.hasPurchased(choice - 1)) {
                    System.out.print("이미 구매한 아이템입니다. 교체하시겠습니까? (Y|N): ");
                    char confirm = in.next().charAt(0);
                    if (confirm != 'Y' && confirm != 'y') {
                        Store.store();
                        return;
                    }
                }
                User.currentUser.setPurchased(choice - 1);
                User.currentUser.addGold(-price);
                if(selectedItem.isWeapon()) {
                    User.currentUser.setAtkItem(choice - 1);
                    System.out.println("공격 아이템 장착 완료!");
                } else {
                    User.currentUser.setDefItem(choice - 1);
                    System.out.println("방어 아이템 장착 완료!");
                }

                System.out.println("[" + choice + "번 아이템 구매 및 장착 완료]");
            } else { // 회복 아이템
                User.currentUser.addGold(-price);
                User.currentUser.getMyCharacter().addHp(selectedItem.getHp());
                System.out.println("체력 물약 사용! 체력 " + selectedItem.getHp() + " 회복됨.");
            }

            GameManager.GameStart();
        } catch (NumberFormatException e) {
            System.out.println("숫자 또는 i/b만 입력 가능합니다.");
            Store.store();
        }
    }
}
