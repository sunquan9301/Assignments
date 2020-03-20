package assigment2;

import assignment3.Params;

import java.util.ArrayList;
import java.util.HashMap;

public class Ass2 {
    //Field parser
    private Parser parser;

    // filed regNumber
    private String regNumber;

    //the root of tree
    private ExpTree root;
    // to judge if the tree is let tree
    private boolean isLetTree = false;
    // a list to record value of indentifier of let tree
    private HashMap<String, Integer> identifierMapValue = new HashMap<String, Integer>();
    //a list to record the miss identifier of Let Expression to offer warn message after output the result of expression
    private ArrayList<String> missIdentifierOfLetTree = new ArrayList<>();

    /**
     * main method
     */
    public static void main(String[] args) {
        Ass2 ass2 = new Ass2();
        ass2.start();
    }


    //start to run program
    private void start() {
        parser = new Parser();
        //input basic information
        System.out.println("Please type your Reg number ");
        regNumber = parser.getLine().trim();
        System.out.println("Reg number " + regNumber);

        //do loop to process expression
        while (true) {
            System.out.println("Welcome to Fred's expression evaluation program. Please type an expression");
            try {
                root = parser.parseLine();
                isLetTree = root.nodeType == ExpTree.TreeType.LET_NODE;
                postOrder();
                //In order
                System.out.println(toString());
                calResultOfExpress();
                //to check if quit the loop
                if (!optionToStopOrContinueExecute()) break;
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                if (!optionToStopOrContinueExecute()) break;
            }
        }
    }

    private void recordIdentifierValueWithLetTree(ExpTree node) {
        if (node == null) return;
        if (node.nodeType == ExpTree.TreeType.DEF_NODE) {
            identifierMapValue.put(node.val.trim(), resultOfExpress(node.leftChild));
        }
        recordIdentifierValueWithLetTree(node.leftChild);
        recordIdentifierValueWithLetTree(node.rightChild);
    }

    //the entrance to cal the value of expression
    private void calResultOfExpress() {
        System.out.print("The result of Express is: ");
        int result = 0;
        if (isLetTree) {
            //for let tree
            identifierMapValue.clear();
            missIdentifierOfLetTree.clear();
            //1.record identifier of left child of root
            recordIdentifierValueWithLetTree(root.leftChild);
            //2.cal the value of rightChild of root
            result = resultOfExpress(root.rightChild);
        } else result = resultOfExpress(root);
        System.out.println(result);
        if (isLetTree && missIdentifierOfLetTree.size() > 0) {
            System.out.println("Warn message：these identifiers " + missIdentifierOfLetTree.toString() + " have not been given a value in Let expression");
        }
    }

    //recursion to cal the value
    private int resultOfExpress(ExpTree node) {
        if (node.nodeType.equals(ExpTree.TreeType.NUM_NODE)) return Integer.valueOf(node.val);
        else if (node.nodeType.equals(ExpTree.TreeType.ID_NODE)) return valueOfIdentifier(node.val);
        else if (node.nodeType.equals(ExpTree.TreeType.OPT_NODE)) {
            switch (node.val) {
                case "+":
                    return resultOfExpress(node.leftChild) + resultOfExpress(node.rightChild);
                case "-":
                    return resultOfExpress(node.leftChild) - resultOfExpress(node.rightChild);
                case "*":
                    return resultOfExpress(node.leftChild) * resultOfExpress(node.rightChild);
                case "/":
                    return resultOfExpress(node.leftChild) / resultOfExpress(node.rightChild);
                case "%":
                    return resultOfExpress(node.leftChild) % resultOfExpress(node.rightChild);
                case "^":
                    return powInt(resultOfExpress(node.leftChild), resultOfExpress(node.rightChild));
                default:
                    throw new IllegalArgumentException("unknown opt node, val = " + node.val);
            }
        } else
            throw new IllegalArgumentException("unaccess node type when cal result of Express, type = " + node.nodeType);
    }

    /**
     * @param val left of ^
     * @param times right of ^
     * @return val of ^ opt
     */
    private int powInt(int val, int times) {
        if (times < 0) throw new ArithmeticException("the right child of a ^ node is negative");
        if (times == 0) return 1;

        int result = 1;
        for (int i = 0; i < times; i++)
            result = result * val;
        return result;
    }

    //the entrence of post order method, in this method it distinguish let root node and normal root node
    private void postOrder() {
        System.out.print("Post-order: ");
        if (isLetTree) postOrder(root.rightChild);
        else postOrder(root);
        System.out.println();
    }


    /**
     * @return in order string of tree
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        inOrder(root, sb);
        return "In-order: " + sb.toString();
    }

    /**
     * according to different type of node, process the inorder string.
     */
    private void inOrder(ExpTree node, StringBuilder sb) {
        if (node == null) return;
        if (node.nodeType.equals(ExpTree.TreeType.OPT_NODE)) {
            if (node.val.equals("+") || node.val.equals("-") || node.val.equals("%")) {
                sb.append("(");
                inOrder(node.leftChild, sb);
                sb.append(node.val);
                inOrder(node.rightChild, sb);
                sb.append(")");
            } else {
                inOrder(node.leftChild, sb);
                sb.append(node.val);
                inOrder(node.rightChild, sb);
            }
        } else if (node.nodeType.equals(ExpTree.TreeType.LET_NODE)) {
            sb.append("let ");
            inOrder(node.leftChild, sb);
            sb.append(" in ");
            inOrder(node.rightChild, sb);
        } else if (node.nodeType.equals(ExpTree.TreeType.AND_NODE)) {
            inOrder(node.leftChild, sb);
            sb.append(" and ");
            inOrder(node.rightChild, sb);
        } else if (node.nodeType.equals(ExpTree.TreeType.DEF_NODE)) {
            sb.append(node.val + " = ");
            inOrder(node.leftChild, sb);
        } else if (node.nodeType.equals(ExpTree.TreeType.NUM_NODE)) {
            if (node.val.length() > 1) sb.append("(" + node.val + ")");
            else sb.append(node.val);
        } else if (node.nodeType.equals(ExpTree.TreeType.ID_NODE)) {
            sb.append(node.val);
        }
    }

    //the method to print each val of node
    private void postOrder(ExpTree node) {
        if (node == null) return;
        postOrder(node.leftChild);
        postOrder(node.rightChild);
        System.out.print(node.val + " ");
    }


    /**
     * get the value of Identifier, for let tree, the value recorded in identifierMapValue,
     * for normal three A -> 25; Z->0.
     * @return the value of identifier
     */
    private int valueOfIdentifier(String identifier) {
        if (!isLetTree) return 25 - (identifier.charAt(0) - 'A');
        if (identifierMapValue.containsKey(identifier)) return identifierMapValue.get(identifier);
        missIdentifierOfLetTree.add(identifier);
        return 0;

    }

    /**
     * a method to judge if stop or continue to execute program
     * @return true : continue or false : stop
     */
    public boolean optionToStopOrContinueExecute() {
        System.out.println("Another expression (y/n)?");
        String opt = parser.getLine().trim();
        if (opt.equals("n")) return false;
        else if (!opt.equals("y")) {
            System.out.println("Please input correct option (y/n).");
            return optionToStopOrContinueExecute();
        }
        return true;
    }
}
