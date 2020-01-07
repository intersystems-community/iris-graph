package IrisGraph.JGW.ToJson;

public class CompareDto extends TupleExprDto{
    private String _class;
    private String operator;
    private TupleExprDto leftArg;
    private TupleExprDto rightArg;

    public CompareDto() {
        this._class = "compare";
    }

    public CompareDto(String operator, TupleExprDto leftArg, TupleExprDto rightArg) {
        this._class = "compare";
        this.operator = operator;
        this.leftArg = leftArg;
        this.rightArg = rightArg;
    }

    public String get_class() {
        return _class;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public TupleExprDto getLeftArg() {
        return leftArg;
    }

    public void setLeftArg(TupleExprDto leftArg) {
        this.leftArg = leftArg;
    }

    public TupleExprDto getRightArg() {
        return rightArg;
    }

    public void setRightArg(TupleExprDto rightArg) {
        this.rightArg = rightArg;
    }

}
