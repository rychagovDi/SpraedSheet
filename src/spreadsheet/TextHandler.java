package spreadsheet;

import java.util.ArrayList;

public class TextHandler {
    
    ArrayList<String> digits = new ArrayList<>();
    ArrayList<String> operands = new ArrayList<>();
    ArrayList<String> letters = new ArrayList<>();
    
    // Списки номеров строк и солбцов для проверки на зацикливание
    ArrayList<Integer> rows = new ArrayList<>();
    ArrayList<Integer> cols = new ArrayList<>();
    
    Object[][] array;
    int rowCount;
    int colCount;
   
    
    public TextHandler() {
    }
    
    public Object whatIsIt(Object obj, Object[][] array, int rowCount, int colCount){
        this.array = array;       
        this.rowCount = rowCount;
        this.colCount = colCount;
        return whatIsIt(obj);

        
    }

    private Object whatIsIt(Object obj) {

        // ASCII 39 = '
        final char ch = 39;
        int dig;


            if (obj != null && obj.toString().length() != 0) {

                String text = obj.toString().trim();

                // Проверка на строку,состоящую только из пробелов
                if (text.equals("")) {
                    return null;
                }
                
                if (text.equals("#Зацикливание")){
                        return "#Зацикливание";
                    }
                
                if (text.equals("#Неправ. ссылка")){
                        return "#Неправ. ссылка";
                    }
                
                if (text.equals("#Выход за пределы таблицы")){
                        return "#Выход за пределы таблицы";
                    }
                

                char[] symbols = text.toCharArray();

                switch (symbols[0]) {
                    
                    case '=':
                        return returnAnswer(symbols);
                        
                    case ch:
                        return returnText(symbols);
                        
                    default:
                        try {
                            dig = Integer.parseInt(text.trim());
                            return dig;
                        } catch (Exception e) {
                            return "#Неизвестный знак";
                        }
                }
            }
        return null;
    }

    public String returnText(char[] symbols) {
        String text = "";
        for (int i = 1; i < symbols.length; i++) {
            text += symbols[i];
        }

        return text;
    }
    
    // Возвращает подсчитанное значение ячейки или ошибку, возникшую в ячейке
    public String returnAnswer(char[] symbols) {
        ArrayList<String> expression = new ArrayList<>();
        String arg = "";
        String delim = " ";
        boolean isCell = false;

        //<editor-fold defaultstate="collapsed" desc="List.add">
        letters.add("A");
        letters.add("B");
        letters.add("C");
        letters.add("D");
        letters.add("E");
        letters.add("F");
        letters.add("G");
        letters.add("H");
        letters.add("I");
        letters.add("G");
        letters.add("K");
        letters.add("L");
        letters.add("M");
        letters.add("N");
        letters.add("O");
        letters.add("P");
        letters.add("Q");
        letters.add("R");
        letters.add("S");
        letters.add("T");
        letters.add("U");
        letters.add("V");
        letters.add("W");
        letters.add("X");
        letters.add("Y");
        letters.add("Z");

        operands.add("+");
        operands.add("-");
        operands.add("*");
        operands.add("/");

        digits.add("1");
        digits.add("2");
        digits.add("3");
        digits.add("4");
        digits.add("5");
        digits.add("6");
        digits.add("7");
        digits.add("8");
        digits.add("9");
        digits.add("0");
        //</editor-fold>
 
        for (int i = 1; i < symbols.length; i++) {
            
            // Проверка, является ли очередной символ строки числом
            if ((digits.contains("" + symbols[i])) && isCell == false) {
                arg += symbols[i];
            } 
            
            // Проверка, является ли очередной символ строки буквой ссылки на
            // ячейку или цифрой ссылки на ячейки
            else if (letters.contains("" + symbols[i]) || (digits.contains("" + symbols[i]))&& isCell == true) {
                isCell = true;
                arg += symbols[i];
            } 
            
            // Проверка, является ли очередной символ строки операндом
            else if (operands.contains("" + symbols[i])) {
                
                // Проверка, являлся ли предыдущий элемент строки ссылкой на 
                // ячейку
                if (isCell){
                    
                    if (digits.contains("" + arg.charAt(0))){
                       return "#Неправ. ссылка"; 
                    }
                    
                    isCell = false;
                    
                    // Перевод ссылки на ячейку в числовые индексы
                    // и получение значения этой ячейки
                    Object result = whatIsIt(Translate(arg));
                    rows.clear();
                    cols.clear();
                    if (result != null && result.equals("#Зацикливание")){
                        return "#Зацикливание";
                    }
                    
                    if (result != null && result.equals("#Неправ. ссылка")){
                        return "#Неправ. ссылка";
                    }
                    
                     if (result != null && result.equals("#Выход за пределы таблицы")){
                        return "#Выход за пределы таблицы";
                    }
                    
                    arg = "";
                    
                    if (result!=null){
                        expression.add(result.toString());
                        expression.add("" + symbols[i]);
                    } else {
                        expression.add("0");
                        expression.add("" + symbols[i]);
                    }
                    
                } else {
                expression.add(arg);
                expression.add("" + symbols[i]);
                arg = "";
                }
            } else if (delim.equals("" + symbols[i])) {
                continue;
            } else {
                return "#Неизвестный знак";
            }
            
           
            
            // Проверки что и в коде выше, но для последнего элемента строки
            if ((i == (symbols.length - 1)) && arg.length() != 0) {
                if (digits.contains("" + arg.charAt(0)) && isCell == true){
                    return "#Неправ. ссылка"; 
                }
                if (letters.contains("" + arg.charAt(0)) && isCell == true){
                    
                    Object result = whatIsIt(Translate(arg));
                    
                    if (result != null && result.equals("#Зацикливание")){
                        return "#Зацикливание";
                    }
                    
                    if (result != null && result.equals("#Неправ. ссылка")){
                        return "#Неправ. ссылка";
                    }
                    
                    if (result != null && result.equals("#Выход за пределы таблицы")){
                        return "#Выход за пределы таблицы";
                    }  
                    
                    if (result!=null){
                        expression.add(result.toString());
                        break;
                    } else {
                        expression.add("0");
                        break;
                    }
                }
                expression.add(arg);
            }
            
        }
        return Calculate(expression);
    }
    
