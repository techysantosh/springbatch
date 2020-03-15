package com.learnspring.multistepbatchjob.configuration;

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
	public Step getNumbersPrintingStep() {
		return stepBuilderFactory.get("PrintNumbers").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				for(int i=0; i<=10; i++) {
					System.out.println("Count : " + i);
				}
				return RepeatStatus.FINISHED;
			}
			
		}).build();
	}
	
	@Bean 
	public Step getAlphabetPrintingStep() {
		return stepBuilderFactory.get("PrintAlphabets").tasklet((contribution, chunkContext) -> {
			for(char ch='A'; ch<'M'; ch++) {
				System.out.println("Char : " + ch);
			}
			return RepeatStatus.FINISHED;
		}).build();
	}
	
	@Bean
	public Job getNumberAndAlphaPrintingJob() {
		return jobBuilderFactory.get("NumberAndAlphaPrintingJob").start(getNumbersPrintingStep())
				.next(getAlphabetPrintingStep()).build();
	}

}
