package IrisGraph.JGW.ToJson;

public class OrderElemDto extends TupleExprDto {
    private String direction;
    private TupleExprDto expretion;

    public OrderElemDto() {
        _class = "orderElem";
    }

    public OrderElemDto(String direction, TupleExprDto expretion) {
        _class = "orderElem";
        this.direction = direction;
        this.expretion = expretion;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public TupleExprDto getExpretion() {
        return expretion;
    }

    public void setExpretion(TupleExprDto expretion) {
        this.expretion = expretion;
    }
}
