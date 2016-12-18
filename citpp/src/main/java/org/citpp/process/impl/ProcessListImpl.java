package org.citpp.process.impl;

import java.util.List;

import org.citpp.process.Process;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessListImpl implements Process {

	private static final Logger LOG = LoggerFactory.getLogger(ProcessListImpl.class);
	private final List<Process> processes;

	public ProcessListImpl(List<Process> processes) {
		super();
		this.processes = processes;
	}

	@Override
	public void execute() {
		if (this.processes != null) {
			for (Process process : processes) {
				LOG.debug("======================================================================");
				process.execute();
				LOG.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			}
		}
	}

}
