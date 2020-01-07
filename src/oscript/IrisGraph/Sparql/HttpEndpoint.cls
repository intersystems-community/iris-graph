Class IrisGraph.Sparql.HttpEndpoint Extends %CSP.REST
{

Parameter CONTENTTYPE = "application/json";

ClassMethod HandleGet() As %Status
{
		try {
			if ($data(%request.Data("query", 1))) {
				do ..select(%request.Data("query", 1))
			} else {
				return $$$ERROR(5002,"""query"" param is required")
			}
		} catch(e) {
			return e.AsStatus()
		}
		return $$$OK
}

ClassMethod HandlePost() As %Status
{
		try {
			set query = %request.Content.Read(1000000)
			if (%request.ContentType = "application/sparql-update") {
				do ..update(query)
			} elseif (%request.ContentType = "application/sparql-query") {
				do ..select(query)
			} else {
				return $$$ERROR(5002, "Wrong Content-Type, only ""application/sparql-update"" is supported")
			}
		} catch(e) {
			return e.AsStatus()
		}
		return $$$OK
}

ClassMethod select(query As %String)
{
		set result = ##class(IrisGraph.Sparql.ExecuterSQ).Execute(query)
		set vars = []
		set key = ""
		do {
			set key = $o(result.header(key))
			q:key=""
			
			if (key = "#") continue
			do vars.%Push(key)
		} while $$$YES
		set bindings = []
		for i=1:1:result.data {
			set binding = {}
			set key = ""
			do {
				set key = $o(result.header(key))
				q:key=""
				
				if (key = "#") continue
				do binding.%Set(key, {"type": "uri", "value": (result.data(i, key))})
			} while $$$YES
			do bindings.%Push(binding)
		}
		set json = {"head": {"vars": (vars)}, "results": {"bindings": (bindings)}}
		write json.%ToJSON()
}

ClassMethod update(query As %String) [ CodeMode = expression ]
{
##class(IrisGraph.Sparql.ExecuterSQ).Execute(query)
}

XData UrlMap
{
<Routes>
			<Route Url="/" Method="GET" Call="IrisGraph.Sparql.HttpEndpoint:HandleGet"/>
			<Route Url="/" Method="POST" Call="IrisGraph.Sparql.HttpEndpoint:HandlePost"/>
		</Routes>
}

}

