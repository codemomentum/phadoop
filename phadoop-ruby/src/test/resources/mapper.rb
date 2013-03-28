require 'java'
@_mkey = org.apache.hadoop.io.Text.new()
@_mvalue = org.apache.hadoop.io.Text.new()

def map(key,value,context)
    print key,value
    $_key.set(key)
    $_value.set(value)
    context.write($_key,$_value)
end
