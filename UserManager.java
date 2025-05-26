import java.io.*;
import java.util.ArrayList;

public class UserManager {  // 파일 입출력 구현 : perplexity 이용
    private static final String DATA_DIR = "data";
    private ArrayList<User> users = new ArrayList<>();

    // 데이터 디렉토리 생성 로직
    static {
        new File(DATA_DIR).mkdirs();
    }

    public UserManager() {
        // 디렉토리 생성
        new File(DATA_DIR).mkdirs();
    }

    public ArrayList<User> Users() {
        return users;
    }

    // 사용자 목록에 추가 후 저장
    public void addUser(User user) {
        users.add(user);
        saveUser(user);
        // 신규 사용자는 캐릭터가 없으므로 저장하지 않음
    }

    public void removeUser(User user) {
        users.remove(user);
        File userFile = new File(DATA_DIR + "/user_" + user.getId() + ".txt");
        File charFile = new File(DATA_DIR + "/character_" + user.getId() + ".txt");
        userFile.delete();
        charFile.delete();
    }

    public void showAllUsers() {
        for (User user : users) {
            user.printInfo();
        }
    }

    public static User loadUser(String id, String pw) {
        String filePath = DATA_DIR + File.separator + "user_" + id + ".txt";
        File userFile = new File(filePath);
        if (!userFile.exists()) {
            System.out.println("사용자 파일 없음: " + filePath);
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String storedId = reader.readLine();
            String storedPw = reader.readLine();
            // 아이디와 비밀번호 검증
            if (!storedId.equals(id) || !storedPw.equals(pw)) {
                System.out.println("아이디 또는 비밀번호 불일치");
                return null;
            }

            // 사용자 객체 생성
            User user = new User(storedId, storedPw);
            // 기타 필드 로드
            user.setGold(Integer.parseInt(reader.readLine()));

            // 캐릭터 로드 (한 번만)
            MyCharacter character = loadCharacter(id);
            user.setMyCharacter(character);

            // 아이템 인덱스와 구매 여부 로드
            user.setStoredAtkItem(Integer.parseInt(reader.readLine()));
            user.setStoredDefItem(Integer.parseInt(reader.readLine()));
            String[] bools = reader.readLine().split(" ");
            boolean[] purchased = new boolean[bools.length];
            for (int i = 0; i < bools.length; i++) {
                purchased[i] = Boolean.parseBoolean(bools[i]);
            }
            user.setItemPurchased(purchased);
            return user;
        } catch (IOException | NumberFormatException e) {
            System.out.println("사용자 로드 실패: " + e.getMessage());
            return null;
        }
    }

    // 사용자 저장
    public static void saveUser(User user) {
        String filePath = DATA_DIR + "/user_" + user.getId() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(user.getId());
            writer.newLine();
            writer.write(user.getPw());
            writer.newLine();
            writer.write(Integer.toString(user.getGold()));
            writer.newLine();
            // 캐릭터가 있을 경우, 실제 캐릭터의 장착 인덱스를 저장
            if (user.getMyCharacter() != null) {
                writer.write(Integer.toString(user.getMyCharacter().getAtkItemIndex()));
                writer.newLine();
                writer.write(Integer.toString(user.getMyCharacter().getDefItemIndex()));
                writer.newLine();
            } else {
                writer.write("-1");
                writer.newLine();
                writer.write("-1");
                writer.newLine();
            }
            boolean[] purchased = user.getItemPurchased();
            for (boolean b : purchased) {
                writer.write(b + " ");
            }
            writer.newLine();
        } catch (IOException e) {
            System.out.println("유저 저장 실패: " + e.getMessage());
        }
    }


    // 캐릭터 저장
    public static void saveCharacter(String userId, MyCharacter c) {
        if (c == null) return;
        String filePath = DATA_DIR + "/character_" + userId + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(c.getJob());
            writer.newLine();
            writer.write(Integer.toString(c.getHp()));
            writer.newLine();
            writer.write(Integer.toString(c.getAtk() - (c.getAtkItemIndex() != -1 ? Item.getItems()[c.getAtkItemIndex()].getAtk() : 0)));
            writer.newLine();
            writer.write(Integer.toString(c.getDef() - (c.getDefItemIndex() != -1 ? Item.getItems()[c.getDefItemIndex()].getDef() : 0)));
            writer.newLine();
            writer.write(Integer.toString(c.getMaxHp()));
            writer.newLine();
            writer.write(Integer.toString(c.getAtkItemIndex()));
            writer.newLine();
            writer.write(Integer.toString(c.getDefItemIndex()));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("캐릭터 저장 실패: " + e.getMessage());
        }
    }

    // 캐릭터 로드 - 오류 메시지 개선
    public static MyCharacter loadCharacter(String userId) {
        String filePath = DATA_DIR + "/character_" + userId + ".txt";
        File characterFile = new File(filePath);

        // 파일이 존재하지 않으면 조용히 null 반환 (신규 사용자의 정상적인 상황)
        if (!characterFile.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String job = reader.readLine();
            int hp = Integer.parseInt(reader.readLine());
            int atk = Integer.parseInt(reader.readLine());
            int def = Integer.parseInt(reader.readLine());
            int maxHp = Integer.parseInt(reader.readLine());
            int atkItemIdx = Integer.parseInt(reader.readLine());
            int defItemIdx = Integer.parseInt(reader.readLine());

            MyCharacter c = new MyCharacter(job, hp, atk, def);
            c.setMaxHp(maxHp);
            c.setAtkItemIndex(atkItemIdx);
            c.setDefItemIndex(defItemIdx);
            return c;
        } catch (IOException e) {
            System.out.println("캐릭터 파일 읽기 중 오류 발생: " + e.getMessage());
            return null;
        }
    }
}
