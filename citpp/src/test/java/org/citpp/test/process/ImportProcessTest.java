package org.citpp.test.process;

import javax.annotation.Resource;

import org.citpp.process.Process;
import org.citpp.test.app.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class ImportProcessTest {

	@Resource(name = "acteursProcess")
	private Process acteursProcess;

	@Resource(name = "votesProcess")
	private Process votesProcess;

	@Resource(name = "amendementsProcess")
	private Process amendementsProcess;

	@Resource(name = "reunionsProcess")
	private Process reunionsProcess;

	@Resource(name = "dossiersProcess")
	private Process dossiersProcess;

	@Resource(name = "questionsOralesProcess")
	private Process questionsOralesProcess;
	@Resource(name = "questionsEcritesProcess")
	private Process questionsEcritesProcess;
	@Resource(name = "questionsGouvProcess")
	private Process questionsGouvProcess;

	@Resource(name = "reservesProcess")
	private Process reservesProcess;

	@Resource(name = "representantsProcess")
	private Process representantsProcess;

	@Resource(name = "dailyProcess")
	private Process dailyProcess;

	@Test
	public void testImportActeursProcess() {
		this.acteursProcess.execute();
	}

	@Test
	public void testImportVotesProcess() {
		this.votesProcess.execute();
	}

	@Test
	public void testImportAmendementsProcess() {
		this.amendementsProcess.execute();
	}

	@Test
	public void testImportReunionsProcess() {
		this.reunionsProcess.execute();
	}

	@Test
	public void testImportDossiersProcess() {
		this.dossiersProcess.execute();
	}

	@Test
	public void testImportQuestionsOralesProcess() {
		this.questionsOralesProcess.execute();
	}

	@Test
	public void testImportQuestionsEcritesProcess() {
		this.questionsEcritesProcess.execute();
	}

	@Test
	public void testImportQuestionsGouvProcess() {
		this.questionsGouvProcess.execute();
	}

	@Test
	public void testImportReservesProcess() {
		this.reservesProcess.execute();
	}

	@Test
	public void testImportRepresentantsProcess() {
		this.representantsProcess.execute();
	}

	@Test
	public void testDailyProcess() {
		this.dailyProcess.execute();
	}
}
