from jpype import JClass
from abstract_context import AbstractContext
class Context(AbstractContext):
    def __init__(self, objects, attributes, relation=None):
        #self.objects = objects
        #self.attributes = attributes
        #if relation is None:
        #    self.matrix = [[False for _ in range(len(attributes))] for _ in range(len(objects))]
        #    self._populate_matrix()
        #else:
        self.java_objects = JClass('java.util.ArrayList')(objects)
        self.java_attributes = JClass('java.util.ArrayList')(attributes)
        self.java_relation = JClass('com.stonearchscientific.eris.Matrix')(relation)
        self.java_context = JClass('com.stonearchscientific.eris.Context')(self.java_objects, self.java_attributes, self.java_relation)

    def decode(self, concept):
        if not isinstance(concept, JClass('com.stonearchscientific.eris.Concept')):
            raise TypeError(f"Expected java class com.stonearchscientific.eris.Concept, got {type(concept)}")
        decoded_extent = list(self.java_context.decodeObjects(concept.extent()))
        decoded_intent = list(self.java_context.decodeAttributes(concept.intent()))
        return [decoded_extent, decoded_intent]

