import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import java.awt.Color;

import javax.swing.JTextPane;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.io.*;
import java.util.*;

import javax.swing.JComboBox;


public class Staff extends JFrame {
        
        private static Connection conn = null; 
        static final String userName = "jmille39";
        static final String password = "Cosc*cyxy";

        Staff derp = this;
	private JPanel contentPane;
	private JTextField nameBox;
	private JTextField dobBox;
	private JTextField ssnBox;
	private JTextField addressBox;
	private JTextField sexBox;
	private JTextField wageBox;
	private JComboBox<String> certBox1;
	private JComboBox<String> certBox2;
	private JComboBox<String> certBox3;
	private JComboBox<String> certBox4;
	private JTextField staffSearchBox;
	private JTextPane txtpnStaffSearch;
	private JTable table;
	
	private JButton NewButton;
	private JButton DeleteButton;
	private JButton EditButton;
	private JButton ApproveButton;
	private int buttonFlag=5;
	private String ssn="";
	Vector<String> comboValues = new Vector<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Staff frame = new Staff();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

        public Staff(){
            this("");
        }
        
	public Staff(String searchText) {
                paintUI();
                populateTable(searchText);		
	}
        
        private void populateTable(String searchText){
                Object[][] data = getTableData(searchText);
                table.setModel(new TableModel(data,
			new String[] {
				"Name", "Address", "SSN", "DOB", "Sex", "Wage"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(99);
		table.getColumnModel().getColumn(1).setPreferredWidth(131);
		table.getColumnModel().getColumn(2).setPreferredWidth(69);
		table.getColumnModel().getColumn(3).setPreferredWidth(72);
		table.getColumnModel().getColumn(4).setPreferredWidth(32);
		table.getColumnModel().getColumn(5).setPreferredWidth(70);
		
		table.setBorder(new LineBorder(new Color(0, 0, 0)));            
        }
        
        private void paintUI(){
		setTitle("Staff Details");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		nameBox = new JTextField();
		nameBox.setEditable(false);
		nameBox.setColumns(10);
		nameBox.setBackground(Color.WHITE);
		nameBox.setBounds(102, 212, 540, 22);
		contentPane.add(nameBox);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("Name:");
		textPane.setEditable(false);
		textPane.setBackground(SystemColor.menu);
		textPane.setBounds(46, 212, 44, 22);
		contentPane.add(textPane);
		
		JTextPane txtpnSsn = new JTextPane();
		txtpnSsn.setText("SSN:");
		txtpnSsn.setEditable(false);
		txtpnSsn.setBackground(SystemColor.menu);
		txtpnSsn.setBounds(55, 278, 35, 22);
		contentPane.add(txtpnSsn);
		
		JTextPane txtpnDob = new JTextPane();
		txtpnDob.setText("D.O.B.:");
		txtpnDob.setEditable(false);
		txtpnDob.setBackground(SystemColor.menu);
		txtpnDob.setBounds(46, 311, 44, 22);
		contentPane.add(txtpnDob);
		
		dobBox = new JTextField();
		dobBox.setEditable(false);
		dobBox.setColumns(10);
		dobBox.setBackground(Color.WHITE);
		dobBox.setBounds(102, 311, 200, 22);
		contentPane.add(dobBox);
                
		
		ssnBox = new JTextField();
		ssnBox.setEditable(false);
		ssnBox.setColumns(10);
		ssnBox.setBackground(Color.WHITE);
		ssnBox.setBounds(102, 278, 200, 22);
		contentPane.add(ssnBox);
		
		JTextPane txtpnAddress = new JTextPane();
		txtpnAddress.setText("Address:");
		txtpnAddress.setEditable(false);
		txtpnAddress.setBackground(SystemColor.menu);
		txtpnAddress.setBounds(33, 245, 57, 22);
		contentPane.add(txtpnAddress);
		
		addressBox = new JTextField();
		addressBox.setEditable(false);
		addressBox.setColumns(10);
		addressBox.setBackground(Color.WHITE);
		addressBox.setBounds(102, 245, 540, 22);
		contentPane.add(addressBox);
		
		sexBox = new JTextField();
		sexBox.setEditable(false);
		sexBox.setColumns(10);
		sexBox.setBackground(Color.WHITE);
		sexBox.setBounds(103, 344, 199, 22);
		contentPane.add(sexBox);
		
		JTextPane txtpnSex = new JTextPane();
		txtpnSex.setText("Sex:");
		txtpnSex.setEditable(false);
		txtpnSex.setBackground(SystemColor.menu);
		txtpnSex.setBounds(58, 344, 32, 22);
		contentPane.add(txtpnSex);
		
		JTextPane txtpnWage = new JTextPane();
		txtpnWage.setText("Wage:");
		txtpnWage.setEditable(false);
		txtpnWage.setBackground(SystemColor.menu);
		txtpnWage.setBounds(46, 377, 44, 22);
		contentPane.add(txtpnWage);
		
		wageBox = new JTextField();
		wageBox.setEditable(false);
		wageBox.setColumns(10);
		wageBox.setBackground(Color.WHITE);
		wageBox.setBounds(103, 377, 199, 22);
		contentPane.add(wageBox);
		
		JTextPane txtpnCertifications = new JTextPane();
		txtpnCertifications.setText("Certifications:");
		txtpnCertifications.setEditable(false);
		txtpnCertifications.setBackground(SystemColor.menu);
		txtpnCertifications.setBounds(335, 278, 85, 22);
		contentPane.add(txtpnCertifications);
		
		ApproveButton = new JButton("Approve");
		ApproveButton.setEnabled(false);
		ApproveButton.setBounds(364, 419, 110, 23);
		contentPane.add(ApproveButton);
		ApproveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int reply = JOptionPane.showConfirmDialog(ApproveButton, "Do you wish to approve these Changes?", "Confirm Database Changes", JOptionPane.YES_NO_CANCEL_OPTION);
				if (reply == JOptionPane.YES_OPTION)
			    {
					//new button
					if(buttonFlag==0){
						if(!(nameBox.getText().equals("")) && !(ssnBox.getText().equals("")) && !(dobBox.getText().equals("")) 
								&& !(addressBox.getText().equals("")) && !(sexBox.getText().equals("")) && !(wageBox.getText().equals("")) ){
							
							try{
								if(conn == null){
									conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
									        + ":5030/jmille39db",
									        userName, password);
									}
							Statement stmt = conn.createStatement();
							stmt.executeUpdate("INSERT INTO PROJ_STAFF "
									+ "(NAME, ADDRESS, SSN, DOB, SEX, WAGE) "
									+ "VALUES "
									+ "('" + nameBox.getText() + "', '" + addressBox.getText() 
									+ "', '" + ssnBox.getText() + "', '" + dobBox.getText() 
									+ "', '" + sexBox.getText() + "', '" + wageBox.getText() + "');");
							
							if(!(certBox1.getSelectedItem().equals(""))){
							stmt.executeUpdate("INSERT INTO PROJ_EMPSHAVECERTS "
									+ "(ESSN, CERT_ID) "
									+ "VALUES "
									+ "('" + ssnBox.getText() + "', (SELECT CERT_ID FROM PROJ_CERTS WHERE TITLE='" + certBox1.getSelectedItem() + "'));");
							}
							
							if(!(certBox2.getSelectedItem().equals(""))){
							stmt.executeUpdate("INSERT INTO PROJ_EMPSHAVECERTS "
									+ "(ESSN, CERT_ID) "
									+ "VALUES "
									+ "('" + ssnBox.getText() + "', (SELECT CERT_ID FROM PROJ_CERTS WHERE TITLE='" + certBox2.getSelectedItem() + "'));");
							}
							
							if(!(certBox3.getSelectedItem().equals(""))){
							stmt.executeUpdate("INSERT INTO PROJ_EMPSHAVECERTS "
									+ "(ESSN, CERT_ID) "
									+ "VALUES "
									+ "('" + ssnBox.getText() + "', (SELECT CERT_ID FROM PROJ_CERTS WHERE TITLE='" + certBox3.getSelectedItem() + "'));");
							}
							
							if(!(certBox4.getSelectedItem().equals(""))){
							stmt.executeUpdate("INSERT INTO PROJ_EMPSHAVECERTS "
									+ "(ESSN, CERT_ID) "
									+ "VALUES "
									+ "('" + ssnBox.getText() + "', (SELECT CERT_ID FROM PROJ_CERTS WHERE TITLE='" + certBox4.getSelectedItem() + "'));");
							}
							
							ssn = ssnBox.getText();							
							
							} catch(SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else
						{
							JOptionPane.showMessageDialog(derp, "You Left Something Blank");
							return;
						}
					}
					
					//edit button
					else if(buttonFlag==1){
						if(!(nameBox.getText().equals("")) && !(ssnBox.getText().equals("")) && !(dobBox.getText().equals("")) 
								&& !(addressBox.getText().equals("")) && !(sexBox.getText().equals("")) && !(wageBox.getText().equals("")) ){
							try{
								
								if(conn == null){
									conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
									        + ":5030/jmille39db",
									        userName, password);
								}
								Statement stmt = conn.createStatement();
								
								stmt.executeUpdate("DELETE FROM PROJ_EMPSHAVECERTS WHERE ESSN='" + ssn + "';");
								
								stmt.executeUpdate("UPDATE PROJ_STAFF "
									+ "SET NAME = '" + nameBox.getText() + "', "
									+ "ADDRESS = '" + addressBox.getText() + "', "
									+ "SSN = '" + ssnBox.getText() + "', "
									+ "DOB = '" + dobBox.getText() + "', "
									+ "SEX = '" + sexBox.getText() + "', "
									+ "WAGE = '" + wageBox.getText() + "' "
									+ "WHERE SSN = '" + ssn + "';");
																
								try{if(!(certBox1.getSelectedItem().equals(""))){
								stmt.executeUpdate("INSERT INTO PROJ_EMPSHAVECERTS "
										+ "(ESSN, CERT_ID) "
										+ "VALUES "
										+ "('" + ssnBox.getText() + "', (SELECT CERT_ID FROM PROJ_CERTS WHERE TITLE='" + certBox1.getSelectedItem() + "'));");
								}}catch(SQLException e1){}
								
								try{if(!(certBox2.getSelectedItem().equals(""))){
								stmt.executeUpdate("INSERT INTO PROJ_EMPSHAVECERTS "
										+ "(ESSN, CERT_ID) "
										+ "VALUES "
										+ "('" + ssnBox.getText() + "', (SELECT CERT_ID FROM PROJ_CERTS WHERE TITLE='" + certBox2.getSelectedItem() + "'));");
								}}catch(SQLException e1){}
								
								try{if(!(certBox3.getSelectedItem().equals(""))){
								stmt.executeUpdate("INSERT INTO PROJ_EMPSHAVECERTS "
										+ "(ESSN, CERT_ID) "
										+ "VALUES "
										+ "('" + ssnBox.getText() + "', (SELECT CERT_ID FROM PROJ_CERTS WHERE TITLE='" + certBox3.getSelectedItem() + "'));");
								}}catch(SQLException e1){}
								
								try{if(!(certBox4.getSelectedItem().equals(""))){
								stmt.executeUpdate("INSERT INTO PROJ_EMPSHAVECERTS "
										+ "(ESSN, CERT_ID) "
										+ "VALUES "
										+ "('" + ssnBox.getText() + "', (SELECT CERT_ID FROM PROJ_CERTS WHERE TITLE='" + certBox4.getSelectedItem() + "'));");
								}}catch(SQLException e1){}
							
								ssn=ssnBox.getText();
							} catch(SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else
						{
							JOptionPane.showMessageDialog(derp, "You Left Something Blank");
							return;
						}
					}
					
					Object[][] newdata = getTableData("");
					table.setModel(new TableModel(
						newdata,
	                    new String[] {
	                    	"Name", "Address", "SSN", "DOB", "Sex", "Wage"
	                    }
					));
					
	                table.getColumnModel().getColumn(0).setPreferredWidth(99);
	                table.getColumnModel().getColumn(1).setPreferredWidth(131);
	                table.getColumnModel().getColumn(2).setPreferredWidth(69);
	                table.getColumnModel().getColumn(3).setPreferredWidth(72);
	                table.getColumnModel().getColumn(4).setPreferredWidth(32);
	                table.getColumnModel().getColumn(5).setPreferredWidth(70);
			    
					buttonFlag = 2;
					nameBox.setEditable(false);
					addressBox.setEditable(false);
					ssnBox.setEditable(false);
					dobBox.setEditable(false);
					sexBox.setEditable(false);
					wageBox.setEditable(false);
					certBox1.setEnabled(false);
					certBox2.setEnabled(false);
					certBox3.setEnabled(false);
					certBox4.setEnabled(false);
					ApproveButton.setEnabled(false);
					NewButton.setEnabled(true);
					EditButton.setEnabled(true);
					DeleteButton.setText("Delete Entry");
					DeleteButton.setEnabled(false);
			    }
				
				else if (reply == JOptionPane.NO_OPTION)
			    {
			      
			    }
				else
				{
					
					nameBox.setEditable(false);
					addressBox.setEditable(false);
					ssnBox.setEditable(false);
					dobBox.setEditable(false);
					sexBox.setEditable(false);
					wageBox.setEditable(false);
					certBox1.setEnabled(false);
					certBox2.setEnabled(false);
					certBox3.setEnabled(false);
					certBox4.setEnabled(false);
					ApproveButton.setEnabled(false);
					NewButton.setEnabled(true);
					if(!(ssn.equals(""))){
					EditButton.setEnabled(true);
					DeleteButton.setEnabled(true);
					}
					else
						DeleteButton.setEnabled(false);
					DeleteButton.setText("Delete Entry");
					buttonFlag = 2;
				}
			}
		});
		
		EditButton = new JButton("Edit Entry");
		EditButton.setEnabled(false);
		EditButton.setBounds(206, 419, 110, 23);
		contentPane.add(EditButton);
		EditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFlag = 1;
				nameBox.setEditable(true);
				addressBox.setEditable(true);
				ssnBox.setEditable(true);
				dobBox.setEditable(true);
				sexBox.setEditable(true);
				wageBox.setEditable(true);
				certBox1.setEnabled(true);
				certBox2.setEnabled(true);
				certBox3.setEnabled(true);
				certBox4.setEnabled(true);
				ApproveButton.setEnabled(true);
				NewButton.setEnabled(false);
				EditButton.setEnabled(false);
				DeleteButton.setEnabled(true);
				DeleteButton.setText("Cancel");
			}
		});
		
