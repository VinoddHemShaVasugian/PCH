package com.pch.kenofrontend.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;


/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class ProcessUtil_old {

	public void executeCommand(String command) throws IOException, InterruptedException{
		final Process p = Runtime.getRuntime().exec("cmd /c "+command);
		new Thread(new Runnable() {
			public void run() {
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = null; 

				try {
					while ((line = input.readLine()) != null)
						System.out.println(line);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

		p.waitFor();
	}

	public void executePowerShellScriptsforWinnerDraw(String computerName, String winnerDrawLocation, String drawingDate, String drawingTime, String gameId,String pickedNumbers,String powerPrizeNumber){

		Process powerShellProcess =  null;		
		try {  
			
			StringBuffer sb = new StringBuffer();
			sb.append("powershell.exe invoke-command -ComputerName "+computerName+" -ScriptBlock {& '"+winnerDrawLocation+"' \""+drawingDate+"` "+drawingTime+"\" "+gameId+" ");
			if(pickedNumbers!=null){
				sb.append("false \""+pickedNumbers+"\"");	
			}
			if(powerPrizeNumber!=null){
				sb.append(" "+powerPrizeNumber);
			}
			sb.append("}");

			String command = sb.toString();
			System.out.println(command);
			// Executing the command
			powerShellProcess = Runtime.getRuntime().exec(command);
			// Getting the results
			powerShellProcess.getOutputStream().close();
			String line;
			System.out.println("Lotto Winner Draw Output:");
			BufferedReader stdout = new BufferedReader(new InputStreamReader(
					powerShellProcess.getInputStream()));
			while ((line = stdout.readLine()) != null) {
				System.out.println(line);
				if(line.contains("[Topshelf] Running, press Control+C to exit")){
					powerShellProcess.getInputStream().close();
					break;
				}
			}
			stdout.close();			
			System.out.println("Lotto Winner Draw Executed Successfully in the location : "+winnerDrawLocation);
			createShutDownHook();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			powerShellProcess.destroy();
		}
	}

	private void createShutDownHook()
	{
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Exiting the Powershell process...");

			}
		}));
	}

	
	
	public void killRemoteProcess(String computerName,String processName) throws PowerShellNotAvailableException{
		String command = "$colProcesses = Get-WmiObject -Class Win32_Process -ComputerName "+computerName+" "
				+ "| Where-Object { $_.name -eq '"+processName+"' }\r\n" + 
				"foreach ($objProcess in $colProcesses) { $objProcess.Terminate() }";

		PowerShell powerShell = PowerShell.openSession();

		//Print results    
		System.out.println(powerShell.executeCommand(command).getCommandOutput());

		powerShell.close();
	}

}
