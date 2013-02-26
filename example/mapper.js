function map(key, value, context){
    words = value.split(/[\s\.:?!]+/)
    for(var i=0; i < words.length; i++) {
        var word = words[i];
        if (word.indexOf('#') == 0)
        {
            _key.set(word);
            _value.set('1');
            context.write(_key,_value);
        }

    }
}

