package IrisGraph.JGW.ToJson;

public class SliceDto extends TupleExprDto {
    private Long limit;
    private Long offset;
    private TupleExprDto arg;

    public SliceDto() {
        _class = "slice";
    }

    public SliceDto(Long limit, Long offset, TupleExprDto arg) {
        _class = "slice";
        this.limit = limit;
        this.offset = offset;
        this.arg = arg;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public TupleExprDto getArg() {
        return arg;
    }

    public void setArg(TupleExprDto arg) {
        this.arg = arg;
    }
}
