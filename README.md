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
    <li>Only Text Input and Output formats are supported for now</li>
</ul>

###Differences from HadoopStreaming
<ul>
    <li>The script being executed is inside the JVM</li>
    <li>You get full control over the Hadoop serialized key and value types.</li>
    <li>Context is exposed to the scripts, you can easily report progress or such</li>
    <li>Probably slower but no performance metrics yet</li>
</ul>


###Reserved Variables
**_mkey** stands for "mapper output key" and  **_mvalue** stands for **mapper output value**. <br>
mkey and mvalue instances are extracted from the script at the startup of map or reduce phase and then reused as the **_key** **_value** wrappers. 

This is just for performance improvement, you can instantiate new Writable's but reusing is encouraged.

**mkey** and **mvalue** are optional, default type is org.apache.hadoop.io.Text if omitted.




#QuickStart
##Wordcount example
###Mapper.js
	_mkey = new org.apache.hadoop.io.Text();
	_mvalue = new org.apache.hadoop.io.Text();

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
	from org.apache.hadoop.io import Text
	_rkey = Text()
	_rvalue = Text()
	
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



Copyright and License
---------------------

Copyright 2013 Codemomentum.org

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in
compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is
distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.
