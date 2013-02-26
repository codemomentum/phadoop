### (Work in progress)
<br>
<br>

#PHadoop (Polyglot Hadoop)

pHadoop is right now a playground for bringing some jvm scripting ( <a
        href="http://docs.oracle.com/javase/6/docs/technotes/guides/scripting/index.html">JSR 223</a> ) to hadoop jobs.


You can use different scripts as mappers or reducers, but you should follow the naming convention  

<li>Extensions should be proper such as: mapper.js, reducer.py
<li>Function names should be **map** for mapper and **reduce** for reducer

The scripting engine takes the extensions into account

Inside the scripts, the variables start with underscore such as **_key**, **_value** are reserved

#QuickStart
##Wordcount example


##phadoop-core
contains some basic plumbing to get this working

##phadoop-js
contains some primitive code to use JavaScript code as Hadoop Map Reduce functions

only supports Text at the moment


###Simple JavaScript Mapper
	function map(key, value, context){
    	_key.set(key);
    	_value.set(value);
    	context.write(_key,_value);
	}

###Simple JavaScript Reducer
	function reduce(key, values, context){
    	var iterator=values.iterator();
    	while (iterator.hasNext()) {
        	_key.set(key);
        	_value.set(iterator.next());
        	context.write(_key,_value);
    	}
	}





##phadoop-python
contains some primitive code to use Python code as Hadoop Map Reduce functions

only supports Text at the moment



###Simple Python Mapper
	def map(key,value,context):
    	print key,value
    	_key.set(key)
    	_value.set(value)
    	context.write(_key,_value)

###Simple Python Reducer
	def reduce(key,values,context):
    	print key,values
    	for value in values:
        	_key.set(key)
        	_value.set(value)
        	context.write(_key,_value)


