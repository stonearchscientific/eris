import jpype.imports
from bitarray import bitarray
from jpype import JClass
from eris.context import Context
from eris.concept import Concept

eris = '../../eris-core/target/eris-core-1.0-SNAPSHOT.jar'
blueprints = '../../eris-core/external/blueprints-core-2.6.0.jar'
guava = '../../eris-core/external/guava-31.0.1-jre.jar'

jpype.startJVM(classpath=[eris, blueprints, guava, 'classes'], convertStrings=False)

#from com.stonearchscientific.eris import Context, Matrix

relation = [
    [1, 1, 1, 1, 1],
    [1, 1, 1, 0, 1],
    [0, 0, 1, 1, 0],
    [0, 0, 0, 0, 1],
    [1, 1, 1, 1, 0]
]

extent = bitarray('1001')
intent = bitarray('0110')
concept = Concept(extent, intent)
print(f"concept.extent : {concept.extent}")
print(f"concept.intent : {concept.intent}")
print(f"concept : {concept}")
#relation = Matrix(example)
#objects = JClass('java.util.ArrayList')(["1", "2", "3", "4", "5"])
#attributes = JClass('java.util.ArrayList')(["a", "b", "c", "d", "e"])
context = Context(["1", "2", "3", "4", "5"], ["a", "b", "c", "d", "e"], relation)

#iterator = context.iterator()
#while iterator.hasNext():
#    print(f"Concept : {iterator.next()}")

for item in context.items():
    print(f"java class : {type(item)}")
    print(f"encoded extent: {item.extent()}")
    print(f"encoded intent: {item.intent()}")
    print(f"decoded concept : {context.decode(item)}")

context.draw()