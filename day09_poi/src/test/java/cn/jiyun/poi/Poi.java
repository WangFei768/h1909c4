package cn.jiyun.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

public class Poi {

	@Test
	public void out() throws Exception{
		
		Student student = new Student();
		student.setSid(1);
		student.setSname("张三1");
		student.setSex("男");
		student.setBirthday(new Date("2020/02/02"));
		student.setHobby("LOL,WOW");
		student.setMid(1);
		
		Student student1 = new Student();
		student1.setSid(2);
		student1.setSname("张三2");
		student1.setSex("男");
		student1.setBirthday(new Date("2020/02/02"));
		student1.setHobby("LOL,WOW");
		student1.setMid(3);
		
		
		Student student2 = new Student();
		student2.setSid(3);
		student2.setSname("张三3");
		student2.setSex("男");
		student2.setBirthday(new Date("2020/02/02"));
		student2.setHobby("LOL,WOW");
		student2.setMid(3);
		
		List<Student> list = new ArrayList<Student>();
		list.add(student);
		list.add(student1);
		list.add(student2);
		
		//实例化工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建sheet页
		HSSFSheet sheet = workbook.createSheet("学生信息");
		//创建行row
		HSSFRow row0 = sheet.createRow(0);
		//创建单元格并赋值
		String[] title = {"编号","姓名","性别","生日","爱好","班级"};
		for (int i = 0; i < title.length; i++) {
			HSSFCell cell = row0.createCell(i);
			cell.setCellValue(title[i]);
		}
		for (int i = 0; i < list.size(); i++) {
			HSSFRow row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(list.get(i).getSid());
			row.createCell(1).setCellValue(list.get(i).getSname());
			row.createCell(2).setCellValue(list.get(i).getSex());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			row.createCell(3).setCellValue(simpleDateFormat.format(list.get(i).getBirthday()));
			row.createCell(4).setCellValue(list.get(i).getHobby());
			if(1==(list.get(i).getMid())){
				row.createCell(5).setCellValue("H1909A");
			}else if(2==(list.get(i).getMid())){
				row.createCell(5).setCellValue("H1909B");
			}else {
				row.createCell(5).setCellValue("未知");
			}
			
		}
		//导出
		FileOutputStream  outputStream =new FileOutputStream("D:/学生信息表.xlsx");
		workbook.write(outputStream);
	}
	@Test
	public void in() throws Exception{
		FileInputStream is = new FileInputStream("D:/学生信息表.xlsx");
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		HSSFSheet sheet = workbook.getSheetAt(0);
		int lastRowNum = sheet.getLastRowNum();
		ArrayList<Student> list = new ArrayList<Student>();
		for (int i = 1; i <lastRowNum; i++) {
			HSSFRow row = sheet.getRow(i);
			Student student = new Student();
			student.setSid((int)row.getCell(0).getNumericCellValue());
			student.setSname(row.getCell(1).getStringCellValue());
			student.setSex(row.getCell(2).getStringCellValue());
			String bir = row.getCell(3).getStringCellValue();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			student.setBirthday(simpleDateFormat.parse(bir));
			student.setHobby(row.getCell(4).getStringCellValue());
			if("H1909A".equals(row.getCell(5).getStringCellValue())){
				student.setMid(1);
			}else if("H1909B".equals(row.getCell(5).getStringCellValue())){
				student.setMid(2);
			}else{
				student.setMid(3);
			}
			list.add(student);
			
		}
		System.out.println(list);

	}
	
}
/*@Test
public void in() throws Exception{
	
	FileInputStream is = new FileInputStream("D:/学生信息表.xlsx");
	//1.创建工作簿
	HSSFWorkbook workbook = new HSSFWorkbook(is);
	//2.读取sheet页
	HSSFSheet sheet = workbook.getSheetAt(0);
	//3.获取行
	int lastRowNum = sheet.getLastRowNum();
	ArrayList<Student> list = new ArrayList<Student>();
	for (int i = 1; i < lastRowNum; i++) {
		HSSFRow row = sheet.getRow(i);
		Student student = new Student();
		student.setSid((int)row.getCell(0).getNumericCellValue());
		student.setSname(row.getCell(1).getStringCellValue());
		student.setSex(row.getCell(2).getStringCellValue());
		String bir = row.getCell(3).getStringCellValue();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		student.setBirthday(simpleDateFormat.parse(bir));
		student.setHobby(row.getCell(4).getStringCellValue());
		if("H1909A".equals(row.getCell(5).getStringCellValue())){
			student.setCid(1);
		}else if("H1909B".equals(row.getCell(5).getStringCellValue())){
			student.setCid(2);
		}else{
			student.setCid(3);
		}
		list.add(student);
		
	}
	System.out.println(list);
}*/