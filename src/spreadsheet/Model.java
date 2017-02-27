package spreadsheet;

import javax.swing.table.*;

public class Model extends DefaultTableModel {

    public TableModel model;
    public Object[][] expression;

    Model() {

    }

    public TableModel Model(int rows, int columns) {

        model = new DefaultTableModel(rows, columns);
        expression = new Object[rows][columns];

        return model;

    }
}
