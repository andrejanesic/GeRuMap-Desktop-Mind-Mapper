package rs.edu.raf.dsw.rudok.app.repository;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class TestIMapNodeComposite {

    public static class IMapNodeTestImplement extends IMapNode {

    }

    public static class IMapNodeCompositeTestImplement extends IMapNode {

    }

    private static Set<IMapNode> generateSamples() {
        Set<IMapNode> samples = new HashSet<>();
        for (int i = new Random().nextInt(5); i > 0; i--) {
            samples.add(new TestIMapNode.IMapNodeTestImplement());
        }
        return samples;
    }

    @Test
    public void testSetChildren() {
        IMapNodeComposite parent = new TestIMapNode.IMapNodeCompositeTestImplement();
        Set<IMapNode> samples = generateSamples();

        parent.setChildren(samples);
        Assert.assertEquals(parent.getChildren(), samples);
    }

    @Test
    public void testAddChild() {
        IMapNodeComposite parent = new TestIMapNode.IMapNodeCompositeTestImplement();
        Set<IMapNode> samples = generateSamples();

        int i = 0;
        Iterator<IMapNode> iterator = samples.iterator();
        while (iterator.hasNext()) {
            IMapNode child = iterator.next();
            Assert.assertEquals(parent.getChildren().size(), i);
            Assert.assertFalse(parent.getChildren().contains(child));
            parent.addChild(child);
            Assert.assertEquals(parent.getChildren().size(), ++i);
            Assert.assertTrue(parent.getChildren().contains(child));
        }
    }

    @Test
    public void testRemoveParent() {
        IMapNodeComposite parent = new TestIMapNode.IMapNodeCompositeTestImplement();
        Set<IMapNode> samples = generateSamples();
        parent.setChildren(samples);

        int i = samples.size();
        Iterator<IMapNode> iterator = samples.iterator();
        while (iterator.hasNext()) {
            IMapNode child = iterator.next();
            Assert.assertEquals(parent.getChildren().size(), i);
            Assert.assertTrue(parent.getChildren().contains(child));
            parent.removeChild(child);
            Assert.assertEquals(parent.getChildren().size(), --i);
            Assert.assertFalse(parent.getChildren().contains(child));
        }
    }
}
