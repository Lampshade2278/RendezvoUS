package rendezvous;

import java.util.ArrayList;

public class GroupCalendar extends CalendarModel {
    private final UserAccount groupCreator;
    private ArrayList<UserAccount> accountsInGroup = new ArrayList<>(); // Reference to the user's accounts

    //Constructor for a single initial group member
    public GroupCalendar(UserAccount groupCreator, GroupViewScreen groupCalendarPanel) {
        super(groupCreator, groupCalendarPanel);
        this.groupCreator = groupCreator;
        accountsInGroup.add(groupCreator);

    }

    // Provides access to the EventStorage instance
    public EventStorage getEventStorage(String username) {
        int account = 0;
        for (int i = 0; i < accountsInGroup.size(); i++) {
            UserAccount acc = accountsInGroup.get(i);
            if (username.equals(acc.getUsername())) {
               account = i; // Found the matching object, return its index
            }
        }
        return new EventStorage(accountsInGroup.get(account).getUsername()); // Create a new EventStorage for the user
    }

    public UserAccount getGroupCreator() {
        return groupCreator;
    }
    public void addAccountToGroup(String newGroupMember) {
        UserAccount newUserAccount = UserDataManager.loadUserAccount(newGroupMember);
        accountsInGroup.add(newUserAccount);
    }
    public void removeAccountInGroup(String groupMember) {
        int groupMemberIndex = 0;
        for(int i = 0; i < accountsInGroup.size(); i++){
            if(groupMember.equals(accountsInGroup.get(i).getUsername()))
                groupMemberIndex = i;
        }
        accountsInGroup.remove(groupMemberIndex);
    }
    public UserAccount getAccountInGroup(int account) {
        UserAccount userAccount = accountsInGroup.get(account);
        return userAccount;
    }

    public int getAccountsInGroupSize() {
        return accountsInGroup.size();
    }

}

