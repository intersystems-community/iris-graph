package IrisGraph.JGW.ToJson;


public class MathExprDto extends TupleExprDto {
    private String operator;
    private TupleExprDto left;
    private TupleExprDto right;

    public MathExprDto() {
        this._class = "mathExpr";
    }

    public MathExprDto(String operator, TupleExprDto left, TupleExprDto right) {
        this._class = "mathExpr";
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public TupleExprDto getLeft() {
        return left;
    }

    public void setLeft(TupleExprDto left) {
        this.left = left;
    }

    public TupleExprDto getRight() {
        return right;
    }

    public void setRight(TupleExprDto right) {
        this.right = right;
    }
}
