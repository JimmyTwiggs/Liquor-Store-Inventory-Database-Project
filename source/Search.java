import java.awt.EventQueue;

import javax.swing.JFrame;
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
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

import java.sql.*;
import java.io.*;
import java.util.*;


public class Search extends JFrame {
	
	static final String userName = "jmille39";
    static final String password = "Cosc*cyxy";
    private static Connection conn = null;

	private JPanel contentPane;
	private JTextField SearchBox;
	private JTable table;
	public String UPC="";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search frame = new Search();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Search(){
		this("");
	}
	
	public Search(final String x, int flag){
		setTitle("Inventory Search");
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
		textPane.setBounds(367, 11, 81, 22);
		contentPane.add(textPane);
		
		//////////////////////////////////////////
		// Search Field
		//////////////////////////////////////////		
		SearchBox = new JTextField();
		SearchBox.setColumns(10);
		SearchBox.setBackground(Color.WHITE);
		SearchBox.setBounds(449, 11, 225, 22);
		contentPane.add(SearchBox);
		SearchBox.setText(x);
		SearchBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[][] newdata = sql(SearchBox.getText());
				table.setModel(new CustomTableModel(
					newdata,
					new String[] {
						"UPC", "Name", "Size", "In Stock", "Region", "Distributor", "ABV", "Cost", "Price"
					}
				));
				table.getColumnModel().getColumn(0).setPreferredWidth(55);
				table.getColumnModel().getColumn(1).setPreferredWidth(230);
				table.getColumnModel().getColumn(2).setPreferredWidth(38);
				table.getColumnModel().getColumn(3).setPreferredWidth(40);
				table.getColumnModel().getColumn(4).setPreferredWidth(70);
				table.getColumnModel().getColumn(5).setPreferredWidth(70);
				table.getColumnModel().getColumn(6).setPreferredWidth(22);
				table.getColumnModel().getColumn(7).setPreferredWidth(37);
				table.getColumnModel().getColumn(8).setPreferredWidth(37);
			}
		});
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 46, 662, 346);
		contentPane.add(scrollPane);
		Object[][] newdata = sql(x);
		table = new JTable(100, 6);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new CustomTableModel(
			newdata,
			new String[] {
				"UPC", "Name", "Size", "In Stock", "Region", "Distributor", "ABV", "Cost", "Price"
			}
		));
		
		table.getColumnModel().getColumn(0).setPreferredWidth(55);
		table.getColumnModel().getColumn(1).setPreferredWidth(230);
		table.getColumnModel().getColumn(2).setPreferredWidth(38);
		table.getColumnModel().getColumn(3).setPreferredWidth(40);
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.getColumnModel().getColumn(5).setPreferredWidth(70);
		table.getColumnModel().getColumn(6).setPreferredWidth(22);
		table.getColumnModel().getColumn(7).setPreferredWidth(37);
		table.getColumnModel().getColumn(8).setPreferredWidth(37);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(table);
		
		final Search derp = this;
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable table = (JTable)e.getSource();
					int row = table.getSelectedRow();
					UPC = table.getValueAt(row, 0).toString();
					derp.dispatchEvent(new WindowEvent(derp, WindowEvent.WINDOW_CLOSING));
				}
			}
		});
		
		
	}
	
	/**
	 * Create the frame.
	 */
	public Search(String x) {
		setTitle("Inventory Search");
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
		textPane.setBounds(367, 11, 81, 22);
		contentPane.add(textPane);
		
		//////////////////////////////////////////
		// Search Field
		//////////////////////////////////////////		
		SearchBox = new JTextField();
		SearchBox.setColumns(10);
		SearchBox.setBackground(Color.WHITE);
		SearchBox.setBounds(449, 11, 225, 22);
		contentPane.add(SearchBox);
		SearchBox.setText(x);
		SearchBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[][] newdata = sql(SearchBox.getText());
				table.setModel(new CustomTableModel(
					newdata,
					new String[] {
						"UPC", "Name", "Size", "In Stock", "Region", "Distributor", "ABV", "Cost", "Price"
					}
				));
				table.getColumnModel().getColumn(0).setPreferredWidth(55);
				table.getColumnModel().getColumn(1).setPreferredWidth(230);
				table.getColumnModel().getColumn(2).setPreferredWidth(38);
				table.getColumnModel().getColumn(3).setPreferredWidth(40);
				table.getColumnModel().getColumn(4).setPreferredWidth(70);
				table.getColumnModel().getColumn(5).setPreferredWidth(70);
				table.getColumnModel().getColumn(6).setPreferredWidth(22);
				table.getColumnModel().getColumn(7).setPreferredWidth(37);
				table.getColumnModel().getColumn(8).setPreferredWidth(37);
			}
		});
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 46, 662, 346);
		contentPane.add(scrollPane);
		table = new JTable(100, 6);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Object[][] newdata = sql(x);
		table.setModel(new CustomTableModel(
			newdata,
			new String[] {
				"UPC", "Name", "Size", "In Stock", "Region", "Distributor", "ABV", "Cost", "Price"
			}
		));
		
		table.getColumnModel().getColumn(0).setPreferredWidth(55);
		table.getColumnModel().getColumn(1).setPreferredWidth(230);
		table.getColumnModel().getColumn(2).setPreferredWidth(38);
		table.getColumnModel().getColumn(3).setPreferredWidth(40);
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.getColumnModel().getColumn(5).setPreferredWidth(70);
		table.getColumnModel().getColumn(6).setPreferredWidth(22);
		table.getColumnModel().getColumn(7).setPreferredWidth(37);
		table.getColumnModel().getColumn(8).setPreferredWidth(37);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(table);
		
		final Search derp = this;
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable table = (JTable)e.getSource();
					int row = table.getSelectedRow();
					UPC = table.getValueAt(row, 0).toString();
					Inventory a = new Inventory(UPC);
					a.setVisible(true);
					derp.dispatchEvent(new WindowEvent(derp, WindowEvent.WINDOW_CLOSING));
				}
			}
		});
		
		
	}
	
	public static Object[][] to2DimArray(Vector v) {  
        Object[][] out = new Object[v.size()][0];  
        for (int i = 0; i < out.length; i++) {  
            out[i] = ((Vector) v.get(i)).toArray();  
        }  
        return out;  
    } 
	
	private Object[][] sql(String SearchField){
		
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		
		try {
			if(conn == null){
			conn = DriverManager.getConnection("jdbc:mysql://triton.towson.edu"
			        + ":5030/jmille39db",
			        userName, password);
			}
			Statement stmt = conn.createStatement();
			ResultSet rs;
			
			Boolean isNumber = true;
			try {
				Integer.parseInt(SearchField);
			} catch (Exception ex){
				isNumber = false;
			}
			
			if (!isNumber){
			rs = stmt.executeQuery("SELECT "
					+ "UPC, NAME, SIZE, NUM_IN_STOCK, REGION, DISTRIBUTOR, ABV, COST, PRICE "
					+ "FROM PROJ_PRODUCTS WHERE Name like '%" + SearchField + "%'"
							+ " OR Region like '%" + SearchField + "%'"
									+ " OR Distributor like '%" + SearchField + "%';");
			} else {
				rs = stmt.executeQuery("SELECT "
						+ "UPC, NAME, SIZE, NUM_IN_STOCK, REGION, DISTRIBUTOR, ABV, COST, PRICE "
						+ "FROM PROJ_PRODUCTS WHERE UPC = '" + SearchField + "';");			
			}
			
            Vector<String> row;
            while (rs.next()) {

                row = new Vector<String>();
                for (int i = 1; i <= 9; i++) {
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

