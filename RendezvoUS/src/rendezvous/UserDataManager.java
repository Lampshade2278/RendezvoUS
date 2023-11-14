package rendezvous;

import java.io.*;

public class UserDataManager {

    public static void saveUserAccount(UserAccount userAccount) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(userAccount.getUsername() + ".dat"))) {
            oos.writeObject(userAccount);
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    public static UserAccount loadUserAccount(String username) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(username + ".dat"))) {
            return (UserAccount) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle exceptions appropriately
            return null;
        }
    }
}
