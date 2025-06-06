import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

public class UserManager {
    // 싱글톤 인스턴스 생성
    private static final UserManager instance = new UserManager();
    private static final String DATA_DIR = "data"; // 데이터 디렉토리 경로
    private ArrayList<User> users = new ArrayList<>(); // 사용자 목록

    // 싱글톤 접근자
    public static UserManager getInstance() {
        return instance;
    }

    // 클래스 로딩 시 디렉토리 생성
    static {
        new File(DATA_DIR).mkdirs();
    }

    // 데이터 디렉토리 경로 반환
    public static String getDataDir() {
        return DATA_DIR;
    }

    // 생성자: 기존 사용자 파일 읽어 초기 사용자 목록 구성
    private UserManager() {
        File[] userFiles = new File(DATA_DIR).listFiles((dir, name) ->
                name.startsWith("user_") && name.endsWith(".txt"));

        if (userFiles != null) {
            for (File file : userFiles) {
                String userId = file.getName()
                        .replace("user_", "")
                        .replace(".txt", "");

                if (users.stream().noneMatch(u -> u.getId().equals(userId))) {
                    users.add(new User(userId, ""));
                }
            }
        }
    }

    // 사용자 리스트 반환
    public ArrayList<User> Users() {
        return users;
    }

    // 사용자 추가 및 저장
    public void addUser(User user) {
        users.add(user);
        saveUser(user);
    }

    // 사용자 삭제
    public void removeUser(User user) {
        users.removeIf(u -> u.getId().equals(user.getId()));

        // 현재 사용자 초기화
        if (User.currentUser != null && User.currentUser.getId().equals(user.getId())) {
            User.currentUser = null;
        }

        // 사용자 파일 삭제
        Path userPath = Paths.get(DATA_DIR, "user_" + user.getId() + ".txt");
        try {
            Files.deleteIfExists(userPath);
        } catch (IOException e) {
            System.err.println("사용자 파일 삭제 실패: " + e.getMessage());
        }

        // 캐릭터 파일 삭제
        Path charFilePath = Paths.get(DATA_DIR, "character_" + user.getId() + ".txt");
        try {
            Files.deleteIfExists(charFilePath);
        } catch (IOException e) {
            System.err.println("캐릭터 파일 삭제 실패: " + e.getMessage());
        }

        // 캐릭터 디렉토리 삭제 (추후 구조 대비)
        Path charDir = Paths.get(DATA_DIR, "characters", user.getId());
        try {
            if (Files.exists(charDir)) {
                Files.walk(charDir)
                        .sorted(Comparator.reverseOrder())
                        .forEach(p -> {
                            try {
                                Files.delete(p);
                            } catch (IOException e) {
                                System.err.println("파일 삭제 실패: " + p + " / " + e.getMessage());
                            }
                        });
            }
        } catch (IOException e) {
            System.err.println("캐릭터 디렉토리 삭제 실패: " + e.getMessage());
        }
    }

