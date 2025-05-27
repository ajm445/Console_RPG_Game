import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Objects;          // null-safe 비교용
import java.util.Scanner;

public class User {
    public static User currentUser;
    private static boolean login = false;
    private String id;
    private String pw;
    private MyCharacter myCharacter;
    private int gold = 100;
    private int atkItem = -1;           // index 기반 장착 아이템
    private int defItem = -1;
    private boolean[] itemPurchased = new boolean[Item.getItems().length];
    private int storedAtkItem = -1;
    private int storedDefItem = -1;

    // 생성자
    public User(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    // ──────────────────────── getter / setter ────────────────────────
    public String getId()              { return id; }
    public void   setId(String id)     { this.id = id; }
    public String getPw()              { return pw; }
    public void   setPw(String pw)     { this.pw = pw; }
    public boolean getLogin() { return login; }
    public void   setLogin(boolean l)  { login = l; }
    public int    getGold()            { return gold; }
    public void   setGold(int gold)    { this.gold = gold; }
    public boolean[] getItemPurchased(){ return itemPurchased; }
    public void   setItemPurchased(boolean[] arr){ this.itemPurchased = arr; }

    public MyCharacter getMyCharacter() { return myCharacter; }
    public void setMyCharacter(MyCharacter c) {
        this.myCharacter = c;
        if (c != null) {          // 저장된 인덱스 재적용
            c.setAtkItemIndex(storedAtkItem);
            c.setDefItemIndex(storedDefItem);
        }
    }
    public void setStoredAtkItem(int idx){
        storedAtkItem = idx;
        if(myCharacter != null) myCharacter.setAtkItemIndex(idx);
    }
    public void setStoredDefItem(int idx){
        storedDefItem = idx;
        if(myCharacter != null) myCharacter.setDefItemIndex(idx);
    }

    // ──────────────────────── 유틸 메서드 ────────────────────────
    /** 숫자 입력을 안전하게 읽는 유틸 */
    private static int safeReadInt(Scanner in){
        while(true){
            System.out.print(">> ");
            try{
                return in.nextInt();
            }catch(InputMismatchException e){
                System.out.println("잘못된 입력입니다! 숫자만 입력해주세요!");
                in.nextLine();    // 버퍼 비우기
            }
        }
    }

    public boolean hasCharacter(){ return myCharacter != null; }

    // ──────────────────────── static 기능 ────────────────────────
    /** 회원가입 */
    public static void signUp(){
        Scanner in = new Scanner(System.in);
        System.out.print("생성할 플레이어 아이디를 입력해주세요 : ");
        String id = in.nextLine().trim();
        if(id.isEmpty()){
            System.out.println("아이디는 공백이 될 수 없습니다.");
            return;
        }

        // 메모리 중복 검사
        for(User u : UserManager.getInstance().Users()){
            if(u != null && Objects.equals(u.getId(), id)){
                System.out.println("이미 존재하는 아이디입니다.");
                return;
            }
        }
        // 파일 중복 검사
        Path userPath = Paths.get(UserManager.getDataDir(),"user_"+id+".txt");
        if(Files.exists(userPath, LinkOption.NOFOLLOW_LINKS)){
            System.out.println("이미 존재하는 아이디입니다.");
            return;
        }

        System.out.print("비밀번호를 설정해주세요 : ");
        String pw = in.nextLine().trim();
        if(pw.isEmpty()){
            System.out.println("비밀번호는 공백이 될 수 없습니다.");
            return;
        }

        User newUser = new User(id, pw);
        UserManager.getInstance().addUser(newUser);
        System.out.println("계정 생성 완료!");
    }

    /** 로그인 */
    public static void signIn(){
        Scanner in = new Scanner(System.in);
        System.out.print("로그인할 플레이어 아이디를 입력해주세요 : ");
        String id = in.nextLine();
        System.out.print("비밀번호를 입력해주세요 : ");
        String pw = in.nextLine();

        User loaded = UserManager.loadUser(id, pw);
        if(loaded != null){
            currentUser = loaded;
            if(!UserManager.getInstance().Users().contains(loaded)){
                UserManager.getInstance().Users().add(loaded);
            }
            User.login = true;
            System.out.println(id+" 계정 로그인 성공!");
        }else{
            System.out.println("로그인 실패!!");
            Main.mainMenu();
        }
    }

    /** 아이디 변경 */
    public static void modifyId(){
        Scanner in = new Scanner(System.in);
        System.out.println("현재 아이디 : " + currentUser.getId());
        System.out.print("바꿀 아이디를 입력하세요 : ");
        String newId = in.nextLine();
        System.out.print("비밀번호를 입력하세요 : ");
        String pw = in.nextLine();

        for(User u : UserManager.getInstance().Users()){
            if(u != null && Objects.equals(u.getId(), newId)){
                System.out.println("이미 존재하는 아이디 입니다.");
                Main.mainMenu();
                return;
            }
        }

        if(Objects.equals(currentUser.getPw(), pw)) {
            // 1. 기존 ID 기준으로 정확하게 제거
            UserManager.getInstance().Users().removeIf(u -> u.getId().equals(currentUser.getId()));
            // 2. 파일 이름 및 ID 수정
            currentUser.idFileUpdate(newId);
            // 3. 다시 리스트에 추가
            UserManager.getInstance().Users().add(currentUser);
            System.out.println("아이디가 성공적으로 변경되었습니다.");
        }else{
            System.out.println("비밀번호가 맞지 않습니다.");
        }
        Main.mainMenu();
    }

    /** 비밀번호 변경 */
    public static void modifyPw(){
        Scanner in = new Scanner(System.in);
        System.out.print("기존 비밀번호를 입력하세요 : ");
        String pw = in.nextLine();

        if(Objects.equals(currentUser.getPw(), pw)){
            System.out.print("바꿀 비밀번호를 입력하세요 : ");
            String newPw = in.nextLine();
            currentUser.pwFileUpdate(newPw);
            System.out.println("비밀번호가 성공적으로 변경되었습니다.");
        }else{
            System.out.println("비밀번호가 맞지 않습니다.");
        }
        Main.mainMenu();
    }

    /** 회원 탈퇴 */
    public static void deleteId(){
        Scanner in = new Scanner(System.in);
        System.out.print("정말로 탈퇴하겠습니까? (Y | N) : ");
        char check = in.next().charAt(0);
        if(check=='Y' || check=='y'){
            UserManager.getInstance().removeUser(currentUser);
            User.login = false;
            currentUser = null;
            System.out.println("계정이 탈퇴되었습니다.");
        }else if(check=='N' || check=='n'){
            System.out.println("회원탈퇴를 취소했습니다.");
        }else{
            System.out.println("잘못된 입력입니다.");
        }
        Main.mainMenu();
    }

    /** 마이페이지 */
    public static void myPage(){
        String id = currentUser.getId();
        int totalWidth = 34;
        String idLine = "아이디 : "+id;
        int padding = (totalWidth - idLine.length())/2;

        System.out.print("""
                ***********************************
                          Console RPG Game
                              마이페이지
                """);
        System.out.println(" ".repeat(padding)+idLine);
        System.out.print("""
                ***********************************
                           1. 아이디 변경
                           2. 비밀번호 변경
                            3. 회원 탈퇴
                            4. 뒤로가기
                ***********************************
                Enter number
                """);

        Scanner in = new Scanner(System.in);
        while(true){
            int num = safeReadInt(in);
            switch(num){
                case 1  -> { System.out.println("[아이디 변경]");  modifyId(); }
                case 2  -> { System.out.println("[비밀번호 변경]"); modifyPw(); }
                case 3  -> { System.out.println("[회원탈퇴]");     deleteId(); }
                case 4  -> {
                    if(currentUser.getLogin()){
                        System.out.println("[뒤로가기]");
                        Main.mainMenu();
                    }
                }
                default -> System.out.println("1부터 4까지의 숫자만 입력해주세요!");
            }
        }
    }

    // ──────────────────────── 게임 내 재화/아이템 처리 ────────────────────────
    public void addGold(int g){ this.gold += g; }
    public void loseGold(int g){ this.gold -= g; }
    public boolean hasPurchased(int idx){ return itemPurchased[idx]; }
    public void setPurchased(int idx){ itemPurchased[idx] = true; }

    public int getAtkItem(){ return atkItem; }
    public void setAtkItem(int idx){
        atkItem = idx;
        if(myCharacter!=null) myCharacter.setAtkItemIndex(idx);
    }
    public int getDefItem(){ return defItem; }
    public void setDefItem(int idx){
        defItem = idx;
        if(myCharacter!=null) myCharacter.setDefItemIndex(idx);
    }

    public void returnUserState(){  // 죽었을 때 플레이어의 체력을 초기화하는 메소드
        myCharacter.getAtk();
        myCharacter.getDef();
        myCharacter.reHp();
    }

    // ──────────────────────── 파일 입출력 관리 ────────────────────────

    public void idFileUpdate(String newId) {  // 아이디 변경 후 파일 변경 메소드
        Path userOldPath = Paths.get("data/user_" + this.id + ".txt");
        Path userNewPath = Paths.get("data/user_" + newId + ".txt");
        Path charOldPath = Paths.get("data/character_" + this.id + ".txt");
        Path charNewPath = Paths.get("data/character_" + newId + ".txt");

        try {
            if (Files.exists(userNewPath)) {
                System.out.println("이미 존재하는 아이디입니다. 다른 아이디를 사용해주세요.");
                return;
            }

            // 파일명 먼저 변경 (유저, 캐릭터 둘 다)
            if (Files.exists(userOldPath)) {
                Files.move(userOldPath, userNewPath);
            }
            if (Files.exists(charOldPath)) {
                Files.move(charOldPath, charNewPath);
            }

            // ID 변경 및 저장
            this.id = newId;
            UserManager.saveUser(this);

        } catch (IOException e) {
            System.out.println("아이디 변경 중 오류 발생: " + e.getMessage());
        }
    }

    public void pwFileUpdate(String newPw) {  // 비밀번호 변경 후 파일 변경 메소드
        this.pw = newPw;
        UserManager.saveUser(this);
    }
}
