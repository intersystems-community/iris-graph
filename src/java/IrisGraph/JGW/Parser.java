package IrisGraph.JGW;


import IrisGraph.JGW.ToJson.*;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.algebra.DeleteData;
import org.eclipse.rdf4j.query.algebra.InsertData;
import org.eclipse.rdf4j.query.algebra.TupleExpr;
import org.eclipse.rdf4j.query.algebra.UpdateExpr;
import org.eclipse.rdf4j.query.parser.*;
import org.eclipse.rdf4j.repository.sail.helpers.SPARQLUpdateDataBlockParser;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class Parser {
    
    public static String getTuple(String query) throws Exception{

        String jsonInString = null;
        TupleExprDto toJSON = null;
        JSONPrinter JSONPrinter = new JSONPrinter();
        ObjectMapper mapper = new ObjectMapper();


        ParsedOperation operation = QueryParserUtil.parseOperation(QueryLanguage.SPARQL, query, null);
        if (operation instanceof ParsedTupleQuery)
        {
            ParsedTupleQuery q = (ParsedTupleQuery)operation;
            TupleExpr te = q.getTupleExpr();
            te.visit(JSONPrinter);
        }
        else if (operation instanceof ParsedGraphQuery)
        {
            ParsedGraphQuery q = (ParsedGraphQuery)operation;
            TupleExpr te = q.getTupleExpr();
            te.visit(JSONPrinter);
        }
        else if (operation instanceof ParsedUpdate)
        {
            String qType = operation.toString();
            if (qType.contains("DeleteData") || qType.contains("InsertData"))
            {
                TupleExprDto tupleExprDto = null;
                Iterator var2 = ((ParsedUpdate) operation).getUpdateExprs().iterator();
                UpdateExpr updateExpr = (UpdateExpr)var2.next();

                RDFParser parser1 = new SPARQLUpdateDataBlockParser();
                StatementCollector handler = new StatementCollector();
                parser1.setRDFHandler(handler);

                if(updateExpr.toString().contains("DeleteData"))
                {
                    DeleteData deleteData = (DeleteData) updateExpr;
                    parser1.parse(new StringReader(deleteData.getDataBlock()), "");
                }
                else if (updateExpr.toString().contains("InsertData"))
                {
                    InsertData insertData = (InsertData) updateExpr;
                    parser1.parse(new StringReader(insertData.getDataBlock()), "");
                }

                Collection<Statement> stmts = handler.getStatements();

                tupleExprDto = parsUpdataData(stmts, updateExpr.toString());

                //Convert object to JSON string
                jsonInString = mapper.writeValueAsString(tupleExprDto);

                //Convert object to JSON string and pretty print
                //jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tupleExprDto);

                return jsonInString;
            }
        }

        toJSON = JSONPrinter.getObject();

        try {
            //Convert object to JSON string
            //jsonInString = mapper.writeValueAsString(toJSON);

            //Convert object to JSON string and pretty print
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(toJSON);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonInString;
    }

    public static TupleExprDto parsInsertData(Collection<Statement> statements){

        InsertDataDto insertDataDto = new InsertDataDto();

        List<StatementPatternDto> statementPatternDtos= new ArrayList<>();
        for (Statement elem:statements)
        {
            StatementPatternDto statementPatternDto = new StatementPatternDto(
                    new TripleDto(elem.getObject().toString(),""),
                    new TripleDto(elem.getPredicate().toString(),""),
                    new TripleDto(elem.getSubject().toString(),""),
                    new TripleDto((elem.getContext() != null) ? elem.getContext().toString() : "",""),
                    ""
            );
            statementPatternDtos.add(statementPatternDto);

        }
        insertDataDto.setStatements(statementPatternDtos);
        return insertDataDto;
    }

    public static TupleExprDto parsDeleteData(Collection<Statement> statements){
        DeleteDataDto deleteDataDto= new DeleteDataDto();

        List<StatementPatternDto> statementPatternDtos= new ArrayList<>();
        for (Statement elem:statements)
        {
            StatementPatternDto statementPatternDto = new StatementPatternDto(
                    new TripleDto(elem.getObject().toString(),""),
                    new TripleDto(elem.getPredicate().toString(),""),
                    new TripleDto(elem.getSubject().toString(),""),
                    new TripleDto((elem.getContext() != null) ? elem.getContext().toString() : "",""),
                    ""
            );
            statementPatternDtos.add(statementPatternDto);

        }
        deleteDataDto.setStatements(statementPatternDtos);
        return deleteDataDto;
    }

    public static TupleExprDto parsUpdataData(Collection<Statement> statements, String qType){

        if(qType.contains("InsertData"))
        {
            return  (InsertDataDto) parsInsertData(statements);
        }
        else if(qType.contains("DeleteData"))
        {
            return (DeleteDataDto) parsDeleteData(statements);
        }

        return null;
    }
}
