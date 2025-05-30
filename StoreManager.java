public class StoreManager {

    public static void purchase(int choice, String job) {
        if (choice < 1 || choice > 16) {
            System.out.println("1부터 16까지의 번호만 입력 가능합니다.");
            Store.store();
            return;
        }

        Item selectedItem = ItemManager.getItems()[choice - 1];
        int price = ItemManager.getPrice(choice - 1);

        boolean isAllowed = switch (job) {
            case "전사" -> choice >= 1 && choice <= 3;
            case "도적" -> choice >= 4 && choice <= 6;
            case "마법사" -> choice >= 7 && choice <= 9;
            default -> false;
        };
        if (choice >= 10 && choice <= 13 || choice >= 14) isAllowed = true;

        if (!isAllowed) {
            System.out.println("해당 직업은 이 아이템을 사용할 수 없습니다.");
            Store.store();
            return;
        }

        if (choice <= 14) { // 공격/방어 아이템
            if (User.currentUser.hasPurchased(choice - 1)) {
                System.out.print("이미 구매한 아이템입니다. 교체하시겠습니까? (Y|N): ");
                char confirm = Store.in.next().charAt(0);
                Store.in.nextLine(); // 버퍼 비우기
                if (confirm != 'Y' && confirm != 'y') {
                    Store.store();
                    return;
                }
            } else {
                if (User.currentUser.getGold() < price) {
                    System.out.println("골드가 부족합니다. 현재 골드: " + User.currentUser.getGold());
                    Store.store();
                    return;
                }
                User.currentUser.loseGold(price);
            }

            User.currentUser.setPurchased(choice - 1);
            if (selectedItem.isWeapon()) {
                User.currentUser.setAtkItem(choice - 1);
                System.out.println("공격 아이템 장착 완료!");
            } else {
                MyCharacter ch = User.currentUser.getMyCharacter();
                User.currentUser.setDefItem(choice - 1);
                ch.setMaxHp(ch.getBaseMaxHp());
                if(ch.getHp() > ch.getMaxHp()) {
                    ch.loseHp(ch.getHp() - ch.getMaxHp());
                }
                System.out.println("방어 아이템 장착 완료!");
                if (selectedItem.getName().equals("고대 갑옷")) {
                    ch.setMaxHp(ch.getBaseMaxHp() + 100);
                    System.out.println("고대 갑옷 착용! 최대 체력이 100 증가합니다.");
                }
            }

            System.out.println("[" + choice + "번 아이템 구매 및 장착 완료]");
        } else { // 회복 아이템
            User.currentUser.loseGold(price);
            User.currentUser.getMyCharacter().addHp(selectedItem.getHp());
            System.out.println("체력 물약 사용! 체력 " + selectedItem.getHp() + " 회복됨.");
        }

        // 상태 저장
        UserManager.saveUser(User.currentUser);
        UserManager.saveCharacter(User.currentUser.getId(), User.currentUser.getMyCharacter());
        GameManager.GameStart();
    }
}
