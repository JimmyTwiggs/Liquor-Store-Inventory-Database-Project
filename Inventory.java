import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JTextField;

import java.awt.Color;

import javax.swing.JTextPane;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;

import java.sql.*;
import java.io.*;
import java.util.*;


public class Inventory extends JFrame {

	private String upc;
	static final String userName = "jmille39";
    static final String password = "Cosc*cyxy";
    private static Connection conn = null;
    private int ButtonFlag = 5;
    Inventory derp = this;
	
	private JPanel contentPane;
	private JTextField SearchBox;
	private JTextPane txtpnName;
	private JTextField nameBox;
	private JTextPane txtpnUpc;
	private JTextField UPCbox;
	private JTextPane txtpnSize;
	private JTextField sizeBox;
	private JTextPane txtpnAvailible;
	private JTextField NumInStockBox;
	private JTextPane txtpnAbv;
	private JTextField ABVbox;
	private JTextPane txtpnRegion;
	private JTextField regionBox;
	private JTextPane txtpnCost;
	private JTextField CostBox;
	private JTextPane txtpnPrice;
	private JTextField PriceBox;
	private JTextPane txtpnDistributor;
	private JTextField DistroBox;
	private JButton ApproveButton;
	private JButton EditButton;
	private JButton NewButton;
	private JTextField deptNumBox;
	private JTextField deptBox;
	private JButton DeleteButton;

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inventory frame = new Inventory();
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
	public Inventory() {
		this("");
	}
	