    // 저장된 모든 사용자 정보 출력
    public void showAllUsers() {
        File folder = new File(DATA_DIR);
        File[] files = folder.listFiles((dir, name) -> name.startsWith("user_") && name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("저장된 유저가 없습니다.");
            return;
        }

        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String id = reader.readLine();
                String pw = reader.readLine();
                System.out.println("아이디 : " + id + " | 비번 : " + pw);
            } catch (IOException e) {
                System.out.println(file.getName() + " 읽기 실패: " + e.getMessage());
            }
        }
    }

    // 사용자 로딩 메서드
    public static User loadUser(String id, String pw) {
        String filePath = DATA_DIR + File.separator + "user_" + id + ".txt";
        File userFile = new File(filePath);
        if (!userFile.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String storedId = reader.readLine();
            String storedPw = reader.readLine();
            if (storedId == null || storedPw == null) throw new IOException("아이디 또는 비밀번호 누락");

            if (!storedId.equals(id) || !storedPw.equals(pw)) {
                return null;
            }

            User user = new User(storedId, storedPw);

            String line;

            line = reader.readLine();
            if (line == null) throw new IOException("골드 정보 누락");
            user.setGold(Integer.parseInt(line));

            MyCharacter character = loadCharacter(id);
            user.setMyCharacter(character);

            line = reader.readLine();
            if (line == null) throw new IOException("공격 아이템 인덱스 누락");
            user.setStoredAtkItem(Integer.parseInt(line));

            line = reader.readLine();
            if (line == null) throw new IOException("방어 아이템 인덱스 누락");
            user.setStoredDefItem(Integer.parseInt(line));

            line = reader.readLine();
            if (line == null) throw new IOException("아이템 구매 정보 누락");
            String[] purchasedStr = line.split(" ");
            boolean[] purchased = new boolean[purchasedStr.length];
            for (int i = 0; i < purchasedStr.length; i++) {
                purchased[i] = Boolean.parseBoolean(purchasedStr[i]);
            }
            user.setItemPurchased(purchased);

            line = reader.readLine();
            if(line == null) throw new IOException("스테이지 클리어 정보 누락");
            String[] stageStr = line.split(" ");
            boolean[] stageClear = new boolean[stageStr.length];
            for (int i = 0; i < stageStr.length; i++) {
                stageClear[i] = Boolean.parseBoolean(stageStr[i]);
            }
            user.setStageClear(stageClear);
            Stage.stageClear = user.getStageClear();

            return user;
        } catch (IOException | NumberFormatException e) {
            System.out.println("유저 불러오기 실패: " + e.getMessage());
            return null;
        }
    }

    // 사용자 정보 저장
    public static void saveUser(User user) {
        String filePath = DATA_DIR + "/user_" + user.getId() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(user.getId());
            writer.newLine();
            writer.write(user.getPw());
            writer.newLine();
            writer.write(Integer.toString(user.getGold()));
            writer.newLine();

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

            for (boolean b : user.getItemPurchased()) {
                writer.write(b + " ");
            }
            writer.newLine();

            for (boolean b : user.getStageClear()) {
                writer.write(b + " ");
            }
            writer.newLine();
        } catch (IOException e) {
            System.out.println("유저 저장 실패: " + e.getMessage());
        }
    }

    // 캐릭터 정보 저장
    public static void saveCharacter(String userId, MyCharacter c) {
        if (c == null) return;
        String filePath = DATA_DIR + "/character_" + userId + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(c.getJob());
            writer.newLine();
            writer.write(Integer.toString(c.getHp()));
            writer.newLine();
            writer.write(Integer.toString(c.getAtk() - (c.getAtkItemIndex() != -1 ? ItemManager.getItems()[c.getAtkItemIndex()].getAtk() : 0)));
            writer.newLine();
            writer.write(Integer.toString(c.getDef() - (c.getDefItemIndex() != -1 ? ItemManager.getItems()[c.getDefItemIndex()].getDef() : 0)));
            writer.newLine();
            writer.write(Integer.toString(c.getBaseMaxHp()));
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

    // 캐릭터 정보 로드
    public static MyCharacter loadCharacter(String userId) {
        String filePath = DATA_DIR + "/character_" + userId + ".txt";
        File characterFile = new File(filePath);

        if (!characterFile.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String job = reader.readLine();
            int hp = Integer.parseInt(reader.readLine());
            int atk = Integer.parseInt(reader.readLine());
            int def = Integer.parseInt(reader.readLine());
            int baseMaxHp = Integer.parseInt(reader.readLine());
            int maxHp = Integer.parseInt(reader.readLine());
            int atkItemIdx = Integer.parseInt(reader.readLine());
            int defItemIdx = Integer.parseInt(reader.readLine());

            MyCharacter c = new MyCharacter(job, hp, atk, def);
            c.setBaseMaxHp(baseMaxHp);
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
