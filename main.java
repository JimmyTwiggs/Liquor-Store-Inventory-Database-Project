import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.sql.*;
import java.io.*;
import java.util.*;

public class main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main window = new main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Main Menu");
		frame.setBounds(100, 100, 700, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		JButton InventoryButton = new JButton("Inventory");
		InventoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Inventory f = new Inventory();
				f.setVisible(true);
			}
		});
		InventoryButton.setBounds(80, 321, 121, 52);
		frame.getContentPane().add(InventoryButton);
		
		JButton StaffButton = new JButton("Staff Details");
		StaffButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Staff a = new Staff();
				a.setVisible(true);
			}
		});
		StaffButton.setBounds(281, 321, 121, 52);
		frame.getContentPane().add(StaffButton);
		
		JButton SaleButton = new JButton("Sale Details");
		SaleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sales b = new Sales();
				b.setVisible(true);
			}
		});
		SaleButton.setBounds(482, 321, 121, 52);
		frame.getContentPane().add(SaleButton);
		
		JTextPane frmtdtxtfldFairgroundsDiscountBeverages = new JTextPane();
		frmtdtxtfldFairgroundsDiscountBeverages.setBackground(SystemColor.menu);
		frmtdtxtfldFairgroundsDiscountBeverages.setEditable(false);
		frmtdtxtfldFairgroundsDiscountBeverages.setFont(new Font("Tahoma", Font.PLAIN, 50));
		frmtdtxtfldFairgroundsDiscountBeverages.setForeground(Color.RED);
		frmtdtxtfldFairgroundsDiscountBeverages.setText("FAIRGROUNDS");
		frmtdtxtfldFairgroundsDiscountBeverages.setBounds(172, 37, 339, 67);
		frame.getContentPane().add(frmtdtxtfldFairgroundsDiscountBeverages);
		
		JTextPane frmtdtxtfldDiscountBeverages = new JTextPane();
		frmtdtxtfldDiscountBeverages.setBackground(SystemColor.menu);
		frmtdtxtfldDiscountBeverages.setEditable(false);
		frmtdtxtfldDiscountBeverages.setFont(new Font("Tahoma", Font.PLAIN, 50));
		frmtdtxtfldDiscountBeverages.setForeground(Color.RED);
		frmtdtxtfldDiscountBeverages.setText("DISCOUNT BEVERAGES");
		frmtdtxtfldDiscountBeverages.setBounds(78, 104, 528, 61);
		frame.getContentPane().add(frmtdtxtfldDiscountBeverages);
		
		JTextPane frmtdtxtfldInventoryAndStaff = new JTextPane();
		frmtdtxtfldInventoryAndStaff.setBackground(SystemColor.menu);
		frmtdtxtfldInventoryAndStaff.setEditable(false);
		frmtdtxtfldInventoryAndStaff.setFont(new Font("Tahoma", Font.PLAIN, 28));
		frmtdtxtfldInventoryAndStaff.setText("Inventory and Staff Database ");
		frmtdtxtfldInventoryAndStaff.setBounds(151, 185, 381, 40);
		frame.getContentPane().add(frmtdtxtfldInventoryAndStaff);
		
		final JTextField SearchBox = new JTextField();
		SearchBox.setBackground(Color.WHITE);
		SearchBox.setBounds(216, 252, 324, 22);
		frame.getContentPane().add(SearchBox);
		SearchBox.setColumns(10);
		
		SearchBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final Search c = new Search(SearchBox.getText());
				c.setVisible(true);
				}
		});
		
		JTextPane txtpnItemSearch = new JTextPane();
		txtpnItemSearch.setBackground(SystemColor.menu);
		txtpnItemSearch.setEditable(false);
		txtpnItemSearch.setText("Item Search:");
		txtpnItemSearch.setBounds(132, 252, 86, 22);
		frame.getContentPane().add(txtpnItemSearch);
		
		JSeparator separator = new JSeparator();
		separator.setSize(new Dimension(5, 5));
		separator.setPreferredSize(new Dimension(0, 5));
		separator.setForeground(Color.BLACK);
		separator.setBounds(66, 178, 552, 5);
		frame.getContentPane().add(separator);
	}
}
