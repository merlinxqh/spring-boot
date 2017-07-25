package com.spring.common.task;

import org.springframework.scheduling.annotation.Scheduled;


/**
 * @author task任务
 *
 */
public class MyTask {
	
   @Scheduled(fixedRate=60000)
   public void work(){
	   System.out.println("execute work...."+System.currentTimeMillis());
   }
}
