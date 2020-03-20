package assigment2;

public class ExpTree {

    // the type of nodes defined by enum
    static enum TreeType {
        NUM_NODE, //des num
        ID_NODE,  // des identifier
        OPT_NODE, // des + - * / % ^
        LET_NODE, // let
        AND_NODE, // and
        DEF_NODE // define
    }

    // the type of node
    TreeType nodeType;
    // the value of node
    String val;
    // the left child node
    ExpTree leftChild;
    // the right child node
    ExpTree rightChild;

    public ExpTree(TreeType nodeType, String val, ExpTree left, ExpTree right) {
        this.nodeType = nodeType;
        this.val = val;
        this.leftChild = left;
        this.rightChild = right;
    }

    private static void validNumNode(String val) {
        try {
            int a = Integer.parseInt(val);
            if (a < 0) {
                throw new ParseException("number leaves must containing a non-negative integer");
            }
        } catch (NumberFormatException e) {
            throw new ParseException("the value of num node is not digital, val = " + val);
        }
    }

    private static void validIDNode(String val, ExpTree left, ExpTree right) {
        for (char c : val.toCharArray()) {
            if (c - 'A' < 0 || c - 'A' > 25)
                throw new ParseException("identifier leaves must containing a string comprising upper-case letters");
        }
    }

    private static void validOptNode(String val, ExpTree left, ExpTree right) {
        if (left == null || right == null) throw new ParseException("Opt node must have two children");
    }

    private static void validDefNode(String val, ExpTree left, ExpTree right) {
        for (char c : val.toCharArray()) {
            if (c - 'A' < 0 || c - 'A' > 25)
                throw new ParseException("the identifier of def node must containing a string comprising upper-case letters");
        }
    }

    public static ExpTree createNode(TreeType nodeType, String val, ExpTree left, ExpTree right) {
        switch (nodeType) {
            case NUM_NODE:
                validNumNode(val);
                break;
            case ID_NODE:
                validIDNode(val, left, right);
                break;
            case OPT_NODE:
                validOptNode(val, left, right);
                break;
            case DEF_NODE:
                validDefNode(val, left, right);
                break;
        }
        return new ExpTree(nodeType, val, left, right);
    }
}
