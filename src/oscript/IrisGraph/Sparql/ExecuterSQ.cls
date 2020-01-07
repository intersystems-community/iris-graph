Import IrisGraph.Sparql.Statements

Class IrisGraph.Sparql.ExecuterSQ Extends %RegisteredObject
{

/// do ##class(IrisGraph.Sparql.ExecuterSQ).Execut()
ClassMethod Execute(Query) As IrisGraph.Sparql.TupleData
{
	#dim parsedQuery As %DynamicObject = ##class(IrisGraph.Sparql.Parser).GetTuple(Query)
	
	set class = parsedQuery."_class"
	
	if (class = "projection")
	{
		set data = ..Projection(parsedQuery)
	}
	elseif(class = "distinct") 
	{
		return ..Distinct(parsedQuery)	
	}
	elseif(class = "modify") 
	{
		return ..Modify(parsedQuery)
	}
	elseif(class = "slice") 
	{
		return ..Slice(parsedQuery)
	}
	elseif(class = "insertData")
	{
		return ..insertSimpleStatement(parsedQuery.statements)
	}
	elseif(class = "deleteData")
	{
		return ..deleteSimpleStatement(parsedQuery.statements)
	}
	
	return data
}

ClassMethod Distinct(distinct As %DynamicObject)
{
	if (distinct.projectionDto."_class" = "projection")
	{
		set tupledata = ..Projection(distinct.projectionDto)
	}
	
	merge data = tupledata.data
	merge header = tupledata.header
	set indexValue = ""
	set i = $order(data(""))
	while (i '= "") 
	{
		set indexValue = ""
		set key = ""
		for {
			set key = $order(header(key))
			q:key=""

			if (key = "#") continue
			set indexValue = indexValue_$lb(data(i, key))
		}
		set:$data(distinctData(indexValue))=0 distinctData(indexValue) = i
		set i = $order(data(i))
	}
	
	set newData = 0
	set i = $order(distinctData(""))
	while (i '= "") 
	{
		set newData = newData + 1
		set key = ""
		for {
			set key = $order(header(key))
			q:key=""

			if (key = "#") continue
			set newData(newData,key) = data(distinctData(i),key)
		}
		set i = $order(distinctData(i))
	}
	
	return ##class(TupleData).%New(.header, .newData)
}

ClassMethod Slice(slice As %DynamicObject)
{
	set limit = slice.limit
	set offset = slice.offset
	
	if (slice.arg = "projection")
	{
		set tupledata = ..Projection(slice.arg)
	}
	elseif(class = "distinct") 
	{
		set tupledata = ..Distinct(parsedQuery)	
	}
	
	merge data = tupledata.data
	merge header = tupledata.header
	
	set offsetCount = 0
	set limitCount = 0
	
	set i = $order(data(""))
	while (i '= "") 
	{
		if (offset '= -1)
		{
			set offsetCount = offsetCount + 1
			if (offsetCount <= offset)
			{
				set i = $order(data(i))
				continue
			}
		}
		
		set key = ""
		for {
			set key = $order(header(key))
			q:key=""

			if (key = "#") continue
			set newData = data(i, key)
		} 
		
		if (limit'=-1)
		{
			set limitCount = limitCount + 1
			quit:limitCount=limit
		}
		
		set i = $order(data(i))
	}
	return ##class(TupleData).%New(.header, .newData)
}

ClassMethod Projection(projection As %DynamicObject) As IrisGraph.Sparql.TupleData
{
	set tupleExpr = projection.tupleExprDto
	
	for i=0:1:projection.projectionElemList.%Size()-1
	{
		set keys(projection.projectionElemList.%Get(i)) = ""
	}
	
	if (tupleExpr."_class" = "order") 
	{
		return ..OrderBy(tupleExpr, .keys)
	}
	elseif (tupleExpr."_class" = "filter") 
	{
		set data = ..Filter(tupleExpr, .func)
	}
	elseif (tupleExpr."_class" = "join") 
	{
		set data = ..Join(tupleExpr)
	}
	elseif (tupleExpr."_class" = "union") 
	{
		set data = ..Union(tupleExpr)
	}
	elseif(tupleExpr."_class" = "statementPattern") 
	{
		set data = ..SelectDataBySimpleStatement(tupleExpr)
	}
	elseif(tupleExpr."_class" = "extention") 
	{
		set arg = tupleExpr.arg
		return ..Extention(tupleExpr, .keys)
	}
	
	set data = data.Filter(.keys)
	
	set:$o(func(""))'="" data = ##class(IrisGraph.Sparql.Filters).Filter(data, .func)
	
	return data
}

ClassMethod Extention(extention As %DynamicObject, ByRef keys) As IrisGraph.Sparql.TupleData
{
	set elements = extention.elements
	set arg = extention.arg
	
	if (arg."_class" = "join") 
	{
		set tupledata = ..Join(arg)
	}
	
	set tupledata = tupledata.Filter(.keys)
	
	for i=0:1:elements.%Size()-1
	{
		set element = elements.%Get(i)
		merge data = tupledata.data
		merge header = tupledata.header
		
		if element."_class" = "extensionElem"
		{
			set newCollum = element.name
			set expr = element.expr
			
			set header(newCollum)=""
			
			if (expr."_class" = "mathExpr")
			{
				set key = ""
				for {
					set key=$order(data(key)) 
					quit:key=""
					merge singlData = data(key)
					set data(key, newCollum) = ..MathExpr(expr, .singlData)
				}
			}
			set tupledata = ##class(TupleData).%New(.header, .data)
		}
	}
}

ClassMethod MathExpr(mathExpr As %DynamicObject, ByRef singlData) As IrisGraph.Sparql.TupleData
{
	set operator = mathExpr.operator
	set left = mathExpr.left
	set right = mathExpr.right
	
	if (left."_class" = "mathExpr")
	{
		set leftValue = ..MathExpr(left, .singlData)
	}
	elseif(left."_class" = "var")
	{
		set name = left.name
	    set leftValue = singlData(name)
	}
	elseif(left."_class" = "valueConstant")
	{
		set lable = left.value.lable
	    set leftValue = lable
	}
	
	if (right."_class" = "mathExpr")
	{
		set rightValue = ..MathExpr(right, .singlData)
	}
	elseif(right."_class" = "var")
	{
		set name = right.name
	    set rightValue = singlData(name)
	}
	elseif(right."_class" = "valueConstant")
	{
		set lable = right.value.lable
	    set rightValue = lable
	}
	
	return $case(operator,
	  "+":leftValue + rightValue,
	  "-":leftValue - rightValue,
	  "*":leftValue * rightValue,
	  "/":leftValue / rightValue)
}

ClassMethod OrderBy(order As %DynamicObject, ByRef keys) As IrisGraph.Sparql.TupleData
{
	set orderElems = order.orderElems
	set arg = order.arg
	
	set orderList("fields") = ""
	set orderList("directions") = ""
	
	for i=0:1:orderElems.%Size()-1
	{
		set orderElem = orderElems.%Get(i)
		set orderList("fields") = orderList("fields") _ $lb(orderElem.expretion.name)
		set orderList("directions") = orderList("directions") _ $lb($s(orderElem.direction="ASC":1,1:-1))
	}

	if (arg."_class" = "filter") 
	{
		set data = ..Filter(arg, .func)
	}
	elseif (arg."_class" = "join") 
	{
		set data = ..Join(arg)
	}
	elseif(arg."_class" = "statementPattern") 
	{
		set data = ..SelectDataBySimpleStatement(arg)
	}
	
	set data = data.Filter(.keys)
	
	set:$o(func(""))'="" data = ##class(IrisGraph.Sparql.Filters).Filter(data, .func)
	
	set data = ##class(IrisGraph.Sparql.OrderBy).Sort(data, .orderList)
	
	return data
}

ClassMethod Filter(filter As %DynamicObject, ByRef func) As IrisGraph.Sparql.TupleData
{
	set condition = filter.condition
	set arg = filter.arg
	
	if (condition."_class" = "isIRI")
	{
		set func("isIRI") = condition.isLiteral.name
	}
	elseif (condition."_class" = "isLitteral")
	{
		set func("isLitteral") = condition.isLiteral.name
	}
	elseif(condition."_class" = "compare")
	{
		set func("compare") = condition.%ToJSON()
	}

	
	if (arg."_class" = "join")
	{
		set data = ..Join(arg)
	}
	elseif(arg."_class" = "statementPattern") 
	{
		set data = ..SelectDataBySimpleStatement(arg)
	}
	
	return data
}

ClassMethod Join(join As %DynamicObject) As IrisGraph.Sparql.TupleData
{
	set left = join.left
	set right = join.right
	set mergeData = ..emptyTuple()
	
	if (left."_class" = "join")
	{
		set mergeData = mergeData.Merge(..Join(left))
	}
	elseif (left."_class" = "statementPattern") 
	{
		set mergeData = mergeData.Merge(..SelectDataBySimpleStatement(left))
		//do ##class(%ZEN.Auxiliary.jsonProvider).%ObjectToJSON(mergeData,,,"bioaeqltw")
	}
	
	return mergeData.Merge(..SelectDataBySimpleStatement(right))
}

ClassMethod Union(union As %DynamicObject) As IrisGraph.Sparql.TupleData
{
	set left = join.left
	set right = join.right
	set mergeData = ..emptyTuple()
	
	set leftObjName = ""
	set rightObjName = ""
	
	if (left."_class" = "join")
	{
		set data = ..Join(arg)
	}
	elseif (left."_class" = "statementPattern") 
	{
		set leftObjName = left.object.name
		set leftTupleData= ..SelectDataBySimpleStatement(left)
	}
	
	if (right."_class" = "statementPattern")
	{
		set rightObjName = right.object.name
		set rightTupleData = ..SelectDataBySimpleStatement(right)
	}
	
	if ((leftObjName '= "") && (rightObjName '=""))
	{
		merge leftData = leftTupleData.data
		merge rightData = rightTupleData.data
		
		set newData = leftRes.data + rightRes.data
		
		for i =1:1:leftRes.data
		{
			set newData(i,rightObjName) = leftData(i, rightObjName)
			set newData(i,leftObjName) = leftData(i + rightRes.data, leftObjName)
		}
	}

	
	
	return 1
}

ClassMethod StatementPattern(statementPattern As %DynamicObject, ByRef triplet)
{
	set subj = statementPattern.subject
	set pred = statementPattern.predicate
	set obj = statementPattern.object

	set triplet("subject") = ..normalize(subj)
	set triplet("predicate") = ..normalize(pred)
	set triplet("object") = ..normalize(obj)
}

ClassMethod SelectDataBySimpleStatement(statementPattern As %DynamicObject) As IrisGraph.Sparql.TupleData
{
	set subj = ..normalize(statementPattern.subject)
	set pred = ..normalize(statementPattern.predicate)
	set obj = ..normalize(statementPattern.object)
	
	set subjQ = $s($e(subj) = "?":"", 1:subj)
	set predQ = $s($e(pred) = "?":"", 1:pred)
	set objQ = $s($e(obj) = "?":"", 1:obj)
	
	#dim resultHeader, resultData
	
	if ($e(subj) = "?") set resultHeader($e(subj, 2, *)) = ""
	if ($e(pred) = "?") set resultHeader($e(pred, 2, *)) = ""
	if ($e(obj) = "?") set resultHeader($e(obj, 2, *)) = ""
	
	set data = ##class(IrisGraph.Storage.Statement).Query(subjQ,predQ,objQ,"")
	set resultData = data.Count()
	return:resultData=0 ..emptyTuple()
	
	for i=1:1:data.Count() {
		if ($e(subj) = "?") set resultData(i, $e(subj, 2, *)) = data.GetAt(i).Subject
		if ($e(pred) = "?") set resultData(i, $e(pred, 2, *)) = data.GetAt(i).Predicate
		if ($e(obj) = "?") set resultData(i, $e(obj, 2, *)) = data.GetAt(i).Object
	}
	
	return ##class(IrisGraph.Sparql.TupleData).%New(.resultHeader, .resultData)
}

ClassMethod emptyTuple() As TupleData
{
		#dim resultHeader, resultData
		set resultHeader("#") = ""
		set resultData = 1
		set resultData(1, "#") = ""
		
		return ##class(TupleData).%New(.resultHeader, .resultData)
}

ClassMethod normalize(data As %DynamicObject) As %String
{
	set name = data.%Get("name")
	set value = data.%Get("value")
	
	return:name="" ""
	
	if ( value = "") { return "?"_name }
	else 
	{
		if ($e(value,1,7) = "http://") {return "<"_value_">"}
		elseif ($e(value,1,8) = "https://") {return "<"_value_">"}
		return value
	}
}

ClassMethod normalizeUpdate(data As %DynamicObject) As %String
{
	set name = data.%Get("name")
	
	return:name="" ""

	if ($e(name,1,7) = "http://") {return "<"_name_">"}
	elseif ($e(name,1,8) = "https://") {return "<"_name_">"}
	
	return name
}

ClassMethod Modify(modify As %DynamicObject)
{
	set insertExpr = modify.insertExpr
	
	if (insertExpr."_class" = "join") {
		return ..Join(insertExpr)
	}elseif(insertExpr."_class" = "statementPattern") {
		return ..StatementPattern(insertExpr, .triplet)
		do ##class(IrisGraph.Storage.Statement).Push(triplet("subject"), triplet("predicate"), triplet("object"),"")
	}
}

ClassMethod insertSimpleStatement(statements As %DynamicArray)
{
	for i=0:1:statements.%Size()-1
	{
		set statement = statements.%Get(i)
		
		set subj = ..normalizeUpdate(statement.subject)
		set pred = ..normalizeUpdate(statement.predicate)
		set obj = ..normalizeUpdate(statement.object)
		set cont = ..normalizeUpdate(statement.context)
		do ##class(IrisGraph.Storage.Statement).Push(subj, pred, obj, cont)
	}
	return $$$OK
}

ClassMethod deleteSimpleStatement(statements As %DynamicArray)
{
	for i=0:1:statements.%Size()-1
	{
		set statement = statements.%Get(i)
		
		set subj = ..normalizeUpdate(statement.subject)
		set pred = ..normalizeUpdate(statement.predicate)
		set obj = ..normalizeUpdate(statement.object)
		set cont = ..normalizeUpdate(statement.context)
		do ##class(IrisGraph.Storage.Statement).DeleteStatements(subj, pred, obj, cont)
	}
	return $$$OK
}

}

