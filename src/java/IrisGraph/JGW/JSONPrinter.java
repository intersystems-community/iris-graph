package IrisGraph.JGW;

import IrisGraph.JGW.ToJson.*;
import org.eclipse.rdf4j.model.impl.SimpleLiteral;
import org.eclipse.rdf4j.query.algebra.*;
import org.eclipse.rdf4j.query.algebra.helpers.AbstractQueryModelVisitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONPrinter extends AbstractQueryModelVisitor<RuntimeException> {

    private TupleExprDto object;

    public JSONPrinter() {
    }

    public TupleExprDto getObject() {
        return object;
    }

    @Override
    protected void meetNode(QueryModelNode node) {
        try {
            if(node instanceof Distinct)
            {
                object = get((Distinct) node);
            }
            else if(node instanceof Projection)
            {
                object = get((Projection) node);
            }
            else if(node instanceof Modify)
            {
                object = get((Modify) node);
            }
            else if (node instanceof Slice)
            {
                object = get((Slice) node);
            }
            else if( node instanceof Reduced)
            {
                object = get((Reduced) node);
            }
        }catch (Exception ignored){

        }
    }

    public DistinctDto get(Distinct node) throws Exception{
        DistinctDto distinctDto = new DistinctDto();
        distinctDto.setProjectionDto(get((Projection) node.getArg()));
        return distinctDto;
    }
    public ProjectionDto get(Projection node) throws Exception{
        ProjectionDto projectionDto = new ProjectionDto();
        List<String> list = new ArrayList<>(node.getProjectionElemList().getTargetNames());
        projectionDto.setProjectionElemList(list);

        if(node.getArg() instanceof Filter){
            projectionDto.setTupleExprDto(get( (Filter) node.getArg()));
        }else if(node.getArg() instanceof Join){
            projectionDto.setTupleExprDto(get( (Join) node.getArg()));
        }else if(node.getArg() instanceof LeftJoin){
            projectionDto.setTupleExprDto(get( (LeftJoin) node.getArg()));
        }else if(node.getArg() instanceof Union){
            projectionDto.setTupleExprDto(get( (Union) node.getArg()));
        }else if (node.getArg() instanceof StatementPattern) {
            projectionDto.setTupleExprDto(get( (StatementPattern) node.getArg()));
        }else if (node.getArg() instanceof Order) {
            projectionDto.setTupleExprDto(get( (Order) node.getArg()));
        }else if (node.getArg() instanceof Extension) {
            projectionDto.setTupleExprDto(get( (Extension) node.getArg()));
        }else if (node.getArg() instanceof Group) {
            projectionDto.setTupleExprDto(get( (Group) node.getArg()));
        }
        return projectionDto;
    }
    public SliceDto get(Slice node) throws Exception{
        SliceDto sliceDto = new SliceDto();

        sliceDto.setLimit(node.getLimit());
        sliceDto.setOffset(node.getOffset());

        if (node.getArg() instanceof Projection){
            sliceDto.setArg(get((Projection) node.getArg()));
        }
        return sliceDto;
    }

    public ModifyDto get(Modify node) throws Exception{
        ModifyDto modifyDto = new ModifyDto();

        if (node.getInsertExpr() != null) {
            if (node.getInsertExpr() instanceof Join) {
                modifyDto.setInsertExpr(get((Join) node.getInsertExpr()));
            } else if (node.getInsertExpr() instanceof StatementPattern) {
                modifyDto.setInsertExpr(get((StatementPattern) node.getInsertExpr()));
            }
        }

        if (node.getWhereExpr() != null) {
            if (node.getWhereExpr() instanceof Filter) {
                modifyDto.setWhereExpr(get((Filter) node.getWhereExpr()));
            }else if(node.getWhereExpr() instanceof LeftJoin) {
                modifyDto.setWhereExpr(get((LeftJoin) node.getWhereExpr()));
            }
        }
        //if (node.getDeleteExpr() != null)
            ///todo
        return modifyDto;
    }

    public ReducedDto get(Reduced node) throws Exception{

        ReducedDto reducedDto = new ReducedDto();

        if (node.getArg() instanceof Projection)
        {
            reducedDto.setArg(get((Projection)node.getArg()));
        }
        return reducedDto;
    }

    public OrderDto get(Order node) throws Exception{
        OrderDto orderDto = new OrderDto();

        orderDto.setOrderElems(get((List<OrderElem>)node.getElements()));

        if (node.getArg() instanceof Filter){
            orderDto.setArg(get((Filter)node.getArg()));
        }else if(node.getArg() instanceof Join){
            orderDto.setArg(get((Join) node.getArg()));
        }else if (node.getArg() instanceof StatementPattern){
            orderDto.setArg(get((StatementPattern) node.getArg()));
        }
        return orderDto;
    }

    public GroupDto get(Group node) throws Exception
    {
        GroupDto groupDto = new GroupDto();

        groupDto.setGroupBindingNames(node.getGroupBindingNames());

        if (node.getArg() instanceof Join) {
            groupDto.setArg(get((Join)node.getArg()));
        }
        if (node.getGroupElements() == null) {
            groupDto.setElements(null);
        }
        return groupDto;
    }

    public ExtensionDto get(Extension node) throws Exception
    {
        ExtensionDto extensionDto = new ExtensionDto();

        List<ExtensionElemDto> extensionElemsDto = new ArrayList<>();
        for (ExtensionElem elem:node.getElements())
        {
            ExtensionElemDto extensionElemDto = new ExtensionElemDto();
            extensionElemDto.setName(elem.getName());

            if(elem.getExpr() instanceof Var){
                extensionElemDto.setExpr(get((Var) elem.getExpr()));
            }
            else if(elem.getExpr() instanceof MathExpr){
                extensionElemDto.setExpr(get((MathExpr) elem.getExpr()));
            }

            extensionElemsDto.add(extensionElemDto);
        }

        extensionDto.setElements(extensionElemsDto);

        if (node.getArg() instanceof Extension){
            extensionDto.setArg(get((Extension)node.getArg()));
        }else if(node.getArg() instanceof Join){
            extensionDto.setArg(get((Join) node.getArg()));
        }

        return extensionDto;
    }

    public MathExprDto get(MathExpr node) throws Exception
    {
        MathExprDto mathExprDto = new MathExprDto();

        mathExprDto.setOperator(node.getOperator().getSymbol());

        if(node.getLeftArg() instanceof FunctionCall)
        {
            mathExprDto.setLeft(get((FunctionCall) node.getLeftArg()));
        }
        else if(node.getLeftArg() instanceof Var)
        {
            mathExprDto.setLeft(get((Var) node.getLeftArg()));
        }
        else if(node.getLeftArg() instanceof ValueConstant)
        {
            mathExprDto.setLeft(get((ValueConstant) node.getLeftArg()));
        }else if(node.getLeftArg() instanceof MathExpr)
        {
            mathExprDto.setLeft(get((MathExpr) node.getLeftArg()));
        }

        if(node.getRightArg() instanceof FunctionCall)
        {
            mathExprDto.setRight(get((FunctionCall) node.getRightArg()));
        }
        else if(node.getRightArg() instanceof Var)
        {
            mathExprDto.setRight(get((Var) node.getRightArg()));
        }
        else if(node.getRightArg() instanceof MathExpr)
        {
            mathExprDto.setRight(get((MathExpr) node.getRightArg()));
        }
        else if(node.getRightArg() instanceof ValueConstant)
        {
            mathExprDto.setRight(get((ValueConstant) node.getRightArg()));
        }

        return mathExprDto;
    }

//    public List<ExtensionElemDto> get(List<ExtensionElem> node) throws Exception
//    {
//        List<ExtensionElemDto> extensionElemDot = new ArrayList<>();
//        for (ExtensionElem elem:node)
//        {
//            ExtensionElemDto extensionElemDto = new ExtensionElemDto();
//            extensionElemDto.setLable(elem.getLable());
//
//            if(elem.getExpr() instanceof Var){
//                extensionElemDto.setExpr(get((Var) elem.getExpr()));
//            }
//            else if(elem.getExpr() instanceof MathExpr){
//                extensionElemDto.setExpr(get((MathExpr) elem.getExpr()));
//            }
//
//            extensionElemDot.add(extensionElemDto);
//        }
//
//        return extensionElemDot;
//    }

    public List<OrderElemDto> get(List<OrderElem> node) throws Exception{
        List<OrderElemDto> orderElemDto = new ArrayList<OrderElemDto>();

        for (OrderElem elem:node) {
            OrderElemDto newElem = new OrderElemDto();

            String direction = elem.getSignature();
            direction = direction.substring(11, direction.length()-1);
            newElem.setDirection(direction);

            if (elem.getExpr() instanceof Var) {
                Var var = (Var) elem.getExpr();
                newElem.setExpretion(new TripleDto(var.getName(), (var.getValue() != null) ? var.getValue().stringValue() : null));
            }else if (elem.getExpr() instanceof FunctionCall) {
                newElem.setExpretion(get((FunctionCall)elem.getExpr()));
            }
            orderElemDto.add(newElem);
        }
        return orderElemDto;
    }

    public FilterDto get(Filter node) throws Exception {
       FilterDto filterDto = new FilterDto();

        if(node.getCondition() instanceof Compare){
            filterDto.setCondition(get((Compare) node.getCondition()));
        }else if (node.getCondition() instanceof IsLiteral){
            filterDto.setCondition(get((IsLiteral) node.getCondition()));
        }else if (node.getCondition() instanceof IsURI){
            filterDto.setCondition(get((IsURI) node.getCondition()));
        }

        if(node.getArg() instanceof Join){
            filterDto.setArg(get((Join) node.getArg()));
        }

        return filterDto;
    }
    public IsLiteralDto get(IsLiteral node){
        Var var = (Var) node.getArg();
        return new IsLiteralDto(new TripleDto(var.getName(), (var.getValue()!=null) ? var.getValue().stringValue(): null));
    }
    public IsIRIDto get(IsURI node){
        Var var = (Var) node.getArg();
        return new IsIRIDto(new TripleDto(var.getName(), (var.getValue()!=null) ? var.getValue().stringValue(): null));
    }

    public CompareDto get(Compare node) throws Exception{
       CompareDto compareDto = new CompareDto();

       compareDto.setOperator(node.getOperator().getSymbol());

        if(node.getLeftArg() instanceof FunctionCall){
            compareDto.setLeftArg(get((FunctionCall) node.getLeftArg()));
        }else if(node.getLeftArg() instanceof Var){
            Var var = (Var) node.getLeftArg();
            compareDto.setLeftArg(new TripleDto(var.getName(), (var.getValue()!=null) ? var.getValue().stringValue(): null));
        }

        if(node.getRightArg() instanceof ValueConstant){
            ValueConstantDto valueConstantDto = get((ValueConstant)node.getRightArg());
            compareDto.setRightArg(valueConstantDto);
        }else if(node.getRightArg() instanceof FunctionCall){
            compareDto.setRightArg(get((FunctionCall) node.getRightArg()));
        }

       return compareDto;
    }

    public FunctionCallDto get(FunctionCall node) throws Exception{
        FunctionCallDto functionCallDto = new FunctionCallDto();

        functionCallDto.setUri(node.getURI());

        List<TupleExprDto> vars = new ArrayList<>();
        Iterator<ValueExpr> iter = node.getArgs().iterator();
        while (iter.hasNext()) {
            Object obj = iter.next();
            if (obj instanceof ValueConstant)
            {
                vars.add(get((Var) obj));
            }
            else if(obj instanceof Var)
            {
                vars.add(get((Var) obj));
            }
            else if(obj instanceof FunctionCall)
            {
                vars.add(get((FunctionCall) obj));
            }
        }
        functionCallDto.setArgs(vars);

        return functionCallDto;
    }

    public JoinDto get(Join node) throws Exception{
        JoinDto joinDto = new JoinDto();

        if(node.getLeftArg() instanceof Join){
            joinDto.setLeft(get((Join) node.getLeftArg()));
        }else if (node.getLeftArg() instanceof StatementPattern){
            joinDto.setLeft(get((StatementPattern) node.getLeftArg()));
        }else if (node.getLeftArg() instanceof Union){
            joinDto.setLeft(get((Union) node.getLeftArg()));
        }else if(node.getLeftArg() instanceof Extension) {
            joinDto.setLeft(get((Extension) node.getLeftArg()));
        }

        if(node.getRightArg() instanceof Join){
            joinDto.setRight(get((Join) node.getRightArg()));
        }else if (node.getRightArg() instanceof StatementPattern){
            joinDto.setRight(get((StatementPattern) node.getRightArg()));
        }else if (node.getRightArg() instanceof Union){
            joinDto.setRight(get((Union) node.getRightArg()));
        }

        return joinDto;
    }
    public UnionDto get(Union node) throws Exception{
        UnionDto unionDto = new UnionDto();

        if(node.getLeftArg() instanceof Join){
            unionDto.setLeft(get((Join) node.getLeftArg()));
        }else if (node.getLeftArg() instanceof StatementPattern){
            unionDto.setLeft(get((StatementPattern) node.getLeftArg()));
        }

        if(node.getRightArg() instanceof Join){
            unionDto.setRight(get((Join) node.getRightArg()));
        }else if (node.getRightArg() instanceof StatementPattern){
            unionDto.setRight(get((StatementPattern) node.getRightArg()));
        }
        return unionDto;
    }
    public LeftJoinDto get(LeftJoin node) throws Exception{
        LeftJoinDto joinDto = new LeftJoinDto();

        if(node.getLeftArg() instanceof Join){
            joinDto.setLeft(get((Join) node.getLeftArg()));
        }else if (node.getLeftArg() instanceof StatementPattern){
            joinDto.setLeft(get((StatementPattern) node.getLeftArg()));
        }

        if(node.getRightArg() instanceof Join){
            joinDto.setRight(get((Join) node.getRightArg()));
        }else if (node.getRightArg() instanceof StatementPattern){
            joinDto.setRight(get((StatementPattern) node.getRightArg()));
        }

        return joinDto;
    }

    public StatementPatternDto get(StatementPattern node) throws Exception{
        StatementPatternDto statementPatternDto = new StatementPatternDto();

        statementPatternDto.setSubject(new TripleDto(node.getVarList().get(0).getName(), (node.getVarList().get(0).getValue()!= null) ? node.getVarList().get(0).getValue().stringValue(): null));
        statementPatternDto.setPredicate(new TripleDto(node.getVarList().get(1).getName(), (node.getVarList().get(1).getValue()!= null) ? node.getVarList().get(1).getValue().stringValue(): null));
        statementPatternDto.setObject(new TripleDto(node.getVarList().get(2).getName(), (node.getVarList().get(2).getValue()!= null) ? node.getVarList().get(2).getValue().stringValue(): null));
        if (node.getContextVar() != null)
            statementPatternDto.setContext(new TripleDto(node.getVarList().get(3).getName(), (node.getVarList().get(3).getValue()!= null) ? node.getVarList().get(3).getValue().stringValue(): null));
        statementPatternDto.setScope(node.getScope().toString());

        return statementPatternDto;
    }

    public ValueConstantDto get(ValueConstant node) throws Exception{
        ValueConstantDto valueConstantDto = new ValueConstantDto();

        if (node.getValue() instanceof SimpleLiteral){
            valueConstantDto.setValue(get((SimpleLiteral)node.getValue()));
        }

        return valueConstantDto;
    }
    public SimpleLiteralDto get(SimpleLiteral node) throws Exception{
        SimpleLiteralDto simpleLiteralDto = new SimpleLiteralDto();

        simpleLiteralDto.setLable(node.getLabel());
        simpleLiteralDto.setLanguage(node.getLanguage().toString());
        simpleLiteralDto.setDatatype(node.getDatatype().getLocalName());

        return simpleLiteralDto;
    }
    public TripleDto get(Var node) throws Exception{

        return new TripleDto(node.getName(), (node.getValue()!=null) ? node.getValue().stringValue(): null);
    }
}
