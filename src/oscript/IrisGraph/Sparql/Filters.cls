Class IrisGraph.Sparql.Filters Extends %RegisteredObject
{

ClassMethod Filter(result As IrisGraph.Sparql.TupleData, func As %String) As IrisGraph.Sparql.TupleData
{

	set key = $o(func(""))
	
	while key'="" {
		
		set:key="isIRI" fresult = ..IsIRIfilter(result,func(key))
		set:key="isLiteral" fresult = ..IsIRIfilter(result,func(key))
		set:key="compare" fresult = ..Compare(result, func(key))
		set key = $o(func(key))
	}
	return fresult
}

ClassMethod Compare(result As IrisGraph.Sparql.TupleData, compare) As IrisGraph.Sparql.TupleData
{
		
	merge data = result.data
	merge header = result.header
	
	set compare = {}.%FromJSON(compare)
	
	set leftArgName = ""
	set leftArgValue = ""
	set rightArgName = ""
	set rightArgValue = ""
	
	if (compare.leftArg."_class" = "var") 
	{
		set leftArgName = compare.leftArg.name
	}
	elseif(compare.leftArg."_class" = "valueConstant")
	{
		set leftArgValue = compare.leftArg.value.lable
	}
	
	if (compare.rightArg."_class" = "var") 
	{
		set rightArgName = compare.rightArg.name
	}
	elseif(compare.rightArg."_class" = "valueConstant")
	{
		set rightArgValue = compare.rightArg.value.lable
	}

	set operator = compare.operator

	set i = $o(data(""))
	while i'="" 
	{
		if ((leftArgValue '="") && (rightArgName '=""))
		{
			k:'..CompareLeftAndRight(leftArgValue, data(i, rightArgName), operator) data(i)
		}
		elseif ((leftArgName '="") && (rightArgValue '=""))
		{
			k:'..CompareLeftAndRight(data(i, leftArgName), rightArgValue, operator) data(i)
		}
		elseif ((leftArgName '="") && (rightArgName '=""))
		{
			k:'..CompareLeftAndRight(data(i, leftArgName), data(i, rightArgName), operator) data(i)
		}
		
		set i = $o(data(i))
	}

	return ##class(IrisGraph.Sparql.TupleData).%New(.header,.data)
}

ClassMethod IsIRIfilter(result As IrisGraph.Sparql.TupleData, property As %String) As IrisGraph.Sparql.TupleData
{
	merge data = result.data
	merge header = result.header

	set i = $o(data(""))
	while i'="" 
	{
		if '..IsIRI(data(i, property))
		{
			set data = data - 1
			set key = ""
			
			do {
				set key = $o(header(key))
				q:key=""

				if (key = "#") continue
				k data(i)
			} while $$$YES
		}
		set i = $o(data(i))
	}
	
	return ##class(IrisGraph.Sparql.TupleData).%New(.header,.data)
}

ClassMethod IsLiteralfilter(result As IrisGraph.Sparql.TupleData, property As %String) As IrisGraph.Sparql.TupleData
{
	merge data = result.data
	merge header = result.header

	set i = $o(data(""))
	while i'="" 
	{
		if '..IsLiteralfilter(data(i, property))
		{
			set data = data - 1
			set key = ""
			
			do {
				set key = $o(header(key))
				q:key=""

				if (key = "#") continue
				k data(i)
			} while $$$YES
		}
		set i = $o(data(i))
	}
	
	return ##class(IrisGraph.Sparql.TupleData).%New(.header,.data)
}

ClassMethod CompareLeftAndRight(left, right, operator) As %Boolean [ CodeMode = expression ]
{
$case(operator,
				 "=": $select(left = right: 1, 1:0),
				 "!=": $select(left '= right: 1, 1:0),
				 "<": $select(left < right: 1, 1:0),
				 ">": $select(left > right: 1, 1:0),
				 "<=": $select(left <= right: 1, 1:0),
				 ">=": $select(left >= right: 1, 1:0),
				 :$$$ERROR(5000, "Unexpected operator: "_operator))
}

ClassMethod IsIRI(data) As %Boolean [ CodeMode = expression ]
{
$select(($e(data)="<") && ($e(data,*)=">"):$$$YES,1:$$$NO)
}

ClassMethod IsLiteral(data) As %Boolean [ CodeMode = expression ]
{
$select(($e(data)'="<") && ($e(data,*)'=">"):$$$YES,1:$$$NO)
}

}

