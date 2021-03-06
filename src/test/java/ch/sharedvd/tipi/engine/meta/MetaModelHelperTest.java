package ch.sharedvd.tipi.engine.meta;

import ch.sharedvd.tipi.engine.action.parentChild.TstParentProcess;
import ch.sharedvd.tipi.engine.command.annotated.AnnotatedSubProcess;
import ch.sharedvd.tipi.engine.command.annotated.AnnotatedTopProcess;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MetaModelHelperTest {

    @Test
    public void testGetTopProcessMetaFromStaticField() {
        TopProcessMetaModel meta = MetaModelHelper.getTopProcessMeta(TstParentProcess.class.getName());
        assertNotNull(meta);
        Assert.assertEquals("Bla bla", meta.getDescription());
        Assert.assertEquals(6, meta.getPriority());
        Assert.assertEquals(-1, meta.getNbMaxTopConcurrent());
        Assert.assertEquals(20, meta.getNbMaxConcurrent());
    }

    @Test
    public void testGetTopProcessMetaFromAnnotationWithClassName() {
        TopProcessMetaModel meta = MetaModelHelper.getTopProcessMeta(AnnotatedTopProcess.class.getName());
        assertNotNull(meta);
        Assert.assertEquals("Test TopProcess", meta.getDescription());
        Assert.assertEquals(100, meta.getPriority());
        Assert.assertEquals(-1, meta.getNbMaxTopConcurrent());
        Assert.assertEquals(-1, meta.getNbMaxConcurrent());
    }

    @Test
    public void testGetTopProcessMetaFromAnnotationWithClass() {
        TopProcessMetaModel meta = MetaModelHelper.createTopProcessMetaModel(AnnotatedTopProcess.class);
        assertNotNull(meta);
        Assert.assertEquals("Test TopProcess", meta.getDescription());
        Assert.assertEquals(100, meta.getPriority());
        Assert.assertEquals(-1, meta.getNbMaxTopConcurrent());
        Assert.assertEquals(-1, meta.getNbMaxConcurrent());
    }

    @Test
    public void testGetSubProcessMetaFromAnnotationWithClass() {
        SubProcessMetaModel meta = MetaModelHelper.createSubProcessMetaModel(AnnotatedSubProcess.class);
        assertNotNull(meta);
        Assert.assertEquals("Test SubProcess", meta.getDescription());
    }

    @Test
    public void testGetActivityMetaModel() {

        // With static field
        ActivityMetaModel meta = MetaModelHelper.createActivityMetaModel(TstParentProcess.class);
        assertNotNull(meta);
        Assert.assertTrue(TopProcessMetaModel.class.isAssignableFrom(meta.getClass()));

        // With annotation
        meta = MetaModelHelper.createActivityMetaModel(AnnotatedTopProcess.class);
        assertNotNull(meta);
        Assert.assertTrue(TopProcessMetaModel.class.isAssignableFrom(meta.getClass()));

        // With annotation subprocess
        meta = MetaModelHelper.createActivityMetaModel(AnnotatedSubProcess.class);
        assertNotNull(meta);
        Assert.assertTrue(SubProcessMetaModel.class.isAssignableFrom(meta.getClass()));
    }
}
