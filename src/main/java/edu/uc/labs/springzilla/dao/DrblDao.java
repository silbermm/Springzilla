package edu.uc.labs.springzilla.dao;

import edu.uc.labs.springzilla.json.*;
import edu.uc.labs.springzilla.exceptions.*;
import org.apache.commons.exec.*;
import org.apache.log4j.*;
import java.io.*;

public class DrblDao
{
	private static final Logger log = Logger.getLogger(DrblDao.class);

	public boolean startSession(CloneJson cloneData)
	{

		int exitValue;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		PumpStreamHandler psh = new PumpStreamHandler(stdout);
		try
		{
			CommandLine cmdLine = new CommandLine("/opt/drbl/sbin/drbl-ocs");
			cmdLine.addArgument("-b");
			cmdLine.addArgument("-g");
			cmdLine.addArgument("auto");
			cmdLine.addArgument("-e1");
			cmdLine.addArgument("auto");
			cmdLine.addArgument("e2");
			cmdLine.addArgument("-r");
			cmdLine.addArgument("-x");
			cmdLine.addArgument("-j2");
			cmdLine.addArgument("-p");
			cmdLine.addArgument("reboot");
			cmdLine.addArgument("--clients-to-wait");
			cmdLine.addArgument(Integer.toString(cloneData.getNumberOfClients()));
			cmdLine.addArgument("--max-time-to-wait");
			cmdLine.addArgument(Integer.toString(cloneData.getMaxWaitTime()));
			cmdLine.addArgument("l");
			cmdLine.addArgument("en_US.UTF-8");
			cmdLine.addArgument("startdisk");
			cmdLine.addArgument(cloneData.getImageProtocol() + "_restore");
			cmdLine.addArgument(cloneData.getImage());
			cmdLine.addArgument("sda");

			Executor exec = new DefaultExecutor();
			exec.setExitValue(1);
			exec.setStreamHandler(psh);
			exitValue = exec.execute(cmdLine);


			boolean r = (exitValue == 0) ? true : false;
			if (!r)
			{
				log.debug("exit code: " + exitValue + " " + stdout.toString());
				throw new SessionException("exit code: " + exitValue + " " + stdout.toString());
			}
		}
		catch (ExecuteException e)
		{
			throw new SessionException("There was a problem starting the session: " + e.getMessage());
		}
		catch (IOException e) {
			throw new SessionException("There was a problem starting the session: " + e.getMessage());
		}
		return true;
	}


}

