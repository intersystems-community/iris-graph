package IrisGraph.JGW.ToJson;

public class ExtensionElemDto extends TupleExprDto {
    private String name;
    private TupleExprDto expr;

    public ExtensionElemDto() {
        _class = "extensionElem";
    }

    public ExtensionElemDto(String name, TupleExprDto expr) {
        _class = "extensionElem";
        this.name = name;
        this.expr = expr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TupleExprDto getExpr() {
        return expr;
    }

    public void setExpr(TupleExprDto expr) {
        this.expr = expr;
    }
}
