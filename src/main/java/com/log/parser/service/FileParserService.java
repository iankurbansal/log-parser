package com.log.parser.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.log.parser.dao.EventDao;
import com.log.parser.model.Event;
import com.log.parser.model.LogRecord;

/**
 * Class used to parse large size of log file
 */
public class FileParserService {
    private static final Map<String, Long> eventMap = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final Gson gson = new Gson();
    EventDao eventDao = new EventDao();
    private static final Logger logger = LoggerFactory.getLogger(FileParserService.class);
    private static final List<Event> events = new CopyOnWriteArrayList<>();

    /**
     * Parse the given file and save events in HSQL database
     *
     * @param fileName - full path of the file that need to be parsed
     */
    public void parseFile(String fileName) {
        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(new File(fileName), "UTF-8");
            while (it.hasNext()) {
                String line = it.nextLine();
                Runnable task = () -> sendEventsToSaveInDb(line);
                executor.submit(task);
            }
        } catch (IOException e) {
            logger.error("error while reading file : ", e);
            System.exit(1);
        } finally {
            LineIterator.closeQuietly(it);
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                saveEventsInBatch(true);
            } catch (InterruptedException e) {
                logger.error(" exception while waiting for termination ", e);
            }
            System.exit(0);
        }
    }

    public Event sendEventsToSaveInDb(String logEntry) {
        LogRecord record = gson.fromJson(logEntry, LogRecord.class);
        long duration = getEventDuration(record);
        if (duration != 0) {
            Event event = getEventFromRecord(record);
            event.setAlert(isAlert(duration));
            event.setDuration(duration);
            events.add(event);
            saveEventsInBatch(false);
            return event;
        }
        return null;
    }

    public boolean saveEventsInBatch(boolean isLastBatch) {
        if (events.size() >= 1000 || isLastBatch) {
            logger.debug("will save the batch of events in DB");
            List<Event> batch = new ArrayList<>(events);
            events.clear();
            for (Event event : batch) {
                eventDao.saveEvent(event);
            }
            return true;
        }
        return false;
    }

    public Event getEventFromRecord(LogRecord record) {
        Event event = new Event();
        event.setHost(record.getHost());
        event.setEventId(record.getId());
        event.setType(record.getType());
        return event;
    }

    public boolean isAlert(long duration) {
        return duration > 4;
    }

    public long getEventDuration(LogRecord record) {
        Long existingTime = eventMap.put(record.getId(), record.getTimestamp());
        if (existingTime == null) {
            return 0L;
        }
        return Math.abs(record.getTimestamp() - existingTime);
    }
}
