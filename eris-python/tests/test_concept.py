import pytest
import jpype.imports
from bitarray import bitarray
from eris.concept import Concept

eris = '../../lib/eris-core-1.0-SNAPSHOT.jar'
blueprints = '../../lib/blueprints-core-2.6.0.jar'
guava = '../../lib/guava-31.0.1-jre.jar'

jpype.startJVM(classpath=[eris, blueprints, guava, 'classes'], convertStrings=False)
class TestConcept:
    @pytest.fixture
    def concept(self):
        extent = bitarray('1001')
        intent = bitarray('0110')
        return Concept(extent, intent)
    def test_extent(self, concept):
        assert concept.extent == bitarray('1001')
    def test_intent(self, concept):
        assert concept.intent == bitarray('011') #ignore the last 0
    def test_equal(self, concept):
        other_concept = Concept(bitarray('1001'), bitarray('0110'))
        assert concept == other_concept
    def test_not_equal(self, concept):
        other_concept = Concept(bitarray('1010'), bitarray('0110'))
        assert concept != other_concept
    def test_less_or_equal(self, concept):
        other_concept = Concept(bitarray('1101'), bitarray('0010'))
        assert concept <= other_concept
    def test_greater_or_equal(self, concept):
        other_concept = Concept(bitarray('0101'), bitarray('1110'))
        assert concept >= other_concept