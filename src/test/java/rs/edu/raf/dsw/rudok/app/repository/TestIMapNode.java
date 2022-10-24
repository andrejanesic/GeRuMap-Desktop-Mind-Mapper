package rs.edu.raf.dsw.rudok.app.repository;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class TestIMapNode {

    public static class IMapNodeTestImplement extends IMapNode {

    }

    public static class IMapNodeCompositeTestImplement extends IMapNodeComposite {

    }

    private static Set<IMapNodeComposite> generateSamples() {
        Set<IMapNodeComposite> samples = new HashSet<>();
        for (int i = new Random().nextInt(5); i > 0; i--) {
            samples.add(new IMapNodeCompositeTestImplement());
        }
        return samples;
    }

    @Test
    public void testSetParents() {
        IMapNode child = new IMapNodeTestImplement();
        Set<IMapNodeComposite> samples = generateSamples();

        child.setParents(samples);
        Assert.assertEquals(child.getParents(), samples);
    }

    @Test
    public void testAddParent() {
        IMapNode child = new IMapNodeTestImplement();
        Set<IMapNodeComposite> samples = generateSamples();

        int i = 0;
        Iterator<IMapNodeComposite> iterator = samples.iterator();
        while (iterator.hasNext()) {
            IMapNodeComposite parent = iterator.next();
            Assert.assertEquals(child.getParents().size(), i);
            Assert.assertFalse(child.getParents().contains(parent));
            child.addParent(parent);
            Assert.assertEquals(child.getParents().size(), ++i);
            Assert.assertTrue(child.getParents().contains(parent));
        }
    }

    @Test
    public void testRemoveParent() {
        IMapNode child = new IMapNodeTestImplement();
        Set<IMapNodeComposite> samples = generateSamples();
        child.setParents(samples);

        int i = samples.size();
        Iterator<IMapNodeComposite> iterator = samples.iterator();
        while (iterator.hasNext()) {
            IMapNodeComposite parent = iterator.next();
            Assert.assertEquals(child.getParents().size(), i);
            Assert.assertTrue(child.getParents().contains(parent));
            child.removeParent(parent);
            Assert.assertEquals(child.getParents().size(), --i);
            Assert.assertFalse(child.getParents().contains(parent));
        }
    }
}
