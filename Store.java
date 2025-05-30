import java.util.Scanner;

public class Store {
    static Scanner in = new Scanner(System.in);

    // 상점 진입 메서드
    public static void store() {
        // 현재 유저의 직업 정보를 가져옴
        String job = User.currentUser.getMyCharacter().getJob();

        // 상점 메뉴 출력
        System.out.print("""
                ***********************************
                          Console RPG Game
                           == 무기 상점 ==
                ***********************************
                            * 무기 정보(i)
                             * 뒤로가기(b)
                ***********************************
                            [공격력 아이템]
                        
                        1. 나무 막대기 : 200골드
                        2. 철검 : 1000골드
                        3. 대검 : 2500골드
                        4. 나무 단검 : 200골드
                        5. 철 단검 : 1000골드
                        6. 특화 단검 : 2500골드
                        7. 낡은 지팡이 : 200골드
                        8. 일반 지팡이 : 1000골드
                        9. 고대 지팡이 : 2500골드
                        10. 고대 무기 : 5000골드
                
                ***********************************
                            [방어력 아이템]
                
                        11. 나무 갑옷 : 200골드
                        12. 철 갑옷 : 1000골드
                        13. 잔해 갑옷 : 2000골드
                        14. 고대 갑옷 : 6000골드
                
                ***********************************
                              [체력 물약]
                    
                        15. 작은 물약 : 50골드
                        16. 큰 물약 : 150골드
                        
                ***********************************
                Enter number
                """);
        System.out.print(">> ");
        String input = in.nextLine(); // 사용자 입력 받기

        // 입력에 따른 분기 처리
        switch (input) {
            case "i" -> {
                // 무기 정보 보기
                System.out.println("[무기 정보]");
                ItemManager.showAllItems(); // 모든 아이템 정보 출력
                store(); // 다시 상점으로
            }
            case "b" -> {
                // 뒤로가기 선택 시 메인 게임 화면으로 이동
                System.out.println("[뒤로가기]");
                GameManager.GameStart();
            }
            default -> {
                try {
                    // 숫자 입력 시 아이템 구매 처리
                    int choice = Integer.parseInt(input);
                    StoreManager.purchase(choice, job); // StoreManager에서 구매 처리
                } catch (NumberFormatException e) {
                    // 잘못된 입력 처리
                    System.out.println("숫자 또는 i/b만 입력 가능합니다.");
                    store(); // 다시 상점으로
                }
            }
        }
    }
}
