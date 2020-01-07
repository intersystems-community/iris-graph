package IrisGraph.JGW.ToJson;

import java.util.List;

public class DeleteDataDto extends TupleExprDto{
    private List<StatementPatternDto> statements;

    public DeleteDataDto() {
        this._class = "deleteData";
    }

    public DeleteDataDto(List<StatementPatternDto> statements) {
        this._class = "deleteData";
        this.statements = statements;
    }

    public String get_class() {
        return _class;
    }

    public List<StatementPatternDto> getStatements() {
        return statements;
    }

    public void setStatements(List<StatementPatternDto> statements) {
        this.statements = statements;
    }

}

