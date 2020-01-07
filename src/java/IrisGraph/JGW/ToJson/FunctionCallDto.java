package IrisGraph.JGW.ToJson;

import java.util.List;

public class FunctionCallDto extends TupleExprDto{
    private String uri;
    private List<TupleExprDto> args;

    public FunctionCallDto() {
        _class = "functionCall";
    }

    public FunctionCallDto(String uri, List<TupleExprDto> args) {
        _class = "functionCall";
        this.uri = uri;
        this.args = args;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<TupleExprDto> getArgs() {
        return args;
    }

    public void setArgs(List<TupleExprDto> args) {
        this.args = args;
    }
}
