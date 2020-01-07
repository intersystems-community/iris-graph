package IrisGraph.JGW.ToJson;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("statementPattern")
public class StatementPatternDto extends TupleExprDto{

    private String _class;
    private TripleDto subject;
    private TripleDto predicate;
    private TripleDto object;
    private TripleDto context;
    private String scope;

    public StatementPatternDto() {
        this._class = "statementPattern";
    }

    public StatementPatternDto(TripleDto object, TripleDto predicate, TripleDto subject, TripleDto context, String scope) {
        this._class = "statementPattern";
        this.object = object;
        this.predicate = predicate;
        this.subject = subject;
        this.context = context;
        this.scope = scope;
    }

    public String get_class() {
        return _class;
    }

    public TripleDto getObject() {
        return object;
    }

    public void setObject(TripleDto object) {
        this.object = object;
    }

    public TripleDto getPredicate() {
        return predicate;
    }

    public void setPredicate(TripleDto predicate) {
        this.predicate = predicate;
    }

    public TripleDto getSubject() {
        return subject;
    }

    public void setSubject(TripleDto subject) {
        this.subject = subject;
    }

    public TripleDto getContext() {
        return context;
    }

    public void setContext(TripleDto context) {
        this.context = context;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
