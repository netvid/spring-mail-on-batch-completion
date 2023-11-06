package com.example.domain.batch.processor;

import com.example.domain.model.Device;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.example.application.constants.ApiConstants.BATCH_DEVICE_KEY_1;

@Component
@JobScope
public class DeviceProcessor implements ItemProcessor<Device, Device> {
    @Value("#{jobExecution.executionContext}")
    private ExecutionContext executionContext;

    @Override
    public Device process(Device item) {
        if(executionContext.containsKey(BATCH_DEVICE_KEY_1)){
            int processedItems = this.executionContext.getInt(BATCH_DEVICE_KEY_1);
            this.executionContext.put(BATCH_DEVICE_KEY_1,processedItems + 1);
        }
        return item;
    }
}
