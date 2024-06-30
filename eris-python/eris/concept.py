from bitarray import bitarray, util
from jpype import JClass

class Concept:
    def bitarray_to_bitset(self, bit_array):
        bitset = JClass('java.util.BitSet')(len(bit_array))
        for i in range(len(bit_array)):
            if bit_array[i]:
                bitset.set(i)
        return bitset
    def bitset_to_bitarray(self, bitset):
        bit_array = bitarray(bitset.length())
        for i in range(bitset.length()):
            if bitset.get(i):
                bit_array[i] = True
        return bit_array
    def __init__(self, extent, intent):
        if not isinstance(extent, bitarray):
            raise TypeError(f"Expected bitarray, got {type(extent)}")
        if not isinstance(intent, bitarray):
            raise TypeError(f"Expected bitarray, got {type(intent)}")
        java_extent = self.bitarray_to_bitset(extent)
        java_intent = self.bitarray_to_bitset(intent)
        self.java_concept = JClass('com.stonearchscientific.eris.Concept')(java_extent, java_intent)
    @property
    def extent(self):
        return self.bitset_to_bitarray(self.java_concept.extent())
    @property
    def intent(self):
        return self.bitset_to_bitarray(self.java_concept.intent())
    def __eq__(self, other):
        return self.java_concept.equals(other.java_concept)
    def __ne__(self, other):
        return not self.__eq__(other)
    def __le__(self, other):
        return self.java_concept.lessOrEqual(other.java_concept)
    def __ge__(self, other):
        return self.java_concept.greaterOrEqual(other.java_concept)
    def __str__(self):
        return str(type(self.java_concept))
    def __repr__(self):
        return self.__str__()
