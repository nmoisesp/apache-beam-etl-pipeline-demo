package com.demo.pipeline.transformations;

import java.util.List;
import org.apache.beam.sdk.transforms.DoFn;

public class FilterWinnersTransformation extends DoFn<List<String>, List<String>> {

    private static final Integer CSV_COLUMN_POSITION_FOR_WINNER = 4;
    private static final String WINNER_STATUS = "yes";
    private static final Integer CSV_HEADER_SIZE = 5;

    @ProcessElement
    public void processElement(ProcessContext context) {
        List<String> row = context.element();

        if(row.size() == CSV_HEADER_SIZE &&
                WINNER_STATUS.equalsIgnoreCase(row.get(CSV_COLUMN_POSITION_FOR_WINNER))) {
            context.output(row);
        }
    }
}
