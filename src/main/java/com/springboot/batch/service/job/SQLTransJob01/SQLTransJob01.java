package com.springboot.batch.service.job.SQLTransJob01;

import com.springboot.batch.config.database.context.RoutingDatabaseInfo;
import com.springboot.batch.service.job.SQLTransJob01.listener.SQLTransJobListener;
import com.springboot.batch.service.job.SQLTransJob01.listener.SQLTransStepListener;
import com.springboot.batch.service.job.SQLTransJob01.parameter.SQLTransJobParameter;
import com.springboot.batch.service.job.SQLTransJob01.parameter.SQLTransJobParameterAndIncrementer;
import com.springboot.batch.service.job.SQLTransJob01.parameter.SQLTransJobParametersValidate;
import com.springboot.batch.service.job.SQLTransJob01.parameter.SQLTransJobShareParameter;
import com.springboot.batch.service.job.SQLTransJob01.step.Step1TaskLet;
import com.springboot.batch.service.job.SQLTransJob01.step.Step2TaskLet;
import com.springboot.batch.service.job.SQLTransJob01.step.Step3TaskLet;
import com.springboot.batch.service.job.SQLTransJob01.step.Step4TaskLet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j // log 사용을 위한 lombok 어노테이션
@RequiredArgsConstructor // 생성자 DI를 위한 lombok 어노테이션
@Configuration
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "JOB01")
public class SQLTransJob01 {

	private final JobBuilderFactory jobBuilderFactory;   // 생성자 DI 받음
	private final StepBuilderFactory stepBuilderFactory; // 생성자 DI 받음
	private final SQLTransJobParameter jobParameter;

	@Bean
    @JobScope
    public SQLTransJobParameter jobParameter() {

		return new SQLTransJobParameter();
    }

	@Autowired
	Step1TaskLet step1TaskLet;

	@Autowired
	Step2TaskLet step2TaskLet;

	@Autowired
	Step3TaskLet step3TaskLet;

	@Autowired
	Step4TaskLet step4TaskLet;

	@Resource
	private ApplicationArguments applicationArguments;  // CLI 인수s

	@Autowired
	SQLTransJobShareParameter<Object> shareParam;

	@Autowired
	SQLTransJobShareParameter<List<Map<String, Object>>> shareParamList;

	@Bean(name="JOB01")
	public Job job() throws Exception {
		log.info("======== Hollow SQLTransJob01 ========");
		// 초기 CLI 인수 JobParameter 세팅
		String jobExeDate = applicationArguments.getSourceArgs()[1];
		Integer jobExeSeq  = Integer.valueOf(applicationArguments.getSourceArgs()[2]);

		// Share Parameter 세팅
		shareParam.putData("JOB_EXE_DATE", jobExeDate);
		shareParam.putData("JOB_EXE_SEQ", jobExeSeq);

		return (Job) jobBuilderFactory.get("JOB01")
				.listener(new SQLTransJobListener())
				//.incrementer(new SQLTransJobParameterAndIncrementer(jobExeDate, jobExeSeq))
				.incrementer(new SQLTransJobParameterAndIncrementer("20220522", 1))
				.validator(new SQLTransJobParametersValidate())   // 유효성 검사
				.start(step1())
					.on("FAILED")
					.to(step5())
					.on("*")
					.end()
				.from(step1())
					.on("*")
					.end()
				.next(step2())
					.on("FAILED")
					.to(step4())
					.on("*")
					.end()
				.from(step2())
					.on("*")
					.end()
				.next(step3())
					.on("FAILED")
				    .to(step4())
				    .on("*")
					.end()
				.from(step3())
					.on("*")
					.end()
				.next(step4())
					.on("*")
					.end()
				.next(step5())
					.on("*")
					.end()
				.end()
				/*
				.from(step1())
					.on("*")
					.to(step2())
					.on("FAILED")
					.to(step5())
					.on("*")
					.end()
					*/
				.build();
	}

