_mkey = new org.apache.hadoop.io.Text();
_mvalue = new org.apache.hadoop.io.Text();

function map(key, value, context) {
    _key.set(key);
    _value.set(value);
    context.write(_key, _value);
}

