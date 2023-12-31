package com.example.domain.batch.job;

import com.example.domain.batch.listener.JobCompletionNotificationListener;
import com.example.domain.batch.processor.DeviceProcessor;
import com.example.domain.batch.writer.DeviceWriter1;
import com.example.domain.batch.writer.DeviceWriter2;
import com.example.domain.batch.writer.DeviceWriter3;
import com.example.domain.model.Device;
import com.example.domain.repository.DeviceRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

import static com.example.application.constants.ApiConstants.BATCH_DEFAULT_CHUNK_SIZE;

@Configuration
public class SampleJob {
    private final JobCompletionNotificationListener notificationListener;
    private final DeviceRepository deviceRepository;
    private final DeviceProcessor deviceProcessor;

    public SampleJob(JobCompletionNotificationListener notificationListener, DeviceRepository deviceRepository, DeviceProcessor deviceProcessor) {
        this.notificationListener = notificationListener;
        this.deviceRepository = deviceRepository;
        this.deviceProcessor = deviceProcessor;
    }

    @Bean
    public Job sampleJob1(JobRepository jobRepository,Step sampleStep){
        return new JobBuilder("sampleJob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(this.notificationListener)
                .start(sampleStep)
                .build();
    }

    @Bean
    public RepositoryItemReader<Device> deviceRepositoryItemReader(){
        return new RepositoryItemReaderBuilder<Device>()
                .name("deviceRepositoryItemReader")
                .methodName("findAll")
                .sorts(Collections.singletonMap("name", Sort.DEFAULT_DIRECTION))
                .repository(this.deviceRepository)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Device> deviceRepositoryItemWriter(){
        return new RepositoryItemWriterBuilder<Device>()
                .methodName("save")
                .repository(this.deviceRepository)
                .build();
    }

    @Bean
    public Step sampleStep(JobRepository jobRepository,
                           PlatformTransactionManager transactionManager,
                           DeviceWriter1 deviceWriter1,
                           DeviceWriter2 deviceWriter2,
                           DeviceWriter3 deviceWriter3,
                           CompositeItemWriter<Device> compositeItemWriter){
        return new StepBuilder("sampleStep1",jobRepository)
                .<Device,Device>chunk(BATCH_DEFAULT_CHUNK_SIZE,transactionManager)
                .listener(deviceWriter1)
                .listener(deviceWriter2)
                .listener(deviceWriter3)
                .reader(deviceRepositoryItemReader())
                .writer(compositeItemWriter)
                .build();
    }

    @Bean
    public Step sampleTaskletStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("sampleStep2",jobRepository)
                .tasklet((contribution,chunkContext) -> {
                    for(int i=0; i <= 10; i++){
                        System.out.println("Looping " + i);
                    }
                    return RepeatStatus.FINISHED;
                },transactionManager)
                .build();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CompositeItemWriter<Device> compositeItemWriter(
            DeviceWriter1 deviceWriter1,
            DeviceWriter2 deviceWriter2,
            DeviceWriter3 deviceWriter3){
        return new CompositeItemWriterBuilder<Device>()
                .delegates(deviceWriter1,deviceWriter2,deviceWriter3)
                .build();

    }
}
