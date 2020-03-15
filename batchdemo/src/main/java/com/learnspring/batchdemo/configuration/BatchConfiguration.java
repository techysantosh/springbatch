package com.learnspring.batchdemo.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step getCountingStep() {
		return stepBuilderFactory.get("JobStep1").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Counting batch job step started.");
				
				for(int i=1; i<=1000; i++) {
					System.out.println("Count : " + i);
				}
				
				System.out.println("Countingbatch job step completed.");
				return RepeatStatus.FINISHED;
			}}).build();
	}
	
	@Bean
	public Job getJob() {
		return jobBuilderFactory.get("CountingBatchJob").start(getCountingStep()).build();
	}
	
	

}
