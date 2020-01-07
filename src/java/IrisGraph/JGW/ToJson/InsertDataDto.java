package IrisGraph.JGW.ToJson;

import java.util.List;

public class InsertDataDto extends TupleExprDto{
    private List<StatementPatternDto> statements;

    public InsertDataDto() {
        this._class = "insertData";
    }

    public InsertDataDto(List<StatementPatternDto> Statement) {
        this._class = "insertData";
        this.statements = Statement;
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

