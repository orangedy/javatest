println "hello world, i'm ${name} too";
name = name + "!!";
map.each({
	println it.getKey() + ":" + it.getValue()
});