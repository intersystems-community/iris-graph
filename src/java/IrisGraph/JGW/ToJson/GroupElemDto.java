package IrisGraph.JGW.ToJson;

public class GroupElemDto extends TupleExprDto {
    private String name;
    private TupleExprDto operator;

    public GroupElemDto() {
        _class = "groupElem";
    }

    public GroupElemDto(String name, TupleExprDto expr) {
        _class = "groupElem";
        this.name = name;
        this.operator = expr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TupleExprDto getOperator() {
        return operator;
    }

    public void setOperator(TupleExprDto operator) {
        this.operator = operator;
    }
}
