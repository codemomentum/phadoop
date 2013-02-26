### (Work in progress)
<br>
<br>

##PHadoop (Polyglot Hadoop)

pHadoop is right now a playground for bringing some jvm scripting ( <a
        href="http://java.sun.com/developer/technicalArticles/J2SE/Desktop/scripting/">JSR 223</a> ) to hadoop jobs.


You can use different scripts as mappers or reducers, but you should follow the naming convention and put their extension properly. Example: mapper.js, reducer.py

The scripting engine takes the extensions into account

Inside the scripts, the variables start with underscore such as **_key**, **_value** are reserved

###phadoop-core
contains some basic plumbing to get this working

###phadoop-js
contains some primitive code to use JavaScript code as Hadoop Map Reduce functions

only supports Text at the moment


###Simple JavaScript Mapper
	function map(key, value, context){
    	_key.set(key);
    	_value.set(value);
    	context.write(_key,_value);
	}



###phadoop-python
contains some primitive code to use Python code as Hadoop Map Reduce functions

only supports Text at the moment



###Simple Python Mapper
	def map(key,value,context):
    	print key,value
    	_key.set(key)
    	_value.set(value)
    	context.write(_key,_value)


