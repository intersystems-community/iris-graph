package IrisGraph.JGW.ToJson;

import java.util.List;

public class OrderDto extends TupleExprDto {
    private List<OrderElemDto> orderElems;
    private TupleExprDto arg;

    public OrderDto() {
        _class = "order";
    }

    public OrderDto(List<OrderElemDto> orderElems, TupleExprDto arg) {
        _class = "order";
        this.orderElems = orderElems;
        this.arg = arg;
    }

    public List<OrderElemDto> getOrderElems() {
        return orderElems;
    }

    public void setOrderElems(List<OrderElemDto> orderElems) {
        this.orderElems = orderElems;
    }

    public TupleExprDto getArg() {
        return arg;
    }

    public void setArg(TupleExprDto arg) {
        this.arg = arg;
    }
}
