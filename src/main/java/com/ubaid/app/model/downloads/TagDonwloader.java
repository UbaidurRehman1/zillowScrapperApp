package com.ubaid.app.model.downloads;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import com.ubaid.app.model.constant.Constant;

public class TagDonwloader implements Runnable
{

	private DHFTS dhfts;
	 
	
	public TagDonwloader(DHFTS dhfts)
	{
		setDhfts(dhfts);		
	}
	
	
	@Override
	public void run()
	{
		cmd();
	}
	
	//we loop each after 15 seconds to download an add from tagCollection 
	public void cmd()
	{
			
			try
			{
				//now getting url of next page from the current page
				for(int i = 0; i < getDhfts().getTagCollections().size(); i++)
				{
					
					//command
					String pythonCommand = "python " + "\"" +
								Constant.getScrapperPath().trim() + "\" " +  "\"" +
								getDhfts().getTagCollections().get(i) + "\"";
	
					String filePath = " " + "\"" +
							getDhfts().getTagsSearchDir().getAbsolutePath() + "\\" + 
							(getDhfts().getUrl_present_in_database() ?
									(getDhfts().getLastIndexOfTable() + (i + 1)) : (i + 1) ) + ".html" + "\"";
										
					try
					{
						
						//writing the above command in the bat file and then executing this file 
						String string_to_write = "start /B call " + pythonCommand + filePath;
						File batFile = null;
						
						try
						{
							batFile = new File(Constant.getAppDirectory() + "\\adDonwload.bat");
							FileUtils.writeStringToFile(batFile, string_to_write, "UTF-8");
						}
						catch(Exception exp)
						{
							Constant.getAlert("fileDownload.bat file is not created, there is an error");
						}

						
						
						Process process = Runtime.getRuntime().exec(batFile.getAbsolutePath());
						boolean isAlive = process.isAlive();
						System.out.println(isAlive);
						while(isAlive)
						{
							Thread.sleep(1500);
						}
						Constant.setLabel("Downloading  ad File " + filePath + "/n Stay Calm", getDhfts().getController());
//						Thread.sleep(15000);
						
						filePath = filePath.trim();
						filePath = filePath.substring(1, filePath.length() - 1);
						
						File file = new File(filePath);
						
						
						if(file.exists())
						{
							Constant.setLabel(filePath + " created " + "/n Stay Calm", getDhfts().getController());
						}
						else
						{
							Constant.getAlert(file.getAbsolutePath() + " not created due to some unknown error\nTrying again");
							i--;
						}
					}
					catch (IOException e) 
					{
						Constant.getAlert(Arrays.toString(e.getStackTrace()));
					}

					
				}
			}
			catch(Exception exp)
			{
				Constant.getAlert(Arrays.toString(exp.getStackTrace()));
			}
			
	}


	public DHFTS getDhfts()
	{
		return dhfts;
	}


	public void setDhfts(DHFTS dhfts)
	{
		this.dhfts = dhfts;
	}

}
