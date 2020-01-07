Class IrisGraph.Sparql.OrderBy Extends %RegisteredObject
{

ClassMethod Sort(result As IrisGraph.Sparql.TupleData, orderList As %List) As IrisGraph.Sparql.TupleData
{
	merge data = result.data
	merge header = result.header
			
	set key = ""
	for {
		set key=$order(data(key)) 
		quit:key=""
		
		set fieldsCount = $ll(orderList("fields"))
		if fieldsCount = 1 {
			set sort(data(key, $lg(orderList("fields"), 1))) = $g(sort(data(key, $lg(orderList("fields"), 1)))) _ $lb(key)
		} elseif fieldsCount=2 {
			set sort(data(key, $lg(orderList("fields"), 1)), data(key, $lg(orderList("fields"), 2))) = $g(sort(data(key, $lg(orderList("fields"), 1)), data(key, $lg(orderList("fields"), 2)))) _ $lb(key)
		} elseif fieldsCount=3 {
			set sort(data(key, $lg(orderList("fields"), 1)), data(key, $lg(orderList("fields"), 2)),data(key, $lg(orderList("fields"), 3))) = $g(sort(data(key, $lg(orderList("fields"), 1)), data(key, $lg(orderList("fields"), 2)),data(key, $lg(orderList("fields"), 3)))) _ $lb(key)
		}
	}
	
	set val = ""
	set key = ""
	for {
		set key=$order(sort(key), $lg(orderList("directions"),1), val1) 
		quit:key=""
		
		if $lg(orderList("directions"),2)'="" {
			set key2 = ""	
			for {
				set key2=$order(sort(key, key2), $lg(orderList("directions"),2), val2)
				quit:key2=""
				
				if $lg(orderList("directions"),3)'="" {
					set key3 = ""
					for {
						set key3=$order(sort(key, key2, key3), $lg(orderList("directions"),3), val3)
						quit:key3=""
						set val = val _ val3
						//write key, " ", key2," ",key3," ",$lts(val3),!
					}
				} else {		
					set val = val _ val2
					//write key, " ", key2," ",!  zw val2
				}
			}
		} else {
			set val = val _ val1
			//write key," ",$lts(val1),!
		}
	}	
	
	set sortdata = 0
	for i=1:1:$ll(val) 
	{
		set key = ""
		set sortdata = sortdata + 1
		for {
			set key = $o(header(key))
			q:key=""

			if (key = "#") continue
			
			set sortdata(i,key) = data($lg(val,i),key)
		} 
	}
	return ##class(IrisGraph.Sparql.TupleData).%New(.header, .sortdata)
}

}

