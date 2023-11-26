package rendezvous;

import java.util.ArrayList;

public class GroupCalendar extends CalendarModel {
    private final UserAccount groupCreator;
    private ArrayList<UserAccount> accountsInGroup = new ArrayList<>(); // Reference to the user's accounts

    //Constructor for a single initial group member
    public GroupCalendar(UserAccount groupCreator, CalendarPanel calendarPanel) {
        super(groupCreator, calendarPanel);
        this.groupCreator = groupCreator;
        accountsInGroup.set(0, groupCreator);
    }
    //Constructor for multiple initial group members
    public GroupCalendar(UserAccount groupCreator, ArrayList<UserAccount> userAccounts, CalendarPanel calendarPanel) {
        super(groupCreator, calendarPanel);
        this.groupCreator = groupCreator;
        accountsInGroup = userAccounts;

    }

    // Provides access to the EventStorage instance
    public EventStorage getEventStorage(int account) {
        return new EventStorage(accountsInGroup.get(account).getUsername()); // Create a new EventStorage for the user
    }

    public UserAccount getGroupCreator() {
        return groupCreator;
    }
    public void addAccountToGroup(UserAccount newGroupMember) {
        accountsInGroup.add(newGroupMember);
    }
    public UserAccount getAccountInGroup(int account) {
        return accountsInGroup.get(account);
    }

}

