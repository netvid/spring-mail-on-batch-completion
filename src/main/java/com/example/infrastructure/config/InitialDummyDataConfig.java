package com.example.infrastructure.config;

import com.example.domain.model.Device;
import com.example.domain.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

import java.util.Arrays;
import java.util.List;


@Profile("local")
@Configuration
@Slf4j
public class InitialDummyDataConfig {

    private final DeviceRepository deviceRepository;

    public InitialDummyDataConfig(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        try{
            Device device1 = new Device("Asus L51");
            Device device2 = new Device("Logitech G201");
            Device device3 = new Device("MSI 800");
            List<Device> deviceList = Arrays.asList(device1,device2,device3);
            this.deviceRepository.saveAll(deviceList);
            log.info("Dummy data has inserted success");
        }catch (Exception ex){
            log.error("An error has occurred inserting dummy data, error: {}",ex.getMessage());
            return ;
        }
    }
}
