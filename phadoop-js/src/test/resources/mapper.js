function map(key, value, context){
    _key.set(key);
    _value.set(value);
    context.write(_key,_value);
}

