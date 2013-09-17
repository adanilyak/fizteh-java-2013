/**
 * ReversePolishNoteEncoder.java
 * Calculator
 *
 * Created by Vladimir Mishatkin on 9/14/13
 */

import java.util.NoSuchElementException;
import java.util.Vector;

public class ReversePolishNoteEncoder
{
    private Vector<Integer> st;     //  operands stack
    private Vector<Character> op;   //  operators stack

    public ReversePolishNoteEncoder() {
        st = new Vector<Integer>();
        op = new Vector<Character>();
    }

    private boolean isSeparator(char c) {
        return c == ' ';
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isValidAlpha(char c) {
        return isDigit(c) || (Calculator.MyBase >= 10 && c >= 'A' && c <= 'A' + Calculator.MyBase - 10);
    }

    private int priorityForOperation(char op) {
        return op == '+' || op == '-' ? 1 :
               op == '*' || op == '/' || op == '%' ? 2 :
               -1;
    }

    private void processOperation(Vector<Integer> st, char op) {
        int r;
        int l;
        try {
            r = st.lastElement();  st.removeElementAt(st.size() - 1);
            l = st.lastElement();  st.removeElementAt(st.size() - 1);
        } catch (NoSuchElementException e) {
            System.err.println("Wrong input format: not enough operands for operator \'" + op + "\'.");
            throw(e);
        }
        switch (op) {
            case '+':  st.add(l + r);  break;
            case '-':  st.add(l - r);  break;
            case '*':  st.add(l * r);  break;
            case '/':  st.add(l / r);  break;
            case '%':  st.add(l % r);  break;
        }
    }

    public int calculate(String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (isSeparator(s.charAt(i))) {
                continue;
            }
            if (s.charAt(i) == '(') {
                op.add('(');
            } else if (s.charAt(i) == ')') {
                while (op.lastElement() != '(') {
                    processOperation(st, op.lastElement());
                    op.removeElementAt(op.size() - 1);
                }
                op.removeElementAt(op.size() - 1);
            }
            else if (isOperator(s.charAt(i))) {
                char currentOperation = s.charAt(i);
                while (!op.isEmpty() && priorityForOperation(op.lastElement()) >= priorityForOperation(s.charAt(i))) {
                    processOperation(st, op.lastElement());
                    op.removeElementAt(op.size() - 1);
                }
                op.add(currentOperation);
            }
            else {
                String operand = "";
                while (i < s.length() && isValidAlpha(s.charAt(i))) {
                    operand += String.valueOf(s.charAt(i++));
                }
                --i;
                try {
                    st.add(Integer.valueOf(operand, Calculator.MyBase));
                }
                catch (NumberFormatException e) {
                    System.err.println(e.getMessage());
                    System.err.println("Wrong input format: numbers should match " + Calculator.MyBase + "-based numeric system.");
                    throw(e);
                }
            }
        }
        while (!op.isEmpty()) {
            processOperation (st, op.lastElement());
            op.removeElementAt(op.size() - 1);
        }
        return st.lastElement();
    }
}
