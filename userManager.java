import java.util.ArrayList;

public class userManager {
    ArrayList<User> users = new ArrayList<>();

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
