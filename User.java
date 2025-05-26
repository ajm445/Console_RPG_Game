import java.util.InputMismatchException;
import java.util.Scanner;

public class User {
    public static UserManager userManager = new UserManager();
    public static User currentUser;
    private boolean login = false;
    private String id;
    private String pw;
    private MyCharacter myCharacter;
    private int gold = 100;
    private int atkItem = -1; // index 기반 장착 아이템
    private int defItem = -1;
    private boolean[] itemPurchased = new boolean[Item.getItems().length]; // 아이템 구매 여부

    // 생성자
    public User(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    // getter, setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public MyCharacter getMyCharacter() {
        return myCharacter;
    }

    public void setMyCharacter(MyCharacter myCharacter) {
        this.myCharacter = myCharacter;
    }

    public int getGold() {
        return gold;
    }

    // method
    public boolean hasCharacter() {
        return this.myCharacter != null;
    }

    public void printInfo() {
        String jobInfo = (myCharacter != null) ? myCharacter.getJob() : "없음";
        System.out.println("ID : " + id + " | PW : " + pw + " | Job : " + jobInfo);
    }

    public static void signUp() {  // 회원가입 -> 공백도 계정생성되어 개선 : GPT 이용
        Scanner in = new Scanner(System.in);
        String id;
        System.out.print("생성할 플레이어 아이디를 입력해주세요 : ");
        id = in.nextLine().trim(); // 양쪽 공백 제거
        if(id.isEmpty()) {
            System.out.println("아이디는 공백이 될 수 없습니다.");
            return;
        }
        for(User player : userManager.Users()) {
            if(player != null && player.getId().equals(id)) {
                System.out.println("이미 존재하는 아이디 입니다.");
                Main.mainMenu();
                return;
            }
        }
        String pw;
        System.out.print("비밀번호를 설정해주세요 : ");
        pw = in.nextLine().trim(); // 양쪽 공백 제거
        if(pw.isEmpty()) {
            System.out.println("비밀번호는 공백이 될 수 없습니다.");
            return;
        }
        User user = new User(id, pw);
        userManager.addUser(user);
        System.out.println("계정 생성 완료!");
    }

    public static void signIn() {  // 로그인
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.print("로그인할 플레이어 아이디를 입력해주세요 : ");
            String id = in.nextLine();
            System.out.print("비밀번호를 입력해주세요 : ");
            String pw = in.nextLine();
            for(User user : userManager.Users()) {
                if(user != null && user.getId().equals(id)) {  // 플레이어가 null 아니고 가져오는 이름과 같으면 성립
                    if(user.getPw().equals(pw)) {  // 플레이어의 비밀번호가 가져오는 비밀번호와 같으면 성립
                        currentUser = user;
                        System.out.println(id + " 계정 로그인 성공!");
                        User.currentUser.setLogin(true);
                        Main.mainMenu();
                        break;
                    }
                }
            } System.out.println("로그인 실패!!"); Main.mainMenu(); return;
        }
    }

    public static void modifyId() {  // 아이디 변경하기
        Scanner in = new Scanner(System.in);
        System.out.println("현재 아이디 : " + User.currentUser.getId());
        System.out.print("바꿀 아이디를 입력하세요 : ");
        String newId = in.nextLine();
        System.out.print("비밀번호를 입력하세요 : ");
        String pw = in.nextLine();
        for (User user : userManager.Users()) {
            if (user != null && user.getId().equals(newId)) {
                System.out.println("이미 존재하는 아이디 입니다.");
                Main.mainMenu();
                return;
            }
            if (user != null && user.getPw().equals(pw)) {
                currentUser.setId(newId);
                System.out.println("아이디가 성공적으로 변경되었습니다.");
                Main.mainMenu();
            } else {
                System.out.println("비밀번호가 맞지 않습니다.");
                Main.mainMenu();
                return;
            }
        }
    }

    public static void modifyPw() {  // 비밀번호 변경하기
        Scanner in = new Scanner(System.in);
        System.out.print("기존 비밀번호를 입력하세요 : ");
        String pw = in.nextLine();
        for(User user : userManager.Users()) {
            if(user.getPw().equals(pw)) {
                System.out.print("바꿀 비밀번호를 입력하세요 : ");
                String newPw = in.nextLine();
                currentUser.setPw(newPw);
                System.out.println("비밀번호가 성공적으로 변경되었습니다.");
                Main.mainMenu();
            } else {
                System.out.println("비밀번호가 맞지 않습니다.");
                Main.mainMenu();
                return;
            }
        }
    }

    public static void deleteId() {  // 유저 탈퇴하기
        Scanner in = new Scanner(System.in);
        System.out.print("정말로 탈퇴하겠습니까? (Y | N) : ");
        try {
            char check = in.next().charAt(0);
            if(check == 'Y' || check == 'y') {
                User user = currentUser;
                userManager.removeUser(user);
                System.out.println("계정이 탈퇴되었습니다.");
                User.currentUser.setLogin(false);
                Main.mainMenu();
            }
            else if(check == 'N' || check == 'n') {
                System.out.println("회원탈퇴를 취소했습니다.");
                in.nextLine();
                Main.mainMenu();
            }
            else System.out.println("잘못된 입력입니다."); User.myPage();
        } catch(InputMismatchException e) {
            System.out.println("잘못된 입력입니다! 숫자만 입력해주세요!");
        }
    }


    public static void myPage() {  // 마이페이지 보기
        String id = User.currentUser.getId();
        int totalWidth = 34;
        String idLine = "아이디 : " + id;
        int padding = (totalWidth - idLine.length()) / 2;
        System.out.print("""
                ***********************************
                          Console RPG Game
                              마이페이지
                """);
        System.out.println(" ".repeat(padding) + idLine);
        System.out.print("""
                ***********************************
                           1. 아이디 변경
                           2. 비밀번호 변경
                            3. 회원 탈퇴
                            4. 뒤로가기
                ***********************************
                Enter number
                """);
        System.out.print(">> ");
        Scanner in = new Scanner(System.in);
        while(true) {
            try {
                int num = in.nextInt();
                switch (num) {
                    case 1:
                        System.out.println("[아이디 변경]");
                        User.modifyId();
                        break;
                    case 2:
                        System.out.println("[비밀번호 변경]");
                        User.modifyPw();
                        break;
                    case 3:
                        System.out.println("[회원탈퇴]");
                        User.deleteId();
                        break;
                    case 4:
                        if(User.currentUser.isLogin()) {
                            System.out.println("[뒤로가기]");
                            Main.mainMenu();
                        } break;
                    default:
                        System.out.println("1부터 4까지의 숫자 하나만 입력해주세요!");
                        User.myPage();
                }
            } catch(InputMismatchException e) {
                System.out.println("잘못된 입력입니다! 숫자만 입력해주세요!");
                in.nextLine();
                User.myPage();
            }
        }
    }

    public void addGold(int gold) {
        this.gold += gold;
    }

    public boolean hasPurchased(int index) {
        return itemPurchased[index];
    }

    public void setPurchased(int index) {
        itemPurchased[index] = true;
    }

    public int getAtkItem() {
        return atkItem;
    }

    public void setAtkItem(int index) {
        myCharacter.setAtkItemIndex(index);
    }

    public int getDefItem() {
        return defItem;
    }

    public void setDefItem(int index) {
        myCharacter.setDefItemIndex(index);
    }

    public void returnUserState() {  // GPT 이용
        myCharacter.getAtk();
        myCharacter.getDef();
        myCharacter.reHp();
    }
}
