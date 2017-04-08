package com.spring.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.spring.task.MyTask;

@Configuration
@EnableScheduling
@ComponentScan(basePackages="com.spring.task")
public class TaskConfig implements SchedulingConfigurer{
  
	
	@Bean
	public MyTask myTask(){
		return new MyTask();
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
//		taskRegistrar.addTriggerTask(
//				           new Runnable() {
//				                public void run() {
//				                    myTask().work();
//				                }
//				            },
//				            new CustomTrigger()
//				       );
	}
	
	/**
	 * 线程池
	 * @return
	 */
	@Bean(destroyMethod="shutdown")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(100);
	}
}
