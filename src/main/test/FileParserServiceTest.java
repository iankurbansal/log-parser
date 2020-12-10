import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.log.parser.model.Event;
import com.log.parser.service.FileParserService;

public class FileParserServiceTest {
    FileParserService fileParserService = new FileParserService();

    @BeforeClass
    public static void runOnceBeforeClass() {
        System.out.println("@BeforeClass - runOnceBeforeClass");
    }

    @Test
    public void testGetEventFromRecord() {
        String record1 = "{\"id\":\"scsmbstgra\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495217}\n";
        String record2 = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495212}\n";

        Event expectedEvent = new Event();
        expectedEvent.setEventId("scsmbstgra");
        expectedEvent.setHost("12345");
        expectedEvent.setType("APPLICATION_LOG");
        expectedEvent.setDuration(5);
        expectedEvent.setAlert(true);
        Event parsedEvent = fileParserService.sendEventsToSaveInDb(record1);
        Assert.assertEquals(null, parsedEvent);
        parsedEvent = fileParserService.sendEventsToSaveInDb(record2);
        Assert.assertEquals(expectedEvent, parsedEvent);
    }

}
