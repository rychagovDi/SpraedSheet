package spreadsheet;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

public class View extends JFrame {

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private int rows;
    private int columns;
    Model mod = new Model();
    TableModel model;
    JList<String> list;

    public View() {

    }

    public View(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        model = mod.Model(this.rows, this.columns);
        initComponents();
        setVisible(true);
    }

    ListSelectionListener listener = new ListSelectionListener() {

        TextHandler Handler = new TextHandler();

        int preRow;
        int preCol;
        boolean preExist = false;

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {

                int row = jTable1.getSelectedRow();
                int column = jTable1.getSelectedColumn();
                list.setSelectedIndex(row + 1);
                
                // Вывод выражения при фокусе на ячейке
                model.setValueAt(mod.expression[row][column], row, column);

                // Переход к новой ячейке (снятие фокуса с ячейки)
                    // Исключает повторный вызов метода (например, при смене 
                    // координат ячейки и по столбцам, и по строкам)
                if ((row != preRow || column != preCol) && preExist) {
                    
                    // Запоминание выражения, показывающееся при фокусе на ячейку
                    mod.expression[preRow][preCol] = jTable1.getModel().getValueAt(preRow, preCol);
                    
                    // Подсчет и вывод значения, соответствующего ячейке
                    model.setValueAt(Handler.whatIsIt(mod.expression[preRow][preCol], 
                            mod.expression, jTable1.getRowCount(), jTable1.getColumnCount()), preRow, preCol);
                }
                preRow = row;
                preCol = column;
                preExist = true;
                
                // Очистка списка для проверки на зацикливание
                Handler.rows.clear();
                Handler.cols.clear();

            }
        }
    };
    
    public String GetValueOfExpression(int row, int col){
        return mod.expression[row][col].toString();
    }

    //<editor-fold defaultstate="collapsed" desc="initComponents()">
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Spread Sheet");

        jTable1.setModel(model);
        jTable1.setToolTipText("");
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setRowHeight(18);
        jTable1.setRowSelectionAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
        );

        String indexes = "";
        for (int i = 1; i <= this.rows; i++) {
            indexes += " " + i;
        }
        list = new JList<>(indexes.split(" "));
        jScrollPane1.setRowHeaderView(list);
        

        jTable1.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.getSelectionModel().addListSelectionListener(listener);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.getColumnModel().getSelectionModel().addListSelectionListener(listener);

        pack();
    }
    //</editor-fold>

}
