package ch.sharedvd.tipi.engine.dates;

import ch.sharedvd.tipi.engine.common.TipiEngineTest;
import ch.sharedvd.tipi.engine.model.DbTopProcess;
import org.junit.Assert;
import org.junit.Test;

public class DateDebutFinExecutionTest extends TipiEngineTest {

    @Test
    public void diffDebutFin() throws Exception {

        final long pid = tipiFacade.launch(DateDebutFinProcess.meta, null);
        waitWhileRunning(pid, 5000);

        txTemplate.txWithout(s -> {
            DbTopProcess process = topProcessRepository.findById(pid).orElse(null);
            long diff = process.getDateEndActivity().getTime() - process.getCreationDate().getTime();
            Assert.assertTrue("Diff trop petit: " + diff, diff > 500);

        });
    }

}
