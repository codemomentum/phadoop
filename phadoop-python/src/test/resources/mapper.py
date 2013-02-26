def map(key,value,context):
    print key,value
    _key.set(key)
    _value.set(value)
    context.write(_key,_value)
