Class IrisGraph.Sparql.Parser
{

Parameter SERVER = "127.0.0.1";

Parameter PORT = 55551;

/// Jar library path
Parameter JARPATH = "/opt/app/jars/";

/// do ##class(IrisGraph.Sparql.Parser).GetTuple()
ClassMethod GetTuple(GDBQuery As %String = "") As %DynamicObject
{
	set gwyConn = ##class(%Net.Remote.Gateway).%New()
	set status = gwyConn.%Connect(..#SERVER, ..#PORT, $namespace)
	quit:$$$ISERR(status) "error: "_$system.OBJ.DisplayError(status)

	set tuple = ##class(IrisGraph.JGW.Parser).getTuple(gwyConn, GDBQuery)
	
	set jsonTuple = {}.%FromJSON(tuple)
	return jsonTuple
}

/// do ##class(IrisGraph.Sparql.Parser).ImportClasses()
ClassMethod ImportClasses() As %Status
{
	set class = "IrisGraph.JGW.Parser"		  
					
	set classpath = ##class(%ListOfDataTypes).%New()
	
	do classpath.Insert(..#JARPATH _ "eclipse-rdf4j-2.4.2-onejar.jar")
	do classpath.Insert(..#JARPATH _ "parser.jar")
	
	set list = ##class(%Net.Remote.Gateway).%ExpressImport(class, ..#PORT, ..#SERVER,, classpath)
	
	return $$$OK
}

}

