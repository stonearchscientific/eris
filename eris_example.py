import jpype.imports
from jpype import JClass
import graphviz

eris = './eris-core/target/eris-core-1.0.0.jar'
blueprints = './eris-core/target/blueprints-core-2.6.0.jar'
guava = './eris-core/target/guava-31.0.1-jre.jar'

jpype.startJVM(classpath=[eris, blueprints, guava, 'classes'], convertStrings=False)

from com.stonearchscientific.eris import Context, Matrix

example = [
    [1, 1, 1, 1, 1],
    [1, 1, 1, 0, 1],
    [0, 0, 1, 1, 0],
    [0, 0, 0, 0, 1],
    [1, 1, 1, 1, 0]
]

relation = Matrix(example)
objects = JClass('java.util.ArrayList')(["1", "2", "3", "4", "5"])
attributes = JClass('java.util.ArrayList')(["a", "b", "c", "d", "e"])
context = Context(objects, attributes, relation)

filename = 'eris-example-context.dot'
f = open(filename, 'w', encoding='utf-8')
f.write(str(context.graphviz()))
f.close()

g = graphviz.Source.from_file(filename)
g.view()