		NewButton = new JButton("New Entry");
		NewButton.setBounds(48, 419, 110, 23);
		contentPane.add(NewButton);
		NewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFlag = 0;
				nameBox.setEditable(true);
				nameBox.setText("");
				addressBox.setEditable(true);
				addressBox.setText("");
				ssnBox.setEditable(true);
				ssnBox.setText("");
				dobBox.setEditable(true);
				dobBox.setText("");
				sexBox.setEditable(true);
				sexBox.setText("");
				wageBox.setEditable(true);
				wageBox.setText("");
				certBox1.setEnabled(true);
				certBox1.setSelectedItem("");
				certBox2.setEnabled(true);
				certBox2.setSelectedItem("");
				certBox3.setEnabled(true);
				certBox3.setSelectedItem("");
				certBox4.setEnabled(true);
				certBox4.setSelectedItem("");
				ApproveButton.setEnabled(true);
				NewButton.setEnabled(true);
				EditButton.setEnabled(false);
				DeleteButton.setEnabled(true);
				DeleteButton.setText("Cancel");
				ssn="";
			}
		});
		
        DeleteButton = new JButton("Delete Entry");
        DeleteButton.setEnabled(false);
        DeleteButton.setBounds(522, 419, 110, 23);
        contentPane.add(DeleteButton);
        DeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(buttonFlag==0||buttonFlag==1){
					DeleteButton.setText("Delete Entry");
					if(!(ssn.equals(""))){
						EditButton.setEnabled(true);
						DeleteButton.setEnabled(true);
					}
					else
					{
						EditButton.setEnabled(false);
						DeleteButton.setEnabled(false);
					}
				}
				else
				{
					if(!(ssn.equals(""))){
						int reply = JOptionPane.showConfirmDialog(ApproveButton, "Are You Sure You Wish To Delete This Staff?", "Confirm Delete Staff", JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION)
					    {
							try{
								
								if(conn == null){
									conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
									        + ":5030/jmille39db",
									        userName, password);
									}
							Statement stmt = conn.createStatement();
							
							stmt.executeUpdate("DELETE FROM PROJ_EMPSHAVECERTS WHERE ESSN='" + ssn + "';");
							
							stmt.executeUpdate("DELETE FROM PROJ_STAFF "
									+ "WHERE SSN = '" + ssn + "';");
															
							Object[][] newdata = getTableData("");
							table.setModel(new TableModel(
								newdata,
			                    new String[] {
			                    	"Name", "Address", "SSN", "DOB", "Sex", "Wage"
			                    }
							));
							
			                table.getColumnModel().getColumn(0).setPreferredWidth(99);
			                table.getColumnModel().getColumn(1).setPreferredWidth(131);
			                table.getColumnModel().getColumn(2).setPreferredWidth(69);
			                table.getColumnModel().getColumn(3).setPreferredWidth(72);
			                table.getColumnModel().getColumn(4).setPreferredWidth(32);
			                table.getColumnModel().getColumn(5).setPreferredWidth(70);
							} catch(SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					    }
						else
							return;
					}
					
					nameBox.setText("");
					addressBox.setText("");
					ssnBox.setText("");
					dobBox.setText("");
					sexBox.setText("");
					wageBox.setText("");
					certBox1.setSelectedItem("");
					certBox2.setSelectedItem("");
					certBox3.setSelectedItem("");
					certBox4.setSelectedItem("");
					EditButton.setEnabled(false);
					DeleteButton.setEnabled(false);
				}
				
				buttonFlag = 3;
				nameBox.setEditable(false);
				addressBox.setEditable(false);
				ssnBox.setEditable(false);				
				dobBox.setEditable(false);				
				sexBox.setEditable(false);				
				wageBox.setEditable(false);				
				certBox1.setEnabled(false);				
				certBox2.setEnabled(false);				
				certBox3.setEnabled(false);				
				certBox4.setEnabled(false);				
				ApproveButton.setEnabled(false);
				NewButton.setEnabled(true);
				ssn="";
			}
		});
		
		/////////////////////////////////////////
        // C-C-C-COMBO BOXES
        /////////////////////////////////////////
        this.setCombo();
        
        certBox1 = new JComboBox<String>(comboValues);
		certBox1.setEnabled(false);
		certBox1.setEditable(false);
		certBox1.setBackground(Color.WHITE);
		certBox1.setBounds(432, 278, 210, 22);
		contentPane.add(certBox1);
		certBox1.setRenderer(new DefaultListCellRenderer() {
	        @Override
	        public void paint(Graphics g) {
	            setForeground(Color.BLACK);
	            super.paint(g);
	        }
	    });
		
        certBox2 = new JComboBox<String>(comboValues);
		certBox2.setEnabled(false);
		certBox2.setEditable(false);
		certBox2.setBackground(Color.WHITE);
		certBox2.setBounds(432, 311, 210, 22);
		contentPane.add(certBox2);
		certBox2.setRenderer(new DefaultListCellRenderer() {
	        @Override
	        public void paint(Graphics g) {
	            setForeground(Color.BLACK);
	            super.paint(g);
	        }
	    });
				
		certBox3 = new JComboBox<String>(comboValues);
		certBox3.setEnabled(false);
		certBox3.setEditable(false);
		certBox3.setBackground(Color.WHITE);
		certBox3.setBounds(432, 344, 210, 22);
		contentPane.add(certBox3);
		certBox3.setRenderer(new DefaultListCellRenderer() {
	        @Override
	        public void paint(Graphics g) {
	            setForeground(Color.BLACK);
	            super.paint(g);
	        }
	    });
		
		certBox4 = new JComboBox<String>(comboValues);
		certBox4.setEnabled(false);
		certBox4.setEditable(false);
		certBox4.setBackground(Color.WHITE);
		certBox4.setBounds(432, 377, 210, 22);
		contentPane.add(certBox4);
		certBox4.setRenderer(new DefaultListCellRenderer() {
	        @Override
	        public void paint(Graphics g) {
	            setForeground(Color.BLACK);
	            super.paint(g);
	        }
	    });
		
		
		
		
		
		
		staffSearchBox = new JTextField();
		staffSearchBox.setColumns(10);
		staffSearchBox.setBackground(Color.WHITE);
		staffSearchBox.setBounds(449, 11, 225, 22);
		contentPane.add(staffSearchBox);
		
		txtpnStaffSearch = new JTextPane();
		txtpnStaffSearch.setText("Staff Search:");
		txtpnStaffSearch.setEditable(false);
		txtpnStaffSearch.setBackground(SystemColor.menu);
		txtpnStaffSearch.setBounds(365, 11, 82, 22);
		contentPane.add(txtpnStaffSearch);
		
        table = new JTable(100,6);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 44, 662, 155);
		contentPane.add(scrollPane);    
        scrollPane.setViewportView(table);                
        addEventHandlers();
        }
        
        private void addEventHandlers(){
            
		staffSearchBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[][] newdata = getTableData(staffSearchBox.getText());
				table.setModel(new TableModel(
					newdata,
                                        new String[] {
                                                "Name", "Address", "SSN", "DOB", "Sex", "Wage"
                                        }
				));
                                table.getColumnModel().getColumn(0).setPreferredWidth(99);
                                table.getColumnModel().getColumn(1).setPreferredWidth(131);
                                table.getColumnModel().getColumn(2).setPreferredWidth(69);
                                table.getColumnModel().getColumn(3).setPreferredWidth(72);
                                table.getColumnModel().getColumn(4).setPreferredWidth(32);
                                table.getColumnModel().getColumn(5).setPreferredWidth(70);
			}
		});
                
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable table = (JTable)e.getSource();
					int row = table.getSelectedRow();
					ssn = table.getValueAt(row, 2).toString();
					getDetail(ssn);
					if(!(ssn.equals(""))){
						DeleteButton.setEnabled(true);
						EditButton.setEnabled(true);
					}
				}
			}
		});            
        }
        private Object[][] getTableData(String searchText){
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		
		try {
			if(conn == null){
			conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
			        + ":5030/jmille39db",
			        userName, password);
			}
			Statement stmt = conn.createStatement();
			ResultSet rs;
			
			rs = stmt.executeQuery("SELECT "
					+ "NAME, ADDRESS, SSN, DOB, SEX, WAGE "
					+ "FROM PROJ_STAFF WHERE Name like '%" + searchText + "%'"
							+ " OR SSN like '%" + searchText + "%';");
			
            Vector<String> row;
            while (rs.next()) {

                row = new Vector<String>();
                for (int i = 1; i <= 6; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
                //Debugging                
            }
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Object[][] newdata = Utility.to2DimArray(data);
		return newdata;            
        }
        
        private void getDetail(String ssn){	
                staffSearchBox.setText(ssn);
		try {
			if(conn == null){
			conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
			        + ":5030/jmille39db",
			        userName, password);
			}
			Statement stmt = conn.createStatement();
			ResultSet rs;
			
			rs = stmt.executeQuery("SELECT "
					+ "NAME, ADDRESS, SSN, DOB, SEX, WAGE "
					+ "FROM PROJ_STAFF WHERE SSN = '" + ssn + "';");
			

                    while (rs.next()) {
                        sexBox.setText(rs.getString(5));
                        nameBox.setText(rs.getString(1));
                        ssnBox.setText(rs.getString(3));
                        addressBox.setText(rs.getString(2));
                        dobBox.setText(rs.getString(4));
                        wageBox.setText(rs.getString(6));
                        //Debugging                
                    }
                    
			rs = stmt.executeQuery("SELECT TITLE FROM PROJ_CERTS WHERE CERT_ID IN("
					+ "SELECT CERT_ID FROM PROJ_EMPSHAVECERTS WHERE ESSN = '" + ssn + "');");

                        while (rs.next()) {
                        	if(!(rs.getString(1).equals(null)))
                        		certBox1.setSelectedItem(rs.getString(1));
                        	else
                        		certBox1.setSelectedItem("");
                        	
                        	
                        	if(rs.next())
                        		certBox2.setSelectedItem(rs.getString(1));
                        	else
                        		certBox2.setSelectedItem("");
                        	
                        	
                        	if(rs.next())
                        		certBox3.setSelectedItem(rs.getString(1));
                        	else
                        		certBox3.setSelectedItem("");
                        	
                        	
                        	if(rs.next())
                        		certBox4.setSelectedItem(rs.getString(1));
                        	else
                        		certBox4.setSelectedItem("");  
                        }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}             
        }
        
        private void setCombo(){
    		comboValues.clear();
    		comboValues.add("");
    		try {
    			if(conn == null){
    			conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
    			        + ":5030/jmille39db",
    			        userName, password);
    			}
    			Statement stmt = conn.createStatement();
    			ResultSet rs;
    			
    			rs = stmt.executeQuery("SELECT TITLE "
    					+ "FROM PROJ_CERTS ");	
    			while(rs.next()){
    				comboValues.add(rs.getString(1));
    			}
    		} catch (SQLException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    	}
}
