Class IrisGraph.Sparql.TupleData Extends %RegisteredObject
{

Property header As %String [ MultiDimensional ];

Property data As %String [ MultiDimensional ];

Method %OnNew(ByRef header, ByRef data) As %Status
{
		merge ..header = header
		merge ..data = data
		return $$$OK
}

Method Print()
{
		set i = $o(..data(""))
		while i'="" {
			set key = ""
			do {
				set key = $o(..header(key))
				q:key=""

				if (key = "#") continue
				write key_" = "_..data(i, key),!
			} while $$$YES
			write !
			set i = $o(..data(i))
		}
}

Method Merge(other As TupleData) As TupleData
{
		#dim commonKeys
		set otherKey = $o(other.header(""))
		while (otherKey '= ""){
			if ($d(..header(otherKey))) set commonKeys(otherKey) = ""
			set otherKey = $o(other.header(otherKey))
		}
		
		if ($o(commonKeys("")) = "") return ..CartesianProduct(other)
		
		#dim index
		for i=1:1:..data {
			set indexValue = ..getIndexValue(.commonKeys)
			set index(indexValue, $i(index(indexValue))) = i
		}
		
		#dim resultHeader, resultData
		for j=1:1:other.data {
			set indexValue = other.getIndexValue(.commonKeys)
			for ii=1:1:index(indexValue){
				set i = index(indexValue, ii)
				set idx = $i(resultData)
				set key = $o(..header(""))
				while (key '= ""){
					set resultData(idx, key) = ..data(i, key)
					set key = $o(..header(key))
				}
				set key = $o(other.header(""))
				while (key '= ""){
					set resultData(idx, key) = other.data(j, key)
					set key = $o(other.header(key))
				}
			}
		}
		return ..%New(.resultHeader, .resultData)
}

Method CartesianProduct(other As TupleData) As TupleData
{
	
		#dim resultHeader, resultData
		merge resultHeader = ..header
		merge resultHeader = other.header
		merge data = ..data
		for i=1:1:data {
			for j=1:1:other.data {
				set idx = $i(resultData)
				set key = $o(..header(""))
				while (key '= ""){
					set resultData(idx, key) = ..data(i, key)
					set key = $o(..header(key))
				}
				set key = $o(other.header(""))
				while (key '= ""){
					set resultData(idx, key) = other.data(j, key)
					set key = $o(other.header(key))
				}
			}
		}
		
		return ..%New(.resultHeader, .resultData)
}

Method Filter(ByRef keys) As TupleData
{
		#dim resultHeader, resultData
		merge resultHeader = keys
		merge resultData = ..data
		for i=1:1:..data {
			set key = $o(keys(""))
			while (key '= ""){
				set resultData(i, key) = ..data(i, key)
				set key = $o(keys(key))
			}
		}
		
		return ..%New(.resultHeader, .resultData)
}

Method getIndexValue(ByRef keys) As %List
{
		set indexValue = ""
		set i = $o(..data(""))
		while i'="" 
		{
			set key = $o(keys(""))
			while (key '= "")
			{
				set indexValue = indexValue_$lb(..data(i, key))
				set key = $o(keys(key))
			}
			set i = $o(..data(i))
		}
		return indexValue
}

}

