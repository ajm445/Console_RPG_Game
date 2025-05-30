public class StoreManager {

    // 아이템 구매 처리 메서드
    public static void purchase(int choice, String job) {
        // 유효한 번호인지 확인 (1~16번)
        if (choice < 1 || choice > 16) {
            System.out.println("1부터 16까지의 번호만 입력 가능합니다.");
            Store.store(); // 상점으로 다시 이동
            return;
        }

        // 선택한 아이템과 가격 가져오기
        Item selectedItem = ItemManager.getItems()[choice - 1];
        int price = ItemManager.getPrice(choice - 1);

        // 직업별 장비 가능한 아이템 설정 (무기 한정)
        boolean isAllowed = switch (job) {
            case "전사" -> choice >= 1 && choice <= 3;   // 전사: 나무 막대기 ~ 대검
            case "도적" -> choice >= 4 && choice <= 6;   // 도적: 단검류
            case "마법사" -> choice >= 7 && choice <= 9; // 마법사: 지팡이류
            default -> false;
        };

        // 방어구(10~13)와 물약(14~16)은 직업 관계없이 구매 가능
        if (choice >= 10 && choice <= 13 || choice >= 14) isAllowed = true;

        // 직업이 아이템을 사용할 수 없는 경우
        if (!isAllowed) {
            System.out.println("해당 직업은 이 아이템을 사용할 수 없습니다.");
            Store.store(); // 상점으로 돌아감
            return;
        }

        // 공격/방어 아이템 처리
        if (choice <= 14) {
            // 이미 구매한 아이템인지 확인
            if (User.currentUser.hasPurchased(choice - 1)) {
                System.out.print("이미 구매한 아이템입니다. 교체하시겠습니까? (Y|N): ");
                char confirm = Store.in.next().charAt(0);
                Store.in.nextLine(); // 입력 버퍼 비우기

                if (confirm != 'Y' && confirm != 'y') {
                    Store.store(); // 취소 시 상점으로
                    return;
                }
            } else {
                // 구매한 적 없으면 골드 확인 후 차감
                if (User.currentUser.getGold() < price) {
                    System.out.println("골드가 부족합니다. 현재 골드: " + User.currentUser.getGold());
                    Store.store(); // 상점으로 복귀
                    return;
                }
                User.currentUser.loseGold(price); // 골드 차감
            }

            // 구매 처리
            User.currentUser.setPurchased(choice - 1); // 구매 여부 저장

            // 무기 장착
            if (selectedItem.isWeapon()) {
                User.currentUser.setAtkItem(choice - 1);
                System.out.println("공격 아이템 장착 완료!");
            } else {
                // 방어구 장착
                MyCharacter ch = User.currentUser.getMyCharacter();
                User.currentUser.setDefItem(choice - 1);
                ch.setMaxHp(ch.getBaseMaxHp()); // 방어구 장착 시 최대 체력 기본값으로 설정
                if (ch.getHp() > ch.getMaxHp()) {
                    ch.loseHp(ch.getHp() - ch.getMaxHp()); // 체력 초과 시 조정
                }

                System.out.println("방어 아이템 장착 완료!");

                // 고대 갑옷 특수 효과: 최대 체력 +100
                if (selectedItem.getName().equals("고대 갑옷")) {
                    ch.setMaxHp(ch.getBaseMaxHp() + 100);
                    System.out.println("고대 갑옷 착용! 최대 체력이 100 증가합니다.");
                }
            }

            // 아이템 구매 완료 메시지
            System.out.println("[" + choice + "번 아이템 구매 및 장착 완료]");
        } else {
            // 회복 아이템 처리
            User.currentUser.loseGold(price);
            User.currentUser.getMyCharacter().addHp(selectedItem.getHp());
            System.out.println("체력 물약 사용! 체력 " + selectedItem.getHp() + " 회복됨.");
        }

        // 상태 저장 (유저, 캐릭터 정보 파일에 저장)
        UserManager.saveUser(User.currentUser);
        UserManager.saveCharacter(User.currentUser.getId(), User.currentUser.getMyCharacter());

        // 게임 메인 화면으로 복귀
        GameManager.GameStart();
    }
}
