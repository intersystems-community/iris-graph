package IrisGraph.JGW.ToJson;

public class ValueConstantDto extends TupleExprDto{
    private TupleExprDto value;

    public ValueConstantDto(TupleExprDto value) {
        this._class = "valueConstant";
        this.value = value;
    }

    public ValueConstantDto() {
        this._class = "valueConstant";
    }

    public String get_class() {
        return _class;
    }

    public TupleExprDto getValue() {
        return value;
    }

    public void setValue(TupleExprDto value) {
        this.value = value;
    }
}
