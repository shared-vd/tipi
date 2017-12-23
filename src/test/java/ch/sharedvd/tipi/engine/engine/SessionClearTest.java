package ch.sharedvd.tipi.engine.engine;

import ch.sharedvd.tipi.engine.action.ActivityResultContext;
import ch.sharedvd.tipi.engine.action.FinishedActivityResultContext;
import ch.sharedvd.tipi.engine.action.TopProcess;
import ch.sharedvd.tipi.engine.common.TipiEngineTest;
import ch.sharedvd.tipi.engine.meta.TopProcessMetaModel;
import ch.sharedvd.tipi.engine.model.DbActivity;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class SessionClearTest extends TipiEngineTest {

    public static class SessionClearProcess extends TopProcess {

        @Autowired
        private EntityManager em;

        public static final TopProcessMetaModel meta = new TopProcessMetaModel(SessionClearProcess.class, 2, -1, 10, null) {
            @Override
            protected void init() {
                setDeleteWhenFinished(false);
            }
        };

        @Override
        protected ActivityResultContext execute() throws Exception {
            em.clear();

            facade.putVariable("bla", "bli");

            return new FinishedActivityResultContext();
        }
    }

    @Test
    public void clear() throws Exception {

        final long pid = tipiFacade.launch(SessionClearProcess.meta, null);
        waitWhileRunning(pid, 5000);

        txTemplate.txWithout(s -> {
            DbActivity model = activityRepository.findOne(pid);
            Assert.assertEquals("bli", model.getVariable("bla"));

        });
    }

}