	public Inventory(String upc){
		this.upc = upc;
		initialize();
	}
	public void initialize(){
		setTitle("Inventory Details");
		setBounds(100, 100, 700, 450);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		////////////////////////////////////////////////////
		//      Inventory Search Field
		////////////////////////////////////////////////////
		
		SearchBox = new JTextField();
		SearchBox.setDisabledTextColor(Color.BLACK);
		SearchBox.setColumns(10);
		SearchBox.setBackground(Color.WHITE);
		SearchBox.setBounds(449, 11, 225, 22);
		contentPane.add(SearchBox);
		SearchBox.setText(upc);
		SearchBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final Search c = new Search(SearchBox.getText(), 0);
				c.setVisible(true);
				c.addWindowListener(new WindowAdapter(){
					public void windowClosing(WindowEvent e){
						upc = c.UPC;
						SearchBox.setText(upc);
						sql(upc);
						if(!(upc.equals(""))){
							EditButton.setEnabled(true);
							DeleteButton.setEnabled(true);
						}
					}
				});
			}
		});
		////////////////////////////////////////////////////////
		
		
		
		
		JTextPane textPane = new JTextPane();
		textPane.setText("Item Search:");
		textPane.setEditable(false);
		textPane.setBackground(SystemColor.menu);
		textPane.setBounds(351, 11, 86, 22);
		contentPane.add(textPane);
		
		txtpnName = new JTextPane();
		txtpnName.setText("Name:");
		txtpnName.setEditable(false);
		txtpnName.setBackground(SystemColor.menu);
		txtpnName.setBounds(59, 58, 44, 22);
		contentPane.add(txtpnName);
		
		nameBox = new JTextField();
		nameBox.setDisabledTextColor(Color.BLACK);
		nameBox.setEditable(false);
		nameBox.setColumns(10);
		nameBox.setBackground(Color.WHITE);
		nameBox.setBounds(115, 58, 516, 22);
		contentPane.add(nameBox);
		
		txtpnUpc = new JTextPane();
		txtpnUpc.setText("UPC:");
		txtpnUpc.setEditable(false);
		txtpnUpc.setBackground(SystemColor.menu);
		txtpnUpc.setBounds(69, 108, 34, 22);
		contentPane.add(txtpnUpc);
		
		UPCbox = new JTextField();
		UPCbox.setDisabledTextColor(Color.BLACK);
		UPCbox.setEditable(false);
		UPCbox.setColumns(10);
		UPCbox.setBackground(Color.WHITE);
		UPCbox.setBounds(115, 108, 194, 22);
		contentPane.add(UPCbox);
		
		txtpnSize = new JTextPane();
		txtpnSize.setText("Size:");
		txtpnSize.setEditable(false);
		txtpnSize.setBackground(SystemColor.menu);
		txtpnSize.setBounds(69, 158, 34, 22);
		contentPane.add(txtpnSize);
		
		sizeBox = new JTextField();
		sizeBox.setDisabledTextColor(Color.BLACK);
		sizeBox.setEditable(false);
		sizeBox.setColumns(10);
		sizeBox.setBackground(Color.WHITE);
		sizeBox.setBounds(115, 158, 194, 22);
		contentPane.add(sizeBox);
		
		txtpnAvailible = new JTextPane();
		txtpnAvailible.setText("Availible:");
		txtpnAvailible.setEditable(false);
		txtpnAvailible.setBackground(SystemColor.menu);
		txtpnAvailible.setBounds(45, 208, 58, 22);
		contentPane.add(txtpnAvailible);
		
		NumInStockBox = new JTextField();
		NumInStockBox.setDisabledTextColor(Color.BLACK);
		NumInStockBox.setEditable(false);
		NumInStockBox.setColumns(10);
		NumInStockBox.setBackground(Color.WHITE);
		NumInStockBox.setBounds(115, 208, 194, 22);
		contentPane.add(NumInStockBox);
		
		txtpnAbv = new JTextPane();
		txtpnAbv.setText("ABV:");
		txtpnAbv.setEditable(false);
		txtpnAbv.setBackground(SystemColor.menu);
		txtpnAbv.setBounds(69, 258, 34, 22);
		contentPane.add(txtpnAbv);
		
		ABVbox = new JTextField();
		ABVbox.setDisabledTextColor(Color.BLACK);
		ABVbox.setEditable(false);
		ABVbox.setColumns(10);
		ABVbox.setBackground(Color.WHITE);
		ABVbox.setBounds(115, 258, 194, 22);
		contentPane.add(ABVbox);
		
		txtpnRegion = new JTextPane();
		txtpnRegion.setText("Region:");
		txtpnRegion.setEditable(false);
		txtpnRegion.setBackground(SystemColor.menu);
		txtpnRegion.setBounds(359, 108, 50, 22);
		contentPane.add(txtpnRegion);
		
		regionBox = new JTextField();
		regionBox.setDisabledTextColor(Color.BLACK);
		regionBox.setEditable(false);
		regionBox.setColumns(10);
		regionBox.setBackground(Color.WHITE);
		regionBox.setBounds(421, 108, 210, 22);
		contentPane.add(regionBox);
		
		txtpnCost = new JTextPane();
		txtpnCost.setText("Cost:");
		txtpnCost.setEditable(false);
		txtpnCost.setBackground(SystemColor.menu);
		txtpnCost.setBounds(373, 208, 36, 22);
		contentPane.add(txtpnCost);
		
		CostBox = new JTextField();
		CostBox.setDisabledTextColor(Color.BLACK);
		CostBox.setEditable(false);
		CostBox.setColumns(10);
		CostBox.setBackground(Color.WHITE);
		CostBox.setBounds(421, 208, 210, 22);
		contentPane.add(CostBox);
		
		txtpnPrice = new JTextPane();
		txtpnPrice.setText("Price:");
		txtpnPrice.setEditable(false);
		txtpnPrice.setBackground(SystemColor.menu);
		txtpnPrice.setBounds(370, 257, 39, 23);
		contentPane.add(txtpnPrice);
		
		PriceBox = new JTextField();
		PriceBox.setDisabledTextColor(Color.BLACK);
		PriceBox.setEditable(false);
		PriceBox.setColumns(10);
		PriceBox.setBackground(Color.WHITE);
		PriceBox.setBounds(421, 258, 210, 22);
		contentPane.add(PriceBox);
		
		txtpnDistributor = new JTextPane();
		txtpnDistributor.setText("Distributor:");
		txtpnDistributor.setEditable(false);
		txtpnDistributor.setBackground(SystemColor.menu);
		txtpnDistributor.setBounds(339, 158, 70, 22);
		contentPane.add(txtpnDistributor);
		
		DistroBox = new JTextField();
		DistroBox.setDisabledTextColor(Color.BLACK);
		DistroBox.setEditable(false);
		DistroBox.setColumns(10);
		DistroBox.setBackground(Color.WHITE);
		DistroBox.setBounds(421, 158, 210, 22);
		contentPane.add(DistroBox);

		deptNumBox = new JTextField();
		deptNumBox.setEditable(false);
		deptNumBox.setDisabledTextColor(Color.BLACK);
		deptNumBox.setColumns(10);
		deptNumBox.setBackground(Color.WHITE);
		deptNumBox.setBounds(115, 305, 194, 22);
		contentPane.add(deptNumBox);
		deptNumBox.getDocument().addDocumentListener(new DocumentListener() {
		      public void changedUpdate(DocumentEvent e) {
		    	  
		      }
		      public void removeUpdate(DocumentEvent e) {
		    	  deptBox.setText("");
		      }
		      public void insertUpdate(DocumentEvent e) {
		    	  try{
			  			
		    			if(conn == null){
		    				conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
		    				        + ":5030/jmille39db",
		    				        userName, password);
		    			}
		    			Statement stmt = conn.createStatement();
		    			ResultSet rs;
		    			
		    			rs = stmt.executeQuery("SELECT NAME "
		    					+ "FROM PROJ_DEPTS WHERE DEPTNUM = '" + deptNumBox.getText() + "';");	
		    			
		    			while (rs.next()) {
		    				deptBox.setText(rs.getString(1));
		    			}
		    				
		    		} catch (SQLException e1) {
		    				// TODO Auto-generated catch block
		    				e1.printStackTrace();
		    		}
		      }
		});
		    
		
		JTextPane txtpnDept = new JTextPane();
		txtpnDept.setText("Dept #:");
		txtpnDept.setEditable(false);
		txtpnDept.setBackground(SystemColor.menu);
		txtpnDept.setBounds(53, 305, 50, 22);
		contentPane.add(txtpnDept);
		
		JTextPane txtpnDepartment = new JTextPane();
		txtpnDepartment.setText("Department:");
		txtpnDepartment.setEditable(false);
		txtpnDepartment.setBackground(SystemColor.menu);
		txtpnDepartment.setBounds(331, 305, 78, 22);
		contentPane.add(txtpnDepartment);
		
		deptBox = new JTextField();
		deptBox.setEditable(false);
		deptBox.setDisabledTextColor(Color.BLACK);
		deptBox.setColumns(10);
		deptBox.setBackground(Color.WHITE);
		deptBox.setBounds(421, 305, 210, 22);
		contentPane.add(deptBox);
		
		ApproveButton = new JButton("Approve");
		ApproveButton.setEnabled(false);
		ApproveButton.setBounds(364, 369, 110, 23);
		contentPane.add(ApproveButton);
		ApproveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int reply = JOptionPane.showConfirmDialog(ApproveButton, "Do you wish to approve these Changes?", "Confirm Database Changes", JOptionPane.YES_NO_CANCEL_OPTION);
				if (reply == JOptionPane.YES_OPTION)
			    {
					if(!(UPCbox.getText().equals("")) && !(nameBox.getText().equals("")) && !(sizeBox.getText().equals("")) 
							&& !(NumInStockBox.getText().equals("")) && !(regionBox.getText().equals("")) && !(DistroBox.getText().equals(""))
							&& !(ABVbox.getText().equals("")) && !(CostBox.getText().equals("")) && !(PriceBox.getText().equals(""))
							&& !(deptNumBox.getText().equals(""))){
						if(ButtonFlag == 1){
							
							try{
								
								if(conn == null){
									conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
									        + ":5030/jmille39db",
									        userName, password);
									}
							Statement stmt = conn.createStatement();
							stmt.executeUpdate("UPDATE PROJ_PRODUCTS "
									+ "SET UPC = '" + UPCbox.getText() + "', "
									+ "NAME = '" + nameBox.getText() + "', "
									+ "SIZE = '" + sizeBox.getText() + "', "
									+ "NUM_IN_STOCK = '" + NumInStockBox.getText() + "', "
									+ "REGION = '" + regionBox.getText() + "', "
									+ "DISTRIBUTOR = '" + DistroBox.getText() + "', "
									+ "ABV = '" + ABVbox.getText() + "', "
									+ "COST = '" + CostBox.getText() + "', "
									+ "PRICE = '" + PriceBox.getText() + "', "
									+ "DNUM = '" + deptNumBox.getText() + "' "
									+ "WHERE UPC = '" + upc + "';");
							} catch(SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}	
						}
						else
						{
							upc = UPCbox.getText();
							SearchBox.setText(upc);
							
							try{
								
								if(conn == null){
									conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
									        + ":5030/jmille39db",
									        userName, password);
									}
							Statement stmt = conn.createStatement();
							stmt.executeUpdate("INSERT INTO PROJ_PRODUCTS "
									+ "(UPC, NAME, SIZE, NUM_IN_STOCK, REGION, DISTRIBUTOR, ABV, COST, PRICE, DNUM) "
									+ "VALUES " 
									+ "('" + UPCbox.getText() + "', '" + nameBox.getText() + "', '" + sizeBox.getText() + "', '"
									+ NumInStockBox.getText() + "', '" + regionBox.getText() + "', '" + DistroBox.getText() + "', '"
									+ ABVbox.getText() + "', '" + CostBox.getText() + "', '" + PriceBox.getText() + "', '" + deptNumBox.getText() + "');");
							} catch(SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}	
						}
					}
					else
					{
						JOptionPane.showMessageDialog(derp, "You Left Something Blank");
						return;
					}
					nameBox.setEditable(false);
					UPCbox.setEditable(false);
					sizeBox.setEditable(false);
					NumInStockBox.setEditable(false);
					ABVbox.setEditable(false);
					regionBox.setEditable(false);
					CostBox.setEditable(false);
					PriceBox.setEditable(false);
					DistroBox.setEditable(false);
					SearchBox.setEditable(true);
					deptNumBox.setEditable(false);
					deptBox.setEditable(false);
					ApproveButton.setEnabled(false);
					NewButton.setEnabled(true);
					if(!(upc.equals(""))){
						EditButton.setEnabled(true);
						DeleteButton.setEnabled(true);
					}
					else
						DeleteButton.setEnabled(false);
					DeleteButton.setText("Delete Entry");
			    }
				else if (reply == JOptionPane.NO_OPTION)
			    {
			      
			    }
				else
				{
					nameBox.setEditable(false);
					UPCbox.setEditable(false);
					sizeBox.setEditable(false);
					NumInStockBox.setEditable(false);
					ABVbox.setEditable(false);
					regionBox.setEditable(false);
					CostBox.setEditable(false);
					PriceBox.setEditable(false);
					DistroBox.setEditable(false);
					deptNumBox.setEditable(false);
					deptBox.setEditable(false);
					SearchBox.setEditable(true);
					ApproveButton.setEnabled(false);
					NewButton.setEnabled(true);
					if(!(upc.equals(""))){
						EditButton.setEnabled(true);
						DeleteButton.setEnabled(true);
					}
					else
						DeleteButton.setEnabled(false);
					DeleteButton.setText("Delete Entry");
				}			
			}
		});
		
		EditButton = new JButton("Edit Entry");
		if(upc=="")
			EditButton.setEnabled(false);
		EditButton.setBounds(206, 369, 110, 23);
		contentPane.add(EditButton);
		EditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ButtonFlag = 1;
				nameBox.setEditable(true);
				UPCbox.setEditable(true);
				sizeBox.setEditable(true);
				NumInStockBox.setEditable(true);
				ABVbox.setEditable(true);
				regionBox.setEditable(true);
				CostBox.setEditable(true);
				PriceBox.setEditable(true);
				DistroBox.setEditable(true);
				deptNumBox.setEditable(true);
				deptBox.setEditable(false);
				SearchBox.setEditable(false);
				ApproveButton.setEnabled(true);
				NewButton.setEnabled(false);
				EditButton.setEnabled(false);
				DeleteButton.setEnabled(true);
				DeleteButton.setText("Cancel");
			}
		});
			
		
		NewButton = new JButton("New Entry");
		NewButton.setBounds(48, 369, 110, 23);
		contentPane.add(NewButton);
		NewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ButtonFlag = 0;
				nameBox.setEditable(true);
				nameBox.setText("");
				UPCbox.setEditable(true);
				UPCbox.setText("");
				sizeBox.setEditable(true);
				sizeBox.setText("");
				NumInStockBox.setEditable(true);
				NumInStockBox.setText("");
				ABVbox.setEditable(true);
				ABVbox.setText("");
				regionBox.setEditable(true);
				regionBox.setText("");
				CostBox.setEditable(true);
				CostBox.setText("");
				PriceBox.setEditable(true);
				PriceBox.setText("");
				DistroBox.setEditable(true);
				DistroBox.setText("");
				deptNumBox.setEditable(true);
				deptNumBox.setText("");
				deptBox.setEditable(false);
				deptBox.setText("");
				SearchBox.setEditable(false);
				SearchBox.setText("");
				ApproveButton.setEnabled(true);
				NewButton.setEnabled(false);
				EditButton.setEnabled(false);
				DeleteButton.setEnabled(true);
				DeleteButton.setText("Cancel");
				upc="";
			}
		});
		
		DeleteButton = new JButton("Delete Entry");
		DeleteButton.setEnabled(false);
		DeleteButton.setBounds(522, 368, 110, 23);
		contentPane.add(DeleteButton);
		DeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(ButtonFlag==0||ButtonFlag==1){
					DeleteButton.setText("Delete Entry");
					if(!(upc.equals(""))){
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
					if(!(upc.equals(""))){
						int reply = JOptionPane.showConfirmDialog(ApproveButton, "Are You Sure You Wish To Delete This Item?", "Confirm Delete Item", JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION)
					    {
							try{
								
								if(conn == null){
									conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
									        + ":5030/jmille39db",
									        userName, password);
									}
							Statement stmt = conn.createStatement();
							stmt.executeUpdate("DELETE FROM PROJ_PRODUCTS "
									+ "WHERE UPC = '" + upc + "';");
								
							
							} catch(SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					    }
						else
							return;
					}
					
					nameBox.setText("");
					UPCbox.setText("");
					sizeBox.setText("");
					NumInStockBox.setText("");
					ABVbox.setText("");
					regionBox.setText("");
					CostBox.setText("");
					PriceBox.setText("");
					DistroBox.setText("");
					deptNumBox.setText("");
					deptBox.setText("");
					SearchBox.setText("");
					EditButton.setEnabled(false);
					DeleteButton.setEnabled(false);
				}
				
				ButtonFlag = 3;
				
				nameBox.setEditable(false);
				UPCbox.setEditable(false);
				sizeBox.setEditable(false);
				NumInStockBox.setEditable(false);
				ABVbox.setEditable(false);
				regionBox.setEditable(false);
				CostBox.setEditable(false);
				PriceBox.setEditable(false);
				DistroBox.setEditable(false);
				deptNumBox.setEditable(false);
				deptBox.setEditable(false);
				SearchBox.setEditable(true);
				ApproveButton.setEnabled(false);
				NewButton.setEnabled(true);
			}
		});
		
		sql(upc);
	}
	
	private void sql(String SearchField){
		try{
			
		if(conn == null){
			conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
			        + ":5030/jmille39db",
			        userName, password);
			}
			Statement stmt = conn.createStatement();
			ResultSet rs;
		
		rs = stmt.executeQuery("SELECT "
				+ "UPC, NAME, SIZE, NUM_IN_STOCK, REGION, DISTRIBUTOR, ABV, COST, PRICE, DNUM "
				+ "FROM PROJ_PRODUCTS WHERE UPC = '" + SearchField + "';");	
		
			while (rs.next()) {
				UPCbox.setText(rs.getString(1));
				nameBox.setText(rs.getString(2));
				sizeBox.setText(rs.getString(3));
				NumInStockBox.setText(rs.getString(4));
				regionBox.setText(rs.getString(5));
				DistroBox.setText(rs.getString(6));
				ABVbox.setText(rs.getString(7));
				CostBox.setText(rs.getString(8));
				PriceBox.setText(rs.getString(9));
				deptNumBox.setText(rs.getString(10));
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