    // Подсчитывает выражение в ячейке
    public String Calculate(ArrayList<String> expression){
        
        int x = -1;
        int result;
        
        try{
        result = Integer.valueOf(expression.get(0));
        }
        catch (Exception e){
            return "#Неверн. выражение";
        }
        
        String op = "";
        
        if ( expression.size()%2 == 0 ){
            return "#Потерян аргумент";
        } else {
            
            for (int i = 1; i<expression.size(); i++){
                
                if ((expression.get(i)).equals(""))
                {
                    return "#Потерян аргумент";
                }
                
                // Получает значение аргумента
                if (digits.contains("" + expression.get(i).charAt(0))){
                    try{
                    x = Integer.valueOf(expression.get(i));
                    }
                    catch(NumberFormatException e){
                       return "#Неверн. выражение";
                    }
                } else if (("" + expression.get(i).charAt(0)).equals("#")){
                    return "#Неверн. выражение";
                } 
                
                // Получает операнд
                if (operands.contains(expression.get(i))){
                    op = expression.get(i);
                }
                
                if (x != -1){
                    if (!op.equals("")) {
                        switch (op) {
                            case "+":
                                result += x;
                                x = -1;
                                op = "";
                                break;
                            case "-":
                                result -= x;
                                x = -1;
                                op = "";
                                break;
                            case "*":
                                result *= x;
                                x = -1;
                                op = "";
                                break;
                            case "/":
                                try{
                                result /= x;
                                x = -1;
                                op = "";
                                } 
                                catch(Exception e){
                                    return "#Деление на 0";
                                }
                                break;
                        }
                    }
                }
        
            }
        }
        return Integer.toString(result);
    }
    
    // Перевод буквенного индекса ячейки в цифровой и возврат значения ячейки
    public Object Translate(String index){
        
        
        int row;
        int col = -1;
        
        
        // Буквы в названии ячейки
        String r = "";
        
        // Цифры в названии ячейки
        String c = "";
        
        for (int i = 0; i < index.length(); i ++){
            
            if (digits.contains("" + index.charAt(i))){
                r+= index.charAt(i);
            }
            if (letters.contains("" + index.charAt(i))){
                c+= index.charAt(i);
            }
        }
        
        try{
        row = Integer.valueOf(r) - 1;
        }
        catch (NumberFormatException e){
            return "#Неправ. ссылка";
        }
        
        // Перевод буквенного индекса ячейки в цифровой с помощью
        // формулы перевода числа 26-ричной степени счисления в десятичную
        for (int i = 0; i < c.length(); i++){
            col += Math.pow(26,(c.length() - 1 - i))*(letters.indexOf("" + c.charAt(i))+1);
        }
        
        if (row >= rowCount || col >= colCount){
            return "#Выход за пределы таблицы";
        }
        
        // Проверка на зацикливание 
        if (rows.contains(row) && cols.contains(col)){
            rows.clear();
            cols.clear();
            return "#Зацикливание";
        } else {
          rows.add(row);
          cols.add(col);
          return array[row][col];
        }
        
    }
    
}
