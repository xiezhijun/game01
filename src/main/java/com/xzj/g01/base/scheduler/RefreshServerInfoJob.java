package com.xzj.g01.base.scheduler;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.xzj.g01.base.data.ServerInfoData;

@Component
public class RefreshServerInfoJob implements Job{
	
	/**定时刷新时间，每分钟执行一次*/
	public static final CronScheduleBuilder refreshTime = CronScheduleBuilder.cronSchedule("0 0/1 * * * ?");
	
	private Logger logger = Logger.getLogger(RefreshServerInfoJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ServerInfoData.getInstance().refreshServerInfo();
		logger.info("refresh serverInfo success !");
	}
}