package IrisGraph.JGW.ToJson;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("join")
public class JoinDto extends TupleExprDto {

    private TupleExprDto left;
    private TupleExprDto right;


    public JoinDto(TupleExprDto left, TupleExprDto right) {
        this._class = "join";
        this.left = left;
        this.right = right;
    }

    public JoinDto() {
        this._class = "join";
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
