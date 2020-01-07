# iris-graph
IRIS as graphdb via implementation of rdf4j API implementation

## Instalation 

1. Download and import objectscript classes.
2. Create new java gateway. [Manual](https://irisdocs.intersystems.com/iris20181/csp/docbook/DocBook.UI.Page.cls?KEY=AFL_javagateway_explore_start).
3. In the IrisGraph.Sparql.Parser class set the `server` and `port` parameters to the same value as when creating the java gateway.
4. Download [jar](https://github.com/intersystems/iris-graph/releases) file from the latest release and specify the path to this jar file in the IrisGraph.Sparql.Parser class.
5. Run this command to connect to the jar library:
   ```do ##class(IrisGraph.Sparql.Parser).ImportClasses()```
   
## Using

- To execute rest requests, use the [IrisGraph.Sparql.HttpEndpoint](https://github.com/intersystems/iris-graph/blob/master/src/oscript/IrisGraph/Sparql/HttpEndpoint.cls) class.
- Or a straight line using the ```do ##class(IrisGraph.Sparql.ExecuterSQ).Execut(Query)```.
