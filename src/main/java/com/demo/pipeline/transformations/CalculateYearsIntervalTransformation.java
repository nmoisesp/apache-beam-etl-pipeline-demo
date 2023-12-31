package com.demo.pipeline.transformations;

import avro.shaded.com.google.common.collect.Lists;
import org.apache.beam.repackaged.core.org.apache.commons.lang3.StringUtils;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.ArrayList;
import java.util.List;

public class CalculateYearsIntervalTransformation extends DoFn<KV<String,Iterable<String>>,KV<String,Iterable<Integer>>> {

    private static final Integer MIN_OF_AWARDS_REQUIRED = 2;

    @ProcessElement
    public void processElement(ProcessContext context) {
        KV<String,Iterable<String>> element = context.element();
        List<String> years = Lists.newArrayList(element.getValue());

        if (years.size() >= MIN_OF_AWARDS_REQUIRED) {
            List<String> yearsSorted = years.stream().sorted().toList();

            for (int i = yearsSorted.size() -1; i > 0; i--){
                List<Integer> yearsAndIntervalCalculated = new ArrayList<>();
                String followingWin = yearsSorted.get(i);
                String previousWin = yearsSorted.get(i -1);

                if (StringUtils.isNumeric(followingWin) && StringUtils.isNumeric(previousWin)) {
                    Integer followingWinConverted = Integer.valueOf(followingWin);
                    Integer previousWinConverted = Integer.valueOf(previousWin);
                    Integer interval = followingWinConverted - previousWinConverted;

                    yearsAndIntervalCalculated.add(0, previousWinConverted);
                    yearsAndIntervalCalculated.add(1, followingWinConverted);
                    yearsAndIntervalCalculated.add(2, interval);
                }
                context.output(KV.of(element.getKey(), yearsAndIntervalCalculated));
            }
        }
    }
}