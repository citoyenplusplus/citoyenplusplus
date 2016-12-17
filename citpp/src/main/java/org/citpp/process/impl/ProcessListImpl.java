package org.citpp.process.impl;

import java.util.List;

import org.citpp.process.Process;

public class ProcessListImpl implements Process {

	private final List<Process> processes;

	public ProcessListImpl(List<Process> processes) {
		super();
		this.processes = processes;
	}

	@Override
	public void execute() {
		if (this.processes != null) {
			for (Process process : processes) {
				process.execute();
			}
		}
	}

}
