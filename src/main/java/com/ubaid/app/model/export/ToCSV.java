package com.ubaid.app.model.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import com.ubaid.app.controller.Controller;
import com.ubaid.app.model.constant.Constant;
import com.ubaid.app.model.table.PropertyInfo;

import javafx.collections.ObservableList;

public class ToCSV implements Export
{

	public ToCSV(File file, ObservableList<PropertyInfo> list,Controller controller) throws Exception
	{
		if(export(file, list, controller))
		{
			Constant.getInfo(file.getAbsolutePath() + "Has been created");
		}
	}
	
	
	@Override
	public Boolean export(File file, ObservableList<PropertyInfo> list, Controller controller) throws Exception
	{
		
		Writer writer = new BufferedWriter(new FileWriter(file));
		
		for(PropertyInfo propertyInfo : list)
		{
			String text = propertyInfo.getAddress() + " ## " + propertyInfo.getBed() + " ## " +
					propertyInfo.getBathCount() + " ## " + 
					propertyInfo.getSqft() + " ## " + 
					propertyInfo.getListingType() + " ## " + 
					propertyInfo.getPrice() + " ## " +
					propertyInfo.getProperty() + " ## " +  
					propertyInfo.getYearBuilt() + " ## " + 
					propertyInfo.getType() + " ##  " + 
					propertyInfo.getDatePosted() + " ## " +
					propertyInfo.getDescription() + "\n";

			
			writer.write(text);
					
		}
		
		writer.flush();
		writer.close();
		
		return true;
	}

}
