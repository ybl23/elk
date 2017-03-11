/**
 * 
 */
package org.eclipse.elk.core.alg;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class AlgorithmAssemblerTest {

    /**
     * Test method for {@link org.eclipse.elk.core.alg.AlgorithmAssembler#enableCaching()}.
     */
    @Test
    public void testEnableCaching() {
        AlgorithmAssembler<TestPhases, StringBuffer> assembler = AlgorithmAssembler.create(TestPhases.class);
        // Caching is enabled by default
        assembler.setPhase(TestPhases.PHASE_1, TestPhases.PHASE_1);
        assembler.setPhase(TestPhases.PHASE_2, TestPhases.PHASE_2);
        
        List<ILayoutProcessor<StringBuffer>> algorithm = assembler.build(null);
        assertEquals(2, algorithm.stream().filter(o -> o instanceof ILayoutPhase).count());
        
        List<ILayoutProcessor<StringBuffer>> algorithm2 = assembler.build(null);
        assertEquals(2, algorithm2.stream().filter(o -> o instanceof ILayoutPhase).count());
        
        for (int i = 0; i < algorithm.size(); i++) {
            assertSame(algorithm.get(i), algorithm2.get(i));
        }
    }

    /**
     * Test method for {@link org.eclipse.elk.core.alg.AlgorithmAssembler#disableCaching()}.
     */
    @Test
    public void testDisableCaching() {
        AlgorithmAssembler<TestPhases, StringBuffer> assembler = AlgorithmAssembler.create(TestPhases.class);
        assembler.disableCaching();
        assembler.setPhase(TestPhases.PHASE_1, TestPhases.PHASE_1);
        assembler.setPhase(TestPhases.PHASE_2, TestPhases.PHASE_2);
        
        List<ILayoutProcessor<StringBuffer>> algorithm = assembler.build(null);
        assertEquals(2, algorithm.stream().filter(o -> o instanceof ILayoutPhase).count());
        
        List<ILayoutProcessor<StringBuffer>> algorithm2 = assembler.build(null);
        assertEquals(2, algorithm2.stream().filter(o -> o instanceof ILayoutPhase).count());
        
        for (int i = 0; i < algorithm.size(); i++) {
            assertNotSame(algorithm.get(i), algorithm2.get(i));
        }
    }

    /**
     * Test method for {@link org.eclipse.elk.core.alg.AlgorithmAssembler#failOnMissingPhase()}.
     */
    @Test(expected = IllegalStateException.class)
    public void testFailOnMissingPhase() {
        AlgorithmAssembler<TestPhases, StringBuffer> assembler = AlgorithmAssembler.create(TestPhases.class);
        // Failing on missing phase is the default
        assembler.setPhase(TestPhases.PHASE_1, TestPhases.PHASE_1);
        assembler.build(null);
    }

    /**
     * Test method for {@link org.eclipse.elk.core.alg.AlgorithmAssembler#dontFailOnMissingPhase()}.
     */
    @Test
    public void testDontFailOnMissingPhase() {
        AlgorithmAssembler<TestPhases, StringBuffer> assembler = AlgorithmAssembler.create(TestPhases.class);
        assembler.dontFailOnMissingPhase();
        assembler.setPhase(TestPhases.PHASE_1, TestPhases.PHASE_1);
        assertTrue(assembler.build(null).size() > 0);
    }

    /**
     * Test method for {@link org.eclipse.elk.core.alg.AlgorithmAssembler#clearCache()}.
     */
    @Test
    public void testClearCache() {
        AlgorithmAssembler<TestPhases, StringBuffer> assembler = AlgorithmAssembler.create(TestPhases.class);
        assembler.enableCaching();
        assembler.setPhase(TestPhases.PHASE_1, TestPhases.PHASE_1);
        assembler.setPhase(TestPhases.PHASE_2, TestPhases.PHASE_2);
        
        List<ILayoutProcessor<StringBuffer>> algorithm = assembler.build(null);
        assertEquals(2, algorithm.stream().filter(o -> o instanceof ILayoutPhase).count());
        
        assembler.clearCache();
        
        List<ILayoutProcessor<StringBuffer>> algorithm2 = assembler.build(null);
        assertEquals(2, algorithm2.stream().filter(o -> o instanceof ILayoutPhase).count());
        
        for (int i = 0; i < algorithm.size(); i++) {
            assertNotSame(algorithm.get(i), algorithm2.get(i));
        }
    }

    /**
     * Test method for {@link org.eclipse.elk.core.alg.AlgorithmAssembler#reset()}.
     */
    @Test(expected = IllegalStateException.class)
    public void testResetWithFailOnMissingPhase() {
        AlgorithmAssembler<TestPhases, StringBuffer> assembler = AlgorithmAssembler.create(TestPhases.class);
        assembler.failOnMissingPhase();
        assembler.setPhase(TestPhases.PHASE_1, TestPhases.PHASE_1);
        assembler.setPhase(TestPhases.PHASE_2, TestPhases.PHASE_2);
        
        assembler.reset();
        
        assembler.build(null);
    }

    /**
     * Test method for {@link org.eclipse.elk.core.alg.AlgorithmAssembler#reset()}.
     */
    @Test
    public void testResetWithoutFailOnMissingPhase() {
        AlgorithmAssembler<TestPhases, StringBuffer> assembler = AlgorithmAssembler.create(TestPhases.class);
        assembler.dontFailOnMissingPhase();
        assembler.setPhase(TestPhases.PHASE_1, TestPhases.PHASE_1);
        assembler.setPhase(TestPhases.PHASE_2, TestPhases.PHASE_2);
        
        assembler.reset();
        
        assertEquals(0, assembler.build(null).size());
    }

    /**
     * Test method for {@link org.eclipse.elk.core.alg.AlgorithmAssembler#addProcessorConfiguration(org.eclipse.elk.core.alg.LayoutProcessorConfiguration)}.
     */
    @Test
    public void testAddProcessorConfiguration() {
        AlgorithmAssembler<TestPhases, StringBuffer> assembler = AlgorithmAssembler.create(TestPhases.class);
        assembler.addProcessorConfiguration(LayoutProcessorConfiguration.<TestPhases, StringBuffer>create()
                .addBefore(TestPhases.PHASE_1, TestProcessors.PROCESSOR_3));
        assembler.setPhase(TestPhases.PHASE_1, TestPhases.PHASE_1);
        assembler.setPhase(TestPhases.PHASE_2, TestPhases.PHASE_2);
        
        List<ILayoutProcessor<StringBuffer>> algorithm = assembler.build(null);

        String[] expectedStrings = {"PROCESSOR_1", "PROCESSOR_3", "PHASE_1", "PROCESSOR_2", "PHASE_2" };
        for (int i = 0; i < algorithm.size(); i++) {
            StringBuffer buffer = new StringBuffer();
            algorithm.get(i).process(buffer, null);
            assertEquals(buffer.toString(), expectedStrings[i]);
        }
    }

}
