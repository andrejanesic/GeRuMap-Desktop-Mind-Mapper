package rs.edu.raf.dsw.rudok.app.repository;

import org.junit.Assert;
import org.junit.Test;
import rs.edu.raf.dsw.rudok.app.Helper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class TestIMapNodeComposite {

    private static Set<IMapNode> generateSamples() {
        Set<IMapNode> samples = new HashSet<>();
        for (int i = new Random().nextInt(5); i > 0; i--) {
            samples.add(new TestIMapNode.IMapNodeTestImplement(Helper.randString()));
        }
        return samples;
    }

    @Test
    public void testSetChildren() {
        IMapNodeComposite parent = new TestIMapNode.IMapNodeCompositeTestImplement(Helper.randString());
        Set<IMapNode> samples = generateSamples();

        parent.setChildren(samples);
        Assert.assertEquals(parent.getChildren(), samples);
    }

    @Test
    public void testAddChild() {
        IMapNodeComposite parent = new TestIMapNode.IMapNodeCompositeTestImplement(Helper.randString());
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
        IMapNodeComposite parent = new TestIMapNode.IMapNodeCompositeTestImplement(Helper.randString());
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
