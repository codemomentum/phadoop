require 'java'
@_rkey = org.apache.hadoop.io.Text.new()
@_rvalue = org.apache.hadoop.io.Text.new()

def reduce(key,values,context)
    print key,values
    for value in values
        $_key.set(key)
        $_value.set(value)
        context.write($_key,$_value)
    end
end
