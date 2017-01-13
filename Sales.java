/*Notes:
 * CalcSalePrice Function
 */

import java.awt.EventQueue;
import java.awt.Graphics;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class Sales extends JFrame implements ItemListener{
	
	static final String userName = "jmille39";
    static final String password = "Cosc*cyxy";
    private static Connection conn = null;

	private JPanel contentPane;
	private JTextField SearchBox;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField UPCbox;
	private JTextField NameBox;
	private JComboBox<String> TypeBox;
	private JTextField SaleBox;
	private JTextField PercentBox;
	private JTextField PriceBox;
	private JTextField EndBox;
	private JTextField StartBox;
	private JButton ApproveButton;
	private JButton EditButton;
	private JButton NewButton;
	private JButton ApplyButton;
	private String upc = "";
	private String type;
	private int SaleNum;
	private int buttonFlag = 5;
	private Object[][] newdata = sqlStart();
	Vector<String> comboValues = new Vector<String>();
	private JButton DeleteButton;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sales frame = new Sales();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Sales() {
		setTitle("Sale Details");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("Item Search:");
		textPane.setEditable(false);
		textPane.setBackground(SystemColor.menu);
		textPane.setBounds(351, 11, 86, 22);
		contentPane.add(textPane);
		
		SearchBox = new JTextField();
		SearchBox.setDisabledTextColor(Color.BLACK);
		SearchBox.setColumns(10);
		SearchBox.setBackground(Color.WHITE);
		SearchBox.setBounds(449, 11, 225, 22);
		contentPane.add(SearchBox);
		SearchBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final Search c = new Search(SearchBox.getText(), 1);
				c.setVisible(true);
				c.addWindowListener(new WindowAdapter(){
					public void windowClosing(WindowEvent e){
						upc = c.UPC;
						SearchBox.setText(upc);
						sqlSearch(upc);
						if(buttonFlag==3 && c.UPC != null){
							TypeBox.setEnabled(true);
						}
					}
				});
			}
		});
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 46, 662, 155);
		contentPane.add(scrollPane);
		table = new JTable(100, 8);		
		table.setModel(new CustomTableModel(
			newdata,
			new String[] {
				"UPC", "Name", "Sale Type", "Start", "End", "Price", "% Off", "Sale $"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(45);
		table.getColumnModel().getColumn(1).setPreferredWidth(260);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setPreferredWidth(40);
		table.getColumnModel().getColumn(4).setPreferredWidth(40);
		table.getColumnModel().getColumn(5).setPreferredWidth(25);
		table.getColumnModel().getColumn(6).setPreferredWidth(25);
		table.getColumnModel().getColumn(7).setPreferredWidth(25);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		table.getColumnModel().getColumn(5).setCellRenderer( rightRenderer );
		table.getColumnModel().getColumn(6).setCellRenderer( rightRenderer );
		table.getColumnModel().getColumn(7).setCellRenderer( rightRenderer );
		scrollPane.setViewportView(table);
		
		UPCbox = new JTextField();
		UPCbox.setEditable(false);
		UPCbox.setDisabledTextColor(Color.BLACK);
		UPCbox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		UPCbox.setColumns(10);
		UPCbox.setBackground(Color.WHITE);
		UPCbox.setBounds(113, 222, 150, 22);
		contentPane.add(UPCbox);
		
		NameBox = new JTextField();
		NameBox.setEditable(false);
		NameBox.setDisabledTextColor(Color.BLACK);
		NameBox.setColumns(10);
		NameBox.setBackground(Color.WHITE);
		NameBox.setBounds(113, 255, 273, 22);
		contentPane.add(NameBox);
		
		//////////////////////////////////////////////
		/// C-C-C-COMBO BOX!!!!!!!
		//////////////////////////////////////////////
		
		this.setCombo();
	
		TypeBox = new JComboBox<String>(comboValues);
		TypeBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		TypeBox.setEnabled(false);
		TypeBox.setBackground(Color.WHITE);
		TypeBox.setBounds(383, 222, 245, 22);
		contentPane.add(TypeBox);
		TypeBox.setRenderer(new DefaultListCellRenderer() {
	        @Override
	        public void paint(Graphics g) {
	            setForeground(Color.BLACK);
	            super.paint(g);
	        }
	    });
		TypeBox.addItemListener(this); 
		
		SaleBox = new JTextField();
		SaleBox.setEditable(false);
		SaleBox.setDisabledTextColor(Color.BLACK);
		SaleBox.setColumns(10);
		SaleBox.setBackground(Color.WHITE);
		SaleBox.setBounds(478, 319, 150, 22);
		contentPane.add(SaleBox);
		
		PercentBox = new JTextField();
		PercentBox.setEditable(false);
		PercentBox.setDisabledTextColor(Color.BLACK);
		PercentBox.setColumns(10);
		PercentBox.setBackground(Color.WHITE);
		PercentBox.setBounds(478, 288, 150, 22);
		contentPane.add(PercentBox);
		
		PriceBox = new JTextField();
		PriceBox.setEditable(false);
		PriceBox.setDisabledTextColor(Color.BLACK);
		PriceBox.setColumns(10);
		PriceBox.setBackground(Color.WHITE);
		PriceBox.setBounds(478, 255, 150, 22);
		contentPane.add(PriceBox);
		
		JTextPane txtpnPrice = new JTextPane();
		txtpnPrice.setText("Price:");
		txtpnPrice.setEditable(false);
		txtpnPrice.setBackground(SystemColor.menu);
		txtpnPrice.setBounds(427, 253, 39, 22);
		contentPane.add(txtpnPrice);
		
		JTextPane txtpnOff = new JTextPane();
		txtpnOff.setText("% Off:");
		txtpnOff.setEditable(false);
		txtpnOff.setBackground(SystemColor.menu);
		txtpnOff.setBounds(422, 286, 44, 22);
		contentPane.add(txtpnOff);
		
		JTextPane txtpnSalePrice = new JTextPane();
		txtpnSalePrice.setText("Sale Price:");
		txtpnSalePrice.setEditable(false);
		txtpnSalePrice.setBackground(SystemColor.menu);
		txtpnSalePrice.setBounds(398, 319, 68, 22);
		contentPane.add(txtpnSalePrice);
		
		JTextPane txtpnSaleType = new JTextPane();
		txtpnSaleType.setText("Sale Type:");
		txtpnSaleType.setEditable(false);
		txtpnSaleType.setBackground(SystemColor.menu);
		txtpnSaleType.setBounds(303, 222, 68, 22);
		contentPane.add(txtpnSaleType);
		
		JTextPane txtpnName = new JTextPane();
		txtpnName.setText("Name:");
		txtpnName.setEditable(false);
		txtpnName.setBackground(SystemColor.menu);
		txtpnName.setBounds(57, 255, 44, 22);
		contentPane.add(txtpnName);
		
		JTextPane textPane_6 = new JTextPane();
		textPane_6.setText("UPC:");
		textPane_6.setEditable(false);
		textPane_6.setBackground(SystemColor.menu);
		textPane_6.setBounds(67, 222, 34, 22);
		contentPane.add(textPane_6);
		
		EndBox = new JTextField();
		EndBox.setEditable(false);
		EndBox.setDisabledTextColor(Color.BLACK);
		EndBox.setColumns(10);
		EndBox.setBackground(Color.WHITE);
		EndBox.setBounds(113, 319, 273, 22);
		contentPane.add(EndBox);
		
		JTextPane txtpnEndDate = new JTextPane();
		txtpnEndDate.setText("End Date:");
		txtpnEndDate.setEditable(false);
		txtpnEndDate.setBackground(SystemColor.menu);
		txtpnEndDate.setBounds(39, 319, 62, 22);
		contentPane.add(txtpnEndDate);
		
		JTextPane txtpnStartDate = new JTextPane();
		txtpnStartDate.setText("Start Date:");
		txtpnStartDate.setEditable(false);
		txtpnStartDate.setBackground(SystemColor.menu);
		txtpnStartDate.setBounds(32, 286, 69, 22);
		contentPane.add(txtpnStartDate);
		
		StartBox = new JTextField();
		StartBox.setEditable(false);
		StartBox.setDisabledTextColor(Color.BLACK);
		StartBox.setColumns(10);
		StartBox.setBackground(Color.WHITE);
		StartBox.setBounds(113, 286, 273, 22);
		contentPane.add(StartBox);
		
		////////////////////////////////////////////////////
		/// Approve Button
		////////////////////////////////////////////////////
		ApproveButton = new JButton("Approve");
		ApproveButton.setEnabled(false);
		ApproveButton.setBounds(418, 369, 110, 23);
		contentPane.add(ApproveButton);
		ApproveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int reply = JOptionPane.showConfirmDialog(ApproveButton, "Do you wish to approve these Changes?", "Confirm Database Changes", JOptionPane.YES_NO_CANCEL_OPTION);
				if (reply == JOptionPane.YES_OPTION)
			    {
					// New Button
					if(buttonFlag==0){
						try{
							
							if(conn == null){
								conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
								        + ":5030/jmille39db",
								        userName, password);
								}
						Statement stmt = conn.createStatement();
						stmt.executeUpdate("INSERT INTO PROJ_SALE "
								+ "(TYPE, START_DATE, END_DATE, PERCENT_OFF) "
								+ "VALUES "
								+ "('" + TypeBox.getSelectedItem() + "', '" + StartBox.getText() 
								+ "', '" + EndBox.getText() + "', '" + PercentBox.getText() + "');");
														
						TypeBox.addItem(TypeBox.getSelectedItem().toString());
						} catch(SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					// Edit Button
					else if(buttonFlag==1){
						try{
							
							if(conn == null){
								conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
								        + ":5030/jmille39db",
								        userName, password);
								}
						Statement stmt = conn.createStatement();
						stmt.executeUpdate("UPDATE PROJ_SALE "
								+ "SET TYPE = '" + TypeBox.getSelectedItem() + "', "
								+ "START_DATE = '" + StartBox.getText() + "', "
								+ "END_DATE = '" + EndBox.getText() + "', "
								+ "PERCENT_OFF = '" + PercentBox.getText() + "' "
								+ "WHERE SALE_NUM = '" + SaleNum + "';");
						
						setCombo();
						} catch(SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					// Apply Button
					else if(buttonFlag==3){
						try{
							
							if(conn == null){
								conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
								        + ":5030/jmille39db",
								        userName, password);
							}
							Statement stmt = conn.createStatement();
							String query="SELECT * FROM PROJ_PRODSONSALE WHERE UPC = '" + upc +"';"; 
							ResultSet rs=stmt.executeQuery(query);
							if(!rs.next()){
								stmt.executeUpdate("INSERT INTO PROJ_PRODSONSALE "
										+ "(UPC, SALE_NUM) "
										+ "VALUES "
										+ "('" + upc + "', '" + SaleNum + "');");
							}
							else{
								stmt.executeUpdate("UPDATE PROJ_PRODSONSALE "
										+ "SET SALE_NUM = '" + SaleNum + "'"
										+ "WHERE UPC = '" + upc + "';");
							}
						} catch(SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						newdata = sqlStart();
						table.setModel(new CustomTableModel(
								newdata,
								new String[] {
									"UPC", "Name", "Sale Type", "Start", "End", "Price", "% Off", "Sale $"
								}
							));
							table.getColumnModel().getColumn(0).setPreferredWidth(45);
							table.getColumnModel().getColumn(1).setPreferredWidth(260);
							table.getColumnModel().getColumn(2).setPreferredWidth(70);
							table.getColumnModel().getColumn(3).setPreferredWidth(40);
							table.getColumnModel().getColumn(4).setPreferredWidth(40);
							table.getColumnModel().getColumn(5).setPreferredWidth(25);
							table.getColumnModel().getColumn(6).setPreferredWidth(25);
							table.getColumnModel().getColumn(7).setPreferredWidth(25);
							table.setBorder(new LineBorder(new Color(0, 0, 0)));
							DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
							rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
							table.getColumnModel().getColumn(5).setCellRenderer( rightRenderer );
							table.getColumnModel().getColumn(6).setCellRenderer( rightRenderer );
							table.getColumnModel().getColumn(7).setCellRenderer( rightRenderer );
					}
									
					NameBox.setEditable(false);
					UPCbox.setEditable(false);
					StartBox.setEditable(false);
					EndBox.setEditable(false);
					PriceBox.setEditable(false);
					TypeBox.setEnabled(false);
					TypeBox.setEditable(false);
					PercentBox.setEditable(false);
					SaleBox.setEditable(false);
					SearchBox.setEditable(false);
					ApproveButton.setEnabled(false);
					NewButton.setEnabled(true);
					EditButton.setEnabled(true);
					ApplyButton.setEnabled(true);
					DeleteButton.setEnabled(false);
					DeleteButton.setText("Delete Entry");
					buttonFlag = 2;
			    }
				else if (reply == JOptionPane.NO_OPTION)
			    {
			      
			    }
				else
				{
					NameBox.setEditable(false);
					UPCbox.setEditable(false);
					StartBox.setEditable(false);
					EndBox.setEditable(false);
					PriceBox.setEditable(false);
					TypeBox.setEnabled(false);
					TypeBox.setEditable(false);
					PercentBox.setEditable(false);
					SaleBox.setEditable(false);
					SearchBox.setEditable(false);
					ApproveButton.setEnabled(false);
					NewButton.setEnabled(true);
					EditButton.setEnabled(true);
					ApplyButton.setEnabled(true);
					DeleteButton.setEnabled(false);
					DeleteButton.setText("Delete Entry");
					buttonFlag = 2;
				}
			}
		});
		
		////////////////////////////////////////////////////
		/// Edit Button
		////////////////////////////////////////////////////
		EditButton = new JButton("Edit Sale");
		EditButton.setBounds(154, 369, 110, 23);
		contentPane.add(EditButton);
		EditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFlag = 1;
				NameBox.setEditable(false);
				NameBox.setText("");
				UPCbox.setEditable(false);
				UPCbox.setText("");
				StartBox.setEditable(true);
				StartBox.setText("");
				EndBox.setEditable(true);
				EndBox.setText("");
				PriceBox.setEditable(false);
				PriceBox.setText("");
				TypeBox.setEnabled(true);
				TypeBox.setEditable(false);
				TypeBox.setSelectedItem("");
				PercentBox.setEditable(true);
				PercentBox.setText("");
				SaleBox.setEditable(false);
				SaleBox.setText("");
				SearchBox.setEditable(false);
				SearchBox.setText("");
				ApproveButton.setEnabled(true);
				NewButton.setEnabled(false);
				EditButton.setEnabled(false);
				ApplyButton.setEnabled(false);
				DeleteButton.setEnabled(true);
				DeleteButton.setText("Cancel");
			}
		});
		
		////////////////////////////////////////////////////
		/// New Button
		////////////////////////////////////////////////////
		NewButton = new JButton("New Sale");
		NewButton.setBounds(22, 369, 110, 23);
		contentPane.add(NewButton);
		NewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFlag = 0;
				NameBox.setEditable(false);
				NameBox.setText("");
				UPCbox.setEditable(false);
				UPCbox.setText("");
				StartBox.setEditable(true);
				StartBox.setText("");
				EndBox.setEditable(true);
				EndBox.setText("");
				PriceBox.setEditable(false);
				PriceBox.setText("");
				TypeBox.setEnabled(true);
				TypeBox.setEditable(true);
				TypeBox.setSelectedItem("");
				PercentBox.setEditable(true);
				PercentBox.setText("");
				SaleBox.setEditable(false);
				SaleBox.setText("");
				SearchBox.setEditable(false);
				SearchBox.setText("");
				ApproveButton.setEnabled(true);
				NewButton.setEnabled(true);
				EditButton.setEnabled(false);
				ApplyButton.setEnabled(false);
				DeleteButton.setEnabled(true);
				DeleteButton.setText("Cancel");
				upc="";
			}
		});
		
		////////////////////////////////////////////////////
		/// Apply Button
		////////////////////////////////////////////////////
		ApplyButton = new JButton("Apply Sale");
		ApplyButton.setBounds(286, 369, 110, 23);
		contentPane.add(ApplyButton);
		ApplyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonFlag = 3;
				NameBox.setEditable(false);
				UPCbox.setEditable(true);
				StartBox.setEditable(false);
				EndBox.setEditable(false);
				PriceBox.setEditable(false);
				TypeBox.setEditable(false);
				PercentBox.setEditable(false);
				SaleBox.setEditable(false);
				SearchBox.setEditable(true);
				ApproveButton.setEnabled(false);
				NewButton.setEnabled(false);
				EditButton.setEnabled(false);
				ApplyButton.setEnabled(false);
				DeleteButton.setEnabled(true);
				DeleteButton.setText("Cancel");
				
				if(upc.equals(""))
					TypeBox.setEnabled(false);
				else
					TypeBox.setEnabled(true);
			}
		});
		
		////////////////////////////////////////////////////
		/// Delete Button
		////////////////////////////////////////////////////
		DeleteButton = new JButton("Delete Entry");
		DeleteButton.setEnabled(false);
		DeleteButton.setBounds(550, 368, 110, 23);
		contentPane.add(DeleteButton);
		DeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(buttonFlag==0||buttonFlag==1||buttonFlag==3){
					DeleteButton.setText("Delete Entry");
				}
				else
				{
					if(!(upc.equals(""))){
						int reply = JOptionPane.showConfirmDialog(ApproveButton, "Are You Sure You Wish To Delete This Sale?", "Confirm Delete Sale", JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION)
					    {
							try{
								
								if(conn == null){
									conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
									        + ":5030/jmille39db",
									        userName, password);
									}
							Statement stmt = conn.createStatement();
							stmt.executeUpdate("DELETE FROM PROJ_PRODSONSALE "
									+ "WHERE UPC = '" + upc + "';");
								
							newdata = sqlStart();
							table.setModel(new CustomTableModel(
									newdata,
									new String[] {
										"UPC", "Name", "Sale Type", "Start", "End", "Price", "% Off", "Sale $"
									}
								));
								table.getColumnModel().getColumn(0).setPreferredWidth(45);
								table.getColumnModel().getColumn(1).setPreferredWidth(260);
								table.getColumnModel().getColumn(2).setPreferredWidth(70);
								table.getColumnModel().getColumn(3).setPreferredWidth(40);
								table.getColumnModel().getColumn(4).setPreferredWidth(40);
								table.getColumnModel().getColumn(5).setPreferredWidth(25);
								table.getColumnModel().getColumn(6).setPreferredWidth(25);
								table.getColumnModel().getColumn(7).setPreferredWidth(25);
								table.setBorder(new LineBorder(new Color(0, 0, 0)));
								DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
								rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
								table.getColumnModel().getColumn(5).setCellRenderer( rightRenderer );
								table.getColumnModel().getColumn(6).setCellRenderer( rightRenderer );
								table.getColumnModel().getColumn(7).setCellRenderer( rightRenderer );
							} catch(SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					    }
						else
							return;
					}
				}
				
				buttonFlag = 4;
				NameBox.setEditable(false);
				UPCbox.setEditable(false);
				StartBox.setEditable(false);
				EndBox.setEditable(false);
				PriceBox.setEditable(false);
				TypeBox.setEnabled(false);
				TypeBox.setEditable(false);
				PercentBox.setEditable(false);
				SaleBox.setEditable(false);
				SearchBox.setEditable(true);
				ApproveButton.setEnabled(false);
				NewButton.setEnabled(true);
				EditButton.setEnabled(true);
				ApplyButton.setEnabled(true);
				DeleteButton.setEnabled(false);
			}
		});
		
		
		////////////////////////////////////////////////////
		/// Update Boxes on Table Double Click
		////////////////////////////////////////////////////
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable table = (JTable)e.getSource();
					int row = table.getSelectedRow();
					upc = table.getValueAt(row, 0).toString();
					
					UPCbox.setText(table.getValueAt(row, 0).toString());
					NameBox.setText(table.getValueAt(row, 1).toString());
					TypeBox.setSelectedItem(table.getValueAt(row, 2).toString());
					StartBox.setText(table.getValueAt(row, 3).toString());
					EndBox.setText(table.getValueAt(row, 4).toString());
					PriceBox.setText(table.getValueAt(row, 5).toString());
					PercentBox.setText(table.getValueAt(row, 6).toString());
					SaleBox.setText(table.getValueAt(row, 7).toString());
					DeleteButton.setEnabled(true);
					
					if(buttonFlag==3){
						TypeBox.setEnabled(true);
					}
				}
			}
		});
	}
	
	
	///////////////////////////////
	// Set Up The Combo Box Values
	///////////////////////////////
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
			
			rs = stmt.executeQuery("SELECT TYPE "
					+ "FROM PROJ_SALE ");	
			while(rs.next()){
				comboValues.add(rs.getString(1));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////
	/// Vector --> 2 Dim Array
	/////////////////////////////////////////////////////
	public static Object[][] to2DimArray(Vector v) {  
        Object[][] out = new Object[v.size()][0];  
        for (int i = 0; i < out.length; i++) {  
            out[i] = ((Vector) v.get(i)).toArray();  
        }  
        return out;  
    } 
	
	/////////////////////////////////////////////////////////
	/// Generate TextField Filler After Search
	/////////////////////////////////////////////////////////
	private void sqlSearch(String SearchField){
		
		try {
			if(conn == null){
			conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
			        + ":5030/jmille39db",
			        userName, password);
			}
			Statement stmt = conn.createStatement();
			ResultSet rs;
			
			rs = stmt.executeQuery("SELECT "
					+ "T1.UPC, T1.NAME, T1.TYPE, T1.START_DATE, T1.END_DATE, T1.PRICE, T1.PERCENT_OFF, "
					+ "ROUND(T1.PRICE - (T1.PRICE * (0.01 * T1.PERCENT_OFF)),2) AS SALE_PRICE "
					+ "FROM (SELECT PROJ_PRODUCTS.UPC, PROJ_PRODUCTS.NAME, PROJ_PRODUCTS.PRICE, PROJ_SALE.SALE_NUM, "
							+ "PROJ_SALE.PERCENT_OFF, PROJ_SALE.TYPE, PROJ_SALE.START_DATE, PROJ_SALE.END_DATE "
							+ "FROM PROJ_PRODUCTS, PROJ_SALE, PROJ_PRODSONSALE "
							+ "WHERE PROJ_PRODUCTS.UPC = PROJ_PRODSONSALE.UPC AND PROJ_SALE.SALE_NUM = PROJ_PRODSONSALE.SALE_NUM"
							+ ") AS T1 "
					+ "WHERE UPC = '" + SearchBox.getText() + "';");
			if(rs.next()){
				UPCbox.setText(rs.getString(1));
				NameBox.setText(rs.getString(2));
				TypeBox.setSelectedItem(rs.getString(3));
				StartBox.setText(rs.getString(4));
				EndBox.setText(rs.getString(5));
				PriceBox.setText(rs.getString(6));
				PercentBox.setText(rs.getString(7));
				SaleBox.setText(rs.getString(8));
			}
			else{
				rs = stmt.executeQuery("SELECT UPC, NAME, PRICE "
						+ "FROM PROJ_PRODUCTS "
						+ "WHERE UPC = '" + upc + "';");
				if(rs.next()){
				UPCbox.setText(rs.getString(1));
				NameBox.setText(rs.getString(2));
				TypeBox.setSelectedItem("");
				StartBox.setText("-");
				EndBox.setText("-");
				PriceBox.setText(rs.getString(3));
				PercentBox.setText("-");
				SaleBox.setText("-");
				}
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
/////////////////////////////////////////////////////////
/// Generate Table At Start
/////////////////////////////////////////////////////////
	private Object[][] sqlStart(){
		
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
					+ "T1.UPC, T1.NAME, T1.TYPE, T1.START_DATE, T1.END_DATE, T1.PRICE, T1.PERCENT_OFF, "
					+ "ROUND(T1.PRICE - (T1.PRICE * (0.01 * T1.PERCENT_OFF)),2) AS SALE_PRICE "
					+ "FROM (SELECT PROJ_PRODUCTS.UPC, PROJ_PRODUCTS.NAME, PROJ_PRODUCTS.PRICE, PROJ_SALE.SALE_NUM, "
							+ "PROJ_SALE.PERCENT_OFF, PROJ_SALE.TYPE, PROJ_SALE.START_DATE, PROJ_SALE.END_DATE "
							+ "FROM PROJ_PRODUCTS, PROJ_SALE, PROJ_PRODSONSALE "
							+ "WHERE PROJ_PRODUCTS.UPC = PROJ_PRODSONSALE.UPC AND PROJ_SALE.SALE_NUM = PROJ_PRODSONSALE.SALE_NUM"
							+ ") AS T1;");

            Vector<String> row;
            while (rs.next()) {

                row = new Vector<String>();
                for (int i = 1; i <= 8; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
                //Debugging                
            }
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Object[][] newdata = to2DimArray(data);
		return newdata;
	}
	
	public void itemStateChanged(ItemEvent ie) {
		//for applybutton
		if(buttonFlag==3){
			try {
				if(conn == null){
				conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
				        + ":5030/jmille39db",
				        userName, password);
				}
				Statement stmt = conn.createStatement();
				ResultSet rs;
		
				rs = stmt.executeQuery("SELECT START_DATE, END_DATE, PERCENT_OFF, SALE_NUM, "
						+ "ROUND(PRICE - (PRICE * (0.01 * PERCENT_OFF)),2) AS SALE_PRICE "
						+ "FROM PROJ_PRODUCTS, PROJ_SALE "
						+ "WHERE TYPE = '" + TypeBox.getSelectedItem().toString() + "' "
						+ "AND UPC = '" + upc + "';");
				
				if(rs.next()){
					StartBox.setText(rs.getString(1));
					EndBox.setText(rs.getString(2));
					PercentBox.setText(rs.getString(3));
					SaleNum=rs.getInt(4);
					SaleBox.setText(rs.getString(5));
					ApproveButton.setEnabled(true);
				}
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//for editbutton
		else if (buttonFlag==1){
			try {
				if(conn == null){
				conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
				        + ":5030/jmille39db",
				        userName, password);
				}
				Statement stmt = conn.createStatement();
				ResultSet rs;
				
				if(TypeBox.getSelectedItem().toString() != null){
				rs = stmt.executeQuery("SELECT START_DATE, END_DATE, PERCENT_OFF, SALE_NUM "
						+ "FROM PROJ_SALE "
						+ "WHERE TYPE = '" + TypeBox.getSelectedItem().toString() + "';");
				}
				else
					return;
				
				if(rs.next()){
					StartBox.setText(rs.getString(1));
					EndBox.setText(rs.getString(2));
					PercentBox.setText(rs.getString(3));
					SaleNum=rs.getInt(4);
					TypeBox.setEditable(true);
				}
				
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private class CustomTableModel extends DefaultTableModel {
		@Override
		public boolean isCellEditable(int row, int column){
			return false;
		}		
		
		public CustomTableModel(Object[][] data, String[] columnDef){
			super(data, columnDef);
		}
	}
}
