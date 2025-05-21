import java.util.ArrayList;

public class UserManager {
    public ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> Users() {  // 유저 정보 반환하기
        return users;
    }

    public void addUser(User user) {  // 유저 생성
        users.add(user);
    }

    public void removeUser(User user) {  // 유저 삭제
        users.remove(user);
    }

    public void showAllUsers() {
        for (User user : users) {
            user.printInfo();
        }
    }
}
