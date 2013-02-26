### (Work in progress)
<br>
<br>

#PHadoop (Polyglot Hadoop)

pHadoop is right now a playground for bringing some jvm scripting ( <a
        href="http://docs.oracle.com/javase/6/docs/technotes/guides/scripting/index.html">JSR 223</a> ) to hadoop jobs.


You can use different kinds of scripts as mappers or reducers, but you should follow the naming convention  

<ul>
    <li>Extensions should be proper such as: mapper.js, reducer.py (The scripting engine takes the extensions into account)</li>
    <li>Function names should be **map** for mapper and **reduce** for reducer</li>
    <li>Inside the scripts, the variables start with underscore such as **_key**, **_value** are reserved </li>
    <li>Only Text is supported for now</li>
</ul>


#QuickStart
##Wordcount example
###Mapper.js
	function map(key, value, context){
    	words = value.toString().split(' ');
    	for(var i=0; i < words.length; i++) {
        	var word = words[i];
        	_key.set(word);
        	_value.set('1');
        	context.write(_key,_value);
    	}
	}



###Reducer.py
	def reduce(key,values,context):
    	count = 0
    	for value in values:
        	count+=1
    	_key.set(key)
    	_value.set(str(count))
    	context.write(_key,_value)

###How to execute
After building with mvn clean install:

	hadoop  jar phadoop-app/target/phadoop-uber-cli.jar phadoop ./example/mapper.js ./example/reducer.py ./example/input/ ./example/output

and the result should be something similar:
	
	hadoop	2
	hello	1
	java	1
	javascript	1
	js	1
	python	3
	…..
	…..

##phadoop-core
contains some basic plumbing to get this working

##phadoop-js
contains some primitive code to use JavaScript code as Hadoop Map Reduce functions


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


