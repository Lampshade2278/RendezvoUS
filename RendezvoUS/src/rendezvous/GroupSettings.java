package rendezvous;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GroupSettings extends JPanel {
    private UserAccount userAccount;
    private String username = null;
    private GroupViewScreen groupCalendarPanel;
    private final JTextArea groupList;
    public GroupSettings(UserAccount userAccount, GroupViewScreen groupCalendarPanel) {
        this.userAccount = userAccount;
        this.groupCalendarPanel = groupCalendarPanel;
        groupList = new JTextArea(20, 20);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createGroupManagementSection());
    }
    //List of Group members, Group Owner, Add/remove group Member

    private JPanel createGroupManagementSection() {
        JPanel groupAccountsPanel = new JPanel();
        groupAccountsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        groupAccountsPanel.setBorder(BorderFactory.createTitledBorder("Group Members"));

        groupList.setBorder(BorderFactory.createTitledBorder("Accounts in Group: "));
        updateGroupList();

        JButton addAccountButton = new JButton("Add Account");
        addAccountButton.addActionListener(e -> {
            username = promptForUsername();
            addAccount(username);
        });

        JButton removeAccountButton = new JButton("Remove Account");
        removeAccountButton.addActionListener(e -> {
            username = promptForUsername();
            removeAccount(username);
        });


        groupAccountsPanel.add(groupList);
        groupAccountsPanel.add(addAccountButton);
        groupAccountsPanel.add(removeAccountButton);
        //groupAccountsPanel.add(sendFeedbackButton);

        return groupAccountsPanel;
    }

    private void updateGroupList() {
        groupList.setText("");
        for(int i = 0; i < groupCalendarPanel.groupCalendar.getAccountsInGroupSize(); i++){
            groupList.append(groupCalendarPanel.groupCalendar.getAccountInGroup(i).getUsername() + "\n");
        }
    }

    private String promptForUsername() {
        String newUsername = null;
        newUsername = JOptionPane.showInputDialog("Enter a Username:");
        return newUsername;
    }

    private void addAccount(String username) {
        File file = new File(username + ".dat");
        if (file.exists()) {
            groupCalendarPanel.groupCalendar.addAccountToGroup(username);
            updateGroupList();
            JOptionPane.showMessageDialog(this, "Account added to Group.");
        } else {
            JOptionPane.showMessageDialog(this, "Error: account not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void removeAccount(String groupMember) {
        boolean inGroup = false;
        for(int i = 0; i < groupCalendarPanel.groupCalendar.getAccountsInGroupSize(); i++){
            if(groupMember.equals(groupCalendarPanel.groupCalendar.getAccountInGroup(i).getUsername())){
                inGroup = true;
            }
        }
        if (groupMember.equals(userAccount.getUsername())){
            JOptionPane.showMessageDialog(this, "Cannot remove group creator from group");
        }
        else if (inGroup) {
            groupCalendarPanel.groupCalendar.removeAccountInGroup(groupMember);
            updateGroupList();
            JOptionPane.showMessageDialog(this, "Account removed from Group.");
        } else {
            JOptionPane.showMessageDialog(this, "Error: account not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}