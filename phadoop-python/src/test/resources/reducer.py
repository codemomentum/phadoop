from org.apache.hadoop.io import Text

_rkey = Text()
_rvalue = Text()

def reduce(key,values,context):
    print key,values
    for value in values:
        _key.set(key)
        _value.set(value)
        context.write(_key,_value)
