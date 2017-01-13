
import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column){
                return false;
        }		

        public TableModel(Object[][] data, String[] columnDef){
                super(data, columnDef);
        }
}
