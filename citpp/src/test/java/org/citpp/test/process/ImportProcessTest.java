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
}
