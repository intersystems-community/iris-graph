package IrisGraph.JGW.ToJson;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("join")
public class LeftJoinDto extends TupleExprDto {

    private TupleExprDto left;
    private TupleExprDto right;


    public LeftJoinDto(TupleExprDto left, TupleExprDto right) {
        this._class = "leftjoin";
        this.left = left;
        this.right = right;
    }

    public LeftJoinDto() {
        this._class = "leftjoin";
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
