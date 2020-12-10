package com.log.parser.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.log.parser.service.FileParserService;

public class ParserApp {
    private static final Logger logger = LoggerFactory.getLogger(ParserApp.class);

    public static void main(String[] args) {
        logger.info("running main");
        if (args.length != 1) {
            logger.info("Incorrect number of arguments {}. "
                + "Please re run the program using just the log file path as an argument", args.length);
            System.exit(1);
        }
        String fileName = args[0];
        FileParserService fileParserService = new FileParserService();
        fileParserService.parseFile(fileName);
    }
}