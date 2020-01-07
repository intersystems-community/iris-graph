package IrisGraph.JGW.ToJson;

public class ModifyDto extends TupleExprDto{
    private TupleExprDto deleteExpr;
    private TupleExprDto insertExpr;
    private TupleExprDto whereExpr;

    public ModifyDto() {
        this._class = "modify";
    }

    public ModifyDto(TupleExprDto deleteExpr, TupleExprDto insertExpr, TupleExprDto whereExpr) {
        this._class = "modify";
        this.deleteExpr = deleteExpr;
        this.insertExpr = insertExpr;
        this.whereExpr = whereExpr;
    }

    public TupleExprDto getDeleteExpr() {
        return deleteExpr;
    }

    public void setDeleteExpr(TupleExprDto deleteExpr) {
        this.deleteExpr = deleteExpr;
    }

    public TupleExprDto getInsertExpr() {
        return insertExpr;
    }

    public void setInsertExpr(TupleExprDto insertExpr) {
        this.insertExpr = insertExpr;
    }

    public TupleExprDto getWhereExpr() {
        return whereExpr;
    }

    public void setWhereExpr(TupleExprDto whereExpr) {
        this.whereExpr = whereExpr;
    }
}
