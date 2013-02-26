def reduce(key,values,context):
    count = 0
    for value in values:
        count+=1
    _key.set(key)
    _value.set(str(count))
    context.write(_key,_value)