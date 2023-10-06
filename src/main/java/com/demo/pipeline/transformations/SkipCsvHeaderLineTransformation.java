package com.demo.pipeline.transformations;

import org.apache.beam.repackaged.core.org.apache.commons.lang3.StringUtils;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.Arrays;
import java.util.List;

public class SkipCsvHeaderLineTransformation extends DoFn<String, List<String>> {

    private static final String CSV_HEADER = "year;titles;studios;producers;winner";

    @ProcessElement
    public void processElement(ProcessContext context) {
        String row = context.element();

        if (!row.equalsIgnoreCase(CSV_HEADER)){
            List<String> columns = Arrays.asList(context.element().split(";"));
            if (StringUtils.isNumeric(columns.get(0)) &&
                    !StringUtils.isEmpty(columns.get(1)) &&
                    !StringUtils.isEmpty(columns.get(2)) &&
                    !StringUtils.isEmpty(columns.get(3))) {
                context.output(columns);
            }
        }
    }
}