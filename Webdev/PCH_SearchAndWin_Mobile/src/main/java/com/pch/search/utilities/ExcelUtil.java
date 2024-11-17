package com.pch.search.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.WritableFont.FontName;
import jxl.write.biff.RowsExceededException;




public class ExcelUtil {
	public static enum CellType {Header,SubHeader,TestName,PassResult,FailResult,SkipResult}	
	private WritableWorkbook workBook;
	private WritableSheet currentSheet;


	public void mergeCell(int col1,int row1,int col2,int row2) throws RowsExceededException, WriteException{
		currentSheet.mergeCells(col1, row1, col2, row2);
	}

	public ExcelUtil(File f) throws BiffException, IOException{
		this.workBook=Workbook.createWorkbook(f);
	}

	public void close() throws IOException, WriteException{
		workBook.write();
		workBook.close();

	}

	public void createSheet(String sheetName,int index){
		currentSheet=workBook.createSheet(sheetName,index);
	}

	public void setColumnSize(int col,int size){
		currentSheet.setColumnView(col,size);
	}

	public void writeLink(String linkText,File link,int col,int row) throws RowsExceededException, WriteException{		

		WritableCellFormat wcf = new WritableCellFormat();
		wcf.setAlignment(Alignment.CENTRE);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcf.setBorder(Border.ALL, BorderLineStyle.MEDIUM);

		Label label = new Label(col, row, linkText, wcf);
		currentSheet.addCell(label);

		WritableHyperlink hyperlink = new WritableHyperlink(col,row,link);
		hyperlink.setDescription(linkText);
		currentSheet.addHyperlink(hyperlink);		
	}
	
	public void writeLink(String linkFormula,int col, int row) throws WriteException{
		WritableFont wf=new WritableFont(WritableFont.ARIAL);
		wf.setColour(Colour.BLUE);
		wf.setBoldStyle(WritableFont.BOLD);
		WritableCellFormat wcf = new WritableCellFormat();
		wcf.setAlignment(Alignment.CENTRE);		
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcf.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
		wcf.setFont(wf);
		//Label label = new Label(col, row, linkText, wcf);
		//currentSheet.addCell(label);		
		currentSheet.addCell(new Formula(col, row, linkFormula, wcf));
		

	}
	public void writeText(Integer i,int col,int row, int size,
			FontName font,boolean isBold, Alignment hAlign, 
			VerticalAlignment vAlign, Colour frgrndColor,Colour backgrndColour){
		writeText(i.toString(), col, row, size, font, isBold, hAlign, vAlign, frgrndColor, backgrndColour);
	}
	
	public void addComment(String text,int col,int row){
		WritableCellFeatures wcf = new WritableCellFeatures();
		wcf.setComment(text);
		currentSheet.getWritableCell(col, row).setCellFeatures(wcf);		
	}
		
	
	public void writeText(String text,int col,int row, int size,
			FontName font,boolean isBold, Alignment hAlign, 
			VerticalAlignment vAlign, Colour frgrndColor,Colour backgrndColour){
		try{
						
			WritableFont wf = null;
			WritableCellFormat wcf = null;
			Label label = null;
			wf=new WritableFont(font);
			if(isBold)
				wf.setBoldStyle(WritableFont.BOLD);

			wf.setPointSize(size);

			wf.setColour(frgrndColor);


			wcf = new WritableCellFormat();
			wcf.setFont(wf);
			wcf.setAlignment(hAlign);
			wcf.setVerticalAlignment(vAlign);
			if(backgrndColour!=Colour.WHITE)
				wcf.setBackground(backgrndColour);
			wcf.setBorder(Border.ALL, BorderLineStyle.MEDIUM);		

			if(!StringUtils.isNumeric(text)){
				label = new Label(col, row, text, wcf);
				
				currentSheet.addCell(label);
			}
			else
				currentSheet.addCell(new Number(col, row, Integer.parseInt(text) , wcf));
				
		}catch(Exception e){

		}

	}



}