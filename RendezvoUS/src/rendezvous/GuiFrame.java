package rendezvous;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GuiFrame extends JFrame {

	private JPanel contentPane;
	private JTextArea mondayOut;
	private JTextArea tuesdayOut;
	private JTextArea wednesdayOut;
	private JTextArea thursdayOut;
	private JTextArea fridayOut;
	private JTextArea saturdayOut;
	private JTextArea sundayOut;
	private JTextField txtMonday;
	private JTextField txtTuesday;
	private JTextField txtWednesday;
	private JTextField txtThursday;
	private JTextField txtFriday;
	private JTextField txtSaturday;
	private JTextField txtSunday;
	private int selectedDay = 0;
	private int selectedHour = 0;
	WeeklySchedule schedule;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public GuiFrame(WeeklySchedule newSchedule) {
		schedule = newSchedule;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(146, 146, 146));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox daySelect = new JComboBox();
		daySelect.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6"}));
		daySelect.setFont(new Font("Arial", Font.PLAIN, 11));
		daySelect.setBounds(10, 297, 42, 22);
		contentPane.add(daySelect);
		daySelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedDay = Integer.parseInt(daySelect.getSelectedItem().toString());
			}
		
		});
		
		JComboBox hourSelect = new JComboBox();
		hourSelect.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "22", "23"}));
		hourSelect.setFont(new Font("Arial", Font.PLAIN, 11));
		hourSelect.setBounds(69, 297, 42, 22);
		contentPane.add(hourSelect);
		hourSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedHour = Integer.parseInt(hourSelect.getSelectedItem().toString());
			}
		
		});
		
		
		JButton btnNewButton = new JButton("Toggle Index");
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				schedule.setAvailability(selectedDay,selectedHour, !schedule.getAvailability(selectedDay, selectedHour));
				mondayOut.setText(schedule.getBusyHoursOfDay(0));
				tuesdayOut.setText(schedule.getBusyHoursOfDay(1));
				wednesdayOut.setText(schedule.getBusyHoursOfDay(2));
				thursdayOut.setText(schedule.getBusyHoursOfDay(3));
				fridayOut.setText(schedule.getBusyHoursOfDay(4));
				saturdayOut.setText(schedule.getBusyHoursOfDay(5));
				sundayOut.setText(schedule.getBusyHoursOfDay(6));
			}
		});
		btnNewButton.setBounds(10, 345, 101, 23);
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(8, 36, 770, 239);
		contentPane.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{110, 110, 110, 110, 110, 110, 110, 0};
		gbl_panel.rowHeights = new int[]{44, 195, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		txtMonday = new JTextField();
		txtMonday.setEditable(false);
		txtMonday.setText("  Monday");
		txtMonday.setColumns(10);
		GridBagConstraints gbc_txtMonday = new GridBagConstraints();
		gbc_txtMonday.fill = GridBagConstraints.BOTH;
		gbc_txtMonday.insets = new Insets(0, 0, 5, 5);
		gbc_txtMonday.gridx = 0;
		gbc_txtMonday.gridy = 0;
		panel.add(txtMonday, gbc_txtMonday);
		
		txtTuesday = new JTextField();
		txtTuesday.setEditable(false);
		txtTuesday.setText("  Tuesday");
		txtTuesday.setColumns(10);
		GridBagConstraints gbc_txtTuesday = new GridBagConstraints();
		gbc_txtTuesday.fill = GridBagConstraints.BOTH;
		gbc_txtTuesday.insets = new Insets(0, 0, 5, 5);
		gbc_txtTuesday.gridx = 1;
		gbc_txtTuesday.gridy = 0;
		panel.add(txtTuesday, gbc_txtTuesday);
		
		txtWednesday = new JTextField();
		txtWednesday.setEditable(false);
		txtWednesday.setText("  Wednesday");
		txtWednesday.setColumns(10);
		GridBagConstraints gbc_txtWednesday = new GridBagConstraints();
		gbc_txtWednesday.fill = GridBagConstraints.BOTH;
		gbc_txtWednesday.insets = new Insets(0, 0, 5, 5);
		gbc_txtWednesday.gridx = 2;
		gbc_txtWednesday.gridy = 0;
		panel.add(txtWednesday, gbc_txtWednesday);
		
		txtThursday = new JTextField();
		txtThursday.setEditable(false);
		txtThursday.setText("  Thursday");
		txtThursday.setColumns(10);
		GridBagConstraints gbc_txtThursday = new GridBagConstraints();
		gbc_txtThursday.fill = GridBagConstraints.BOTH;
		gbc_txtThursday.insets = new Insets(0, 0, 5, 5);
		gbc_txtThursday.gridx = 3;
		gbc_txtThursday.gridy = 0;
		panel.add(txtThursday, gbc_txtThursday);
		
		txtFriday = new JTextField();
		txtFriday.setEditable(false);
		txtFriday.setText("  Friday");
		txtFriday.setColumns(10);
		GridBagConstraints gbc_txtFriday = new GridBagConstraints();
		gbc_txtFriday.fill = GridBagConstraints.BOTH;
		gbc_txtFriday.insets = new Insets(0, 0, 5, 5);
		gbc_txtFriday.gridx = 4;
		gbc_txtFriday.gridy = 0;
		panel.add(txtFriday, gbc_txtFriday);
		
		txtSaturday = new JTextField();
		txtSaturday.setEditable(false);
		txtSaturday.setText("  Saturday");
		txtSaturday.setColumns(10);
		GridBagConstraints gbc_txtSaturday = new GridBagConstraints();
		gbc_txtSaturday.fill = GridBagConstraints.BOTH;
		gbc_txtSaturday.insets = new Insets(0, 0, 5, 5);
		gbc_txtSaturday.gridx = 5;
		gbc_txtSaturday.gridy = 0;
		panel.add(txtSaturday, gbc_txtSaturday);
		
		txtSunday = new JTextField();
		txtSunday.setEditable(false);
		txtSunday.setText(" Sunday");
		txtSunday.setColumns(10);
		GridBagConstraints gbc_txtSunday = new GridBagConstraints();
		gbc_txtSunday.fill = GridBagConstraints.BOTH;
		gbc_txtSunday.insets = new Insets(0, 0, 5, 0);
		gbc_txtSunday.gridx = 6;
		gbc_txtSunday.gridy = 0;
		panel.add(txtSunday, gbc_txtSunday);
		
		mondayOut = new JTextArea();
		mondayOut.setEditable(false);
		GridBagConstraints gbc_mondayOut = new GridBagConstraints();
		gbc_mondayOut.fill = GridBagConstraints.BOTH;
		gbc_mondayOut.insets = new Insets(0, 0, 0, 5);
		gbc_mondayOut.gridx = 0;
		gbc_mondayOut.gridy = 1;
		panel.add(mondayOut, gbc_mondayOut);
		mondayOut.setText(schedule.getBusyHoursOfDay(0));
		mondayOut.setColumns(10);
		
		tuesdayOut = new JTextArea();
		tuesdayOut.setEditable(false);
		tuesdayOut.setColumns(10);
		tuesdayOut.setText(schedule.getBusyHoursOfDay(1));
		GridBagConstraints gbc_tuesdayOut = new GridBagConstraints();
		gbc_tuesdayOut.fill = GridBagConstraints.BOTH;
		gbc_tuesdayOut.insets = new Insets(0, 0, 0, 5);
		gbc_tuesdayOut.gridx = 1;
		gbc_tuesdayOut.gridy = 1;
		panel.add(tuesdayOut, gbc_tuesdayOut);
		
		wednesdayOut = new JTextArea();
		wednesdayOut.setEditable(false);
		wednesdayOut.setColumns(10);
		wednesdayOut.setText(schedule.getBusyHoursOfDay(2));
		GridBagConstraints gbc_wednesdayOut = new GridBagConstraints();
		gbc_wednesdayOut.fill = GridBagConstraints.BOTH;
		gbc_wednesdayOut.insets = new Insets(0, 0, 0, 5);
		gbc_wednesdayOut.gridx = 2;
		gbc_wednesdayOut.gridy = 1;
		panel.add(wednesdayOut, gbc_wednesdayOut);
		
		thursdayOut = new JTextArea();
		thursdayOut.setEditable(false);
		thursdayOut.setColumns(10);
		thursdayOut.setText(schedule.getBusyHoursOfDay(3));
		GridBagConstraints gbc_thursdayOut = new GridBagConstraints();
		gbc_thursdayOut.fill = GridBagConstraints.BOTH;
		gbc_thursdayOut.insets = new Insets(0, 0, 0, 5);
		gbc_thursdayOut.gridx = 3;
		gbc_thursdayOut.gridy = 1;
		panel.add(thursdayOut, gbc_thursdayOut);
		
		fridayOut = new JTextArea();
		fridayOut.setEditable(false);
		fridayOut.setColumns(10);
		fridayOut.setText(schedule.getBusyHoursOfDay(4));
		GridBagConstraints gbc_fridayOut = new GridBagConstraints();
		gbc_fridayOut.fill = GridBagConstraints.BOTH;
		gbc_fridayOut.insets = new Insets(0, 0, 0, 5);
		gbc_fridayOut.gridx = 4;
		gbc_fridayOut.gridy = 1;
		panel.add(fridayOut, gbc_fridayOut);
		
		saturdayOut = new JTextArea();
		saturdayOut.setEditable(false);
		saturdayOut.setColumns(10);
		saturdayOut.setText(schedule.getBusyHoursOfDay(5));
		GridBagConstraints gbc_saturdayOut = new GridBagConstraints();
		gbc_saturdayOut.fill = GridBagConstraints.BOTH;
		gbc_saturdayOut.insets = new Insets(0, 0, 0, 5);
		gbc_saturdayOut.gridx = 5;
		gbc_saturdayOut.gridy = 1;
		panel.add(saturdayOut, gbc_saturdayOut);
		
		sundayOut = new JTextArea();
		sundayOut.setEditable(false);
		sundayOut.setColumns(10);
		sundayOut.setText(schedule.getBusyHoursOfDay(6));
		GridBagConstraints gbc_sundayOut = new GridBagConstraints();
		gbc_sundayOut.fill = GridBagConstraints.BOTH;
		gbc_sundayOut.gridx = 6;
		gbc_sundayOut.gridy = 1;
		panel.add(sundayOut, gbc_sundayOut);
	}
}
