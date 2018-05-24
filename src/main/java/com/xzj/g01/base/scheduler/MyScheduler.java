package com.xzj.g01.base.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 适配执行所有定时任务
 * @author zane
 * @Time 2018-5-18 下午6:04:35
 */
public class MyScheduler {
	
	private static SchedulerFactory factory = new StdSchedulerFactory();
	
	private static final Logger logger = Logger.getLogger(MyScheduler.class);
	
	/**
	 * 执行定时任务
	 * @param job 任务
	 * @param startTime 开启时间
	 * @param cronScheduleBuilder 定时调度 如：CronScheduleBuilder.cronSchedule("0/2 * * * ?")//每2秒执行一次
	 */
	public static void schedule(Job job, Date startTime, CronScheduleBuilder cronScheduleBuilder){
		try {
			Scheduler scheduler = factory.getScheduler();
			JobDetail jobDetail = JobBuilder.newJob(job.getClass()).build();
			Trigger trigger = TriggerBuilder.newTrigger()
                    .startAt(startTime)  //设置调度开启时间，不设置时默认当前时间启动
                    .withSchedule(cronScheduleBuilder) //两秒执行一次
					.build();
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			logger.info("job "+job.getClass().getSimpleName()+" started");
		} catch (SchedulerException e) {
			logger.error("schedule job "+job.getClass().getSimpleName()+" error");
			e.printStackTrace();
		}
	}
	
}
