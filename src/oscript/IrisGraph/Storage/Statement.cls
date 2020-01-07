Class IrisGraph.Storage.Statement Extends %Persistent
{

Property Object As %String(MAXLEN = "");

Property Predicate As %String(MAXLEN = "");

Property Subject As %String(MAXLEN = "");

Property Context As %String(MAXLEN = "");

ClassMethod Query(subject As %String, predicate As %String, object As %String, context As %String) As %ListOfObjects
{
		s statementsList = ..SelectStatements(subject, predicate, object, context)
		q statementsList
}

ClassMethod Push(subject As %String, predicate As %String, object As %String, context As %String)
{
		s statement = ..%New()
		s statement.Subject = subject
		s statement.Predicate = predicate
		s statement.Object = object
		s statement.Context = context
		s sc = statement.%Save()
		throw:($$$ISERR(sc)) ##class(%Exception.StatusException).CreateFromStatus(sc)
}

ClassMethod SelectStatements(subject As %String, predicate As %String, object As %String, context As %String) As %ListOfObjects
{
		s sql = "SELECT "_"ID FROM IrisGraph_Storage.Statement"
		kill sqlp
		s sqlw = " WHERE 1=1"
		if (subject '= "") {
			s sqlw = sqlw_" AND Subject = ?"
			s sqlp($I(sqlp)) = subject
		}
		if (predicate '= "") { 
			s sqlw = sqlw_" AND Predicate = ?"
			s sqlp($I(sqlp)) = predicate
		}
		if (object '= "") { 
			s sqlw = sqlw_" AND Object = ?"
			s sqlp($I(sqlp)) = object
		}
		if (context '= "") { 
			s sqlw = sqlw_" AND Context = ?"
			s sqlp($I(sqlp)) = context
		}
		
		s rset=##class(%SQL.Statement).%ExecDirect(,sql_sqlw,sqlp...)
		d ..CheckRsetExecDirect(rset)
		
		s statementsList = ##class(%ListOfObjects).%New()
		while (rset.%Next())
		{
			s sc = statementsList.Insert($CLASSMETHOD("IrisGraph.Storage.Statement", "%OpenId", rset.%Get("ID")))
			throw:($$$ISERR(sc)) ##class(%Exception.StatusException).CreateFromStatus(sc)
		}
		return statementsList
}

ClassMethod DeleteStatements(subject As %String, predicate As %String, object As %String, context As %String) As %ListOfObjects
{
		s sql = "DELETE FROM IrisGraph_Storage.Statement"
		kill sqlp
		s sqlw = " WHERE 1=1"
		if (subject '= "") {
			s sqlw = sqlw_" AND Subject = ?"
			s sqlp($I(sqlp)) = subject
		}
		if (predicate '= "") { 
			s sqlw = sqlw_" AND Predicate = ?"
			s sqlp($I(sqlp)) = predicate
		}
		if (object '= "") { 
			s sqlw = sqlw_" AND Object = ?"
			s sqlp($I(sqlp)) = object
		}
		if (context '= "") { 
			s sqlw = sqlw_" AND Context = ?"
			s sqlp($I(sqlp)) = context
		}
		
		s rset=##class(%SQL.Statement).%ExecDirect(,sql_sqlw,sqlp...)
		
		return rset
}

ClassMethod CheckRsetExecDirect(rset)
{
		s sc = $$$OK
		s:'$LF($LB(0,100),rset.%SQLCODE) sc = $$$ERROR(5002,rset.%SQLCODE_" "_rset.%Message)
		throw:($$$ISERR(sc)) ##class(%Exception.StatusException).CreateFromStatus(sc)
}

Storage Default
{
<Data name="StatementDefaultData">
<Value name="1">
<Value>%%CLASSNAME</Value>
</Value>
<Value name="2">
<Value>Object</Value>
</Value>
<Value name="3">
<Value>Predicate</Value>
</Value>
<Value name="4">
<Value>Subject</Value>
</Value>
<Value name="5">
<Value>Context</Value>
</Value>
</Data>
<DataLocation>^IrisGraph.Storage.StatementD</DataLocation>
<DefaultData>StatementDefaultData</DefaultData>
<IdLocation>^IrisGraph.Storage.StatementD</IdLocation>
<IndexLocation>^IrisGraph.Storage.StatementI</IndexLocation>
<StreamLocation>^IrisGraph.Storage.StatementS</StreamLocation>
<Type>%Storage.Persistent</Type>
}

}

