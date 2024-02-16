from abc import ABC, abstractmethod
from jpype import JClass

class AbstractContext(ABC):
    @abstractmethod
    def __init__(self):
        self.java_context = JClass('org.nmdp.ngs.fca.AbstractContext')
    def dual(self):
        return self.java_context.dual()
    def size(self):
        return self.java_context.size()
    def order(self):
        return self.java_context.order()
    def empty(self):
        return self.java_context.isEmpty()
    def add(self, relatable):
        return self.java_context.add(relatable)
    def add_all(self, collection):
        return self.java_context.addAll(collection)
    def clear(self):
        return self.java_context.clear()

    class AbstractIterator(ABC):
        def __init__(self, java_context_iterator):  # add start parameter when iterator(start) is exposed through java_context
            self.java_context_iterator = java_context_iterator
        def __iter__(self):
            return self
        def __next__(self):
            if not self.java_context_iterator.hasNext():
                raise StopIteration
            return self.java_context_iterator.next()

    def items(self):
        return self.AbstractIterator(self.java_context.iterator())
    def __eq__(self, other):
        return self.java_context.equals(other.java_context)
    def __ne__(self, other):
        return not self.__eq__(other)
    def __str__(self):
        return str(self.java_context.toString())
    def __repr__(self):
        return self.__str__()
