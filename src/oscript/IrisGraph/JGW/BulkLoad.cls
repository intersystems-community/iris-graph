Class IrisGraph.JGW.BulkLoad
{

Parameter SERVER = "127.0.0.1";

Parameter PORT = 55553;

/// Curent Namespace
Parameter NS = "GRAPHDB";

/// Jar library path
Parameter BLJARPATH = "/irissys/data/IRIS/mgr/bulk/BulkLoad.jar";

/// do ##class(IrisGraph.JGW.BulkLoad).run()
ClassMethod run(filename As %String)
{
	set gwyConn = ##class(%Net.Remote.Gateway).%New()
	set status = gwyConn.%Connect(..#SERVER, ..#PORT, ..#NS)
	quit:$$$ISERR(status) "error: "_$system.OBJ.DisplayError(status)
	
	#dim sets as %ListOfDataTypes
	set triplets = ##class(User.BulkLoad).%New(gwyConn)
	set prefixes = ""
	set body = ""
	
	set stream = ##class(%Stream.FileBinary).%New()
	do stream.LinkToFile(filename)
	set flag = 1
	while 'stream.AtEnd
	{
		set line = stream.ReadLine()
		if ($find($extract(line, 1, 9), "@prefix") = 0) && (flag = 1)
		{
			continue
		}else{
			set flag = 0
		}
		if ($find($extract(line, 1, 9), "@prefix") '= 0)
		{
			set prefixes = prefixes_line_$c(10)
		}
		else{
			do triplets.setPrefixes(prefixes)
			quit
		}
	}
	
	set i = 0
	set count = 0
	while 'stream.AtEnd
	{
		set line = stream.ReadLine()
		
		set body = body_line_$c(10)
		
		if ($find($extract(line, *-2, *), ".") '= 0) set i = i + 1
		
		if i = 1 
		{
			set sets = triplets.GetTriplets(body)
			set i = 0
			set body = ""
			set count = count + sets.Count()
			do ..SetTriplets(sets)
		}

	}
	
	if i'=0
	{
		set sets = triplets.GetTriplets(body)
		do ..SetTriplets(sets)
		set count = count + sets.Count()
	}


	// close the connection to the Java gateway
	set status = gwyConn.%Disconnect()
	if $$$ISERR(status) {
                write !,"error: "_$system.OBJ.DisplayError(status)
                quit
	}
}

/// do ##class(IrisGraph.JGW.BulkLoad).ImportClasses()
ClassMethod ImportClasses() As %Status
{
	#dim obj as %SQL.ClassQueryResultSet = ##class(%Net.Remote.Utility).GetAllClassesFunc(..#BLJARPATH, ..#SERVER, ..#PORT)

	set class = "BulkLoad"		  
					
	set classpath = ##class(%ListOfDataTypes).%New()
	
	do classpath.Insert(..#BLJARPATH)

	set list = ##class(%Net.Remote.Gateway).%ExpressImport(class, ..#PORT, ..#SERVER,, classpath)
	
	return $$$OK
}

ClassMethod SetTriplets(triplets) As %Status
{
	for i=1:1:triplets.Count()
	{
		set subject = $piece(triplets.GetAt(i)," ", 1) 
		set predicate = $piece(triplets.GetAt(i)," ", 2)
		set object = $piece(triplets.GetAt(i)," ", 3, *-1)
		do ##class(IrisGraph.Storage.Statement).Push(subject, predicate, object,"")
		//w "subject: "_subject, !, "predicate "_predicate, !, "object: "_object,!!
	}
	
	return $$$OK
}

}