	@Bean
	@JobScope
	public Step step1() {
		int chunkSize = 10;
		return stepBuilderFactory.get("step1")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is STEP1");
					try {
						List<Map<String, Object>> rtnList = step1TaskLet.step1TaskLetProcess(jobParameter);
						if(rtnList.isEmpty()) throw new Exception("rtnList.isEmpty 실행 시킬 JOB 정보가 없습니다.");

					} catch (Exception e) {
						log.error("STEP1 TaskletProcess ERROR - "+ e);
						contribution.setExitStatus(ExitStatus.FAILED);
					}

					return RepeatStatus.FINISHED;
				})
				.build();
	}

	@Bean
	@JobScope
	public Step step2() {
		return stepBuilderFactory.get("step2")
				.listener(new SQLTransStepListener())
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is STEP2");
					try {
						Boolean rtn = step2TaskLet.step2TaskLetProcess(jobParameter);
						if(!rtn) throw new Exception("rtn : false");

					} catch (Exception e) {
						log.error("STEP2 TaskletProcess ERROR - "+ e);
						contribution.setExitStatus(ExitStatus.FAILED);
					}
					//contribution.setExitStatus(ExitStatus.FAILED);
					return RepeatStatus.FINISHED;
				})
				.build();
	}

	@Bean
	@JobScope
	public Step step3() {
		/*
		return stepBuilderFactory.get("step3")
				.<List<Map<String, Object>>, List<Map<String, Object>>> chunk(5000)    // <Object, Object> 에서 첫번째는 Reader에서 반환타입, 두번쨰는 Writer에 파라미터로 넘어올 타입
				.reader(customItemReader())
				.writer(customItemWriter())
				//.taskExecutor(taskExecutor())
				.build();
		*/

		return stepBuilderFactory.get("step3")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is STEP3");
					Boolean rtn = step3TaskLet.step3TaskLetProcess(jobParameter);
					if(!rtn) throw new Exception("rtn : false");
					/*
					try {
						//boolean rtn = false;//(TaskletStep) step3Chunk.step3ChunkProcess();
						Boolean rtn = step3TaskLet.step3TaskLetProcess(jobParameter);
						if(!rtn) throw new Exception("rtn : false");

					} catch (Exception e) {
						log.error("STEP3 TaskletProcess ERROR - "+ e);
						contribution.setExitStatus(ExitStatus.FAILED);
					}

					 */
					return RepeatStatus.FINISHED;
				})
				.build();
	}

	/*
	@Bean
	@StepScope
	@Transactional(propagation= Propagation.REQUIRES_NEW)
	public SynchronizedItemStreamReader<List<Map<String, Object>>> customItemReader() {
		return step3Dao.selectSourceItemReader();
	}

	@StepScope
	@Transactional(propagation= Propagation.REQUIRES_NEW)
	public MyBatisBatchItemWriter<List<Map<String, Object>>> customItemWriter() throws Exception {
		return step3Dao.insertTargetItemWriter();
	}
	*/

	@Bean
	@JobScope
	public Step step4() {
		return stepBuilderFactory.get("step4")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is STEP4");
					try {
						Boolean rtn = step4TaskLet.step4TaskLetProcess(jobParameter);
						if(!rtn) throw new Exception("rtn : false");

					} catch (Exception e){
						log.error("STEP4 TaskletProcess ERROR - "+ e);
						contribution.setExitStatus(ExitStatus.FAILED);
					}
					return RepeatStatus.FINISHED;
				})
				.build();
	}

	@Bean
	@JobScope
	public Step step5() {
		return stepBuilderFactory.get("step5")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is STEP5");
					log.info("SQLTransJob01 작업 정상 종료");
					contribution.setExitStatus(ExitStatus.COMPLETED);
					return RepeatStatus.FINISHED;
				})
				.build();
	}
}