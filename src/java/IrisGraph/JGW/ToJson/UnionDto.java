package IrisGraph.JGW.ToJson;


public class UnionDto extends TupleExprDto {
    private TupleExprDto left;
    private TupleExprDto right;

    public UnionDto(TupleExprDto left, TupleExprDto right) {
        this._class = "union";
        this.left = left;
        this.right = right;
    }

    public UnionDto() {
        this._class = "union";
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
