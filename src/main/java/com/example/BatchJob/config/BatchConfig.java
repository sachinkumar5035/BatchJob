package com.example.BatchJob.config;

import com.example.BatchJob.model.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    @Bean
    public Job jobBean(JobRepository jobRepository,
                       JobCompletionNotificationImpl jobListener,
                       Step steps
                        ){ // this job will be launch by job launcher
        return new JobBuilder("excelReadJob",jobRepository)
                .listener(jobListener)
                .start(steps)
                .build();
    }

    @Bean
    public Step steps(JobRepository jobRepository,
                      DataSourceTransactionManager transactionManager,
                      ItemReader<Employee> employeeReader,
                      ItemProcessor<Employee,Employee> employeeProcessor,
                      ItemWriter<Employee> employeeWriter  ) {
        return new StepBuilder("steps", jobRepository)
                .<Employee,Employee>chunk(5,transactionManager)
                .reader(employeeReader)
                .processor(employeeProcessor)
                .writer(employeeWriter)
                .build();
    }

    // create a reader
    @Bean
    public FlatFileItemReader<Employee> employeeReader() {
        return new FlatFileItemReaderBuilder<Employee>()
                .name("employeeReader")
                .resource(new ClassPathResource("data.csv"))
                .delimited()
                .names("empId","name","address","salary") // these are the name of the column which we will read from the csv file
                .targetType(Employee.class) // after reading the csv file we will be targeting the employee class
                .build();
    }

    // create a processor

    @Bean
    public ItemProcessor<Employee,Employee> employeeProcessor() {
        return new CustomEmployeeProcessor();
    }


    // create a writer
    @Bean
    public ItemWriter<Employee> employeeWriter(DataSource dataSource) {
        return  new JdbcBatchItemWriterBuilder<Employee>()
                .sql("INSERT into employee(empId,name,address,salary,hraPer,hra) values(:empId,:name,:address,:salary,:hraPer,:hra)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

}
