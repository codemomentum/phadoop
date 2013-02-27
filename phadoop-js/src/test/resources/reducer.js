_rkey = new org.apache.hadoop.io.Text();
_rvalue = new org.apache.hadoop.io.Text();

function reduce(key, values, context){
    var iterator=values.iterator();
    while (iterator.hasNext()) {
        _key.set(key);
        _value.set(iterator.next());
        context.write(_key,_value);
    }
}

