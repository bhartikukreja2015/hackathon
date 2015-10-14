package abc;


import java.awt.BorderLayout;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ReadXlsx 
{
	static String typeOfSql[]=new String[60];//first column of output
	static String sqlIdentifier[]=new String[60];//second column of output
	static String targetSchema[]=new String[60];//3 column
	static String targetTable[]=new String[60];//4 column
	static String targetColumn[]=new String[60];//5 column
	static String sourceSchema[]=new String[60];//6 column
	static String sourceTable[]=new String[60];//7 column
	static String sourceColumn[]=new String[60];//8 column
	static String transformation[]=new String[60];//9th column
	static int actualRows=0;
	static int count=0;
	static int increment[];
	
	/*public static void moveTypeOfSql(int i)

	{
		int g=0;

		while (typeOfSql[g]!=null)

		{
			g++;
		}

		for(int j=g;j>=i;j--)

		{
			typeOfSql[j+1]=typeOfSql[j];

		}

		typeOfSql[i+1]=typeOfSql[i];

	}

	public static void movesqlIdentifier(int i)

	{
		int g=0;

		while (sqlIdentifier[g]!=null)

		{
			g++;
		}

		for(int j=g;j>=i;j--)

		{
			sqlIdentifier[j+1]=sqlIdentifier[j];

		}

		sqlIdentifier[i+1]=sqlIdentifier[i];

	}

*/



	public static void getInfo(String path) 
	{

		try {
			//output arrays

			typeOfSql[0]="type of sql statement";

			sqlIdentifier[0]=new String("sql identifier");

			targetSchema[0]="target schema/database";

			targetTable[0]="target table";

			targetColumn[0]="target column";

			sourceSchema[0]="source db/schema";

			sourceTable[0]="source table";

			sourceColumn[0]="source table";

			transformation[0]="transformations";
			//result file

			//  FileOutputStream fileOut=null;

			if(path==null)
			{
				path="D:/work/Hackathon-SQL.xls";
			}
			//input file
			FileInputStream fileInputStream = new FileInputStream(path);
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet worksheet = workbook.getSheet("Sheet1");
			HSSFHeader header = worksheet.getHeader();
			System.out.println(worksheet);


			//headings
			HSSFRow row1 = worksheet.getRow(0);
			HSSFCell cellA1 = row1.getCell((short) 0);
			String a1Val = cellA1.getStringCellValue();
			HSSFCell cellB1 = row1.getCell((short) 1);
			String b1Val = cellB1.getStringCellValue();

			System.out.println("A1: " + a1Val);

			System.out.println("B1: " + b1Val);
			//inside values
			int rowsCount = worksheet.getLastRowNum();
			System.out.println("Total Number of Rows: " + (rowsCount + 1));
			increment=new int[rowsCount+1];
			count=rowsCount;
			int rowNum=1;
			int i = 1;
			int fileGet=0;
			for (i=1,fileGet=1; count>0; i++,fileGet++)
			{
				if(fileGet > rowsCount)
				{
					break;
				}
				HSSFRow row = worksheet.getRow(fileGet);
				HSSFCell cell1 = row.getCell((short)0);
				Double id=cell1.getNumericCellValue();
				// finding second column
				sqlIdentifier[fileGet]=id.toString();
				System.out.println("[" + fileGet + ",0 ]=" + sqlIdentifier[fileGet]);
				HSSFCell cell2 = row.getCell((short)1);
				String query=cell2.getStringCellValue();
				System.out.println("[" + fileGet + ",1 ]=" + query);

				query = query.trim();
				String delim = " ";
				//finding first column
				StringTokenizer tok = new StringTokenizer(query, delim, true);
				boolean expectDelim = false;

				if(tok.hasMoreTokens())
				{
					String token = tok.nextToken();
					if (delim.equals(token)) 
					{
						if (expectDelim) 
						{
							expectDelim = false;

						} 
						else {
							// unexpected delim means empty token
								token = null;

						}

					}
					System.out.println(token);


					typeOfSql[fileGet]=new String(token);
					
					expectDelim = true;
				}
			
				//finding rest of the column
				String sequences[]=query.split(" ");
				
				int targetCount[]=new int[rowsCount+1];
				targetCount[0]=1;//for heading ie. oth row it will be 1 only
				for(int fill=1; fill<=rowsCount;fill++)
				{
				targetCount[fill]=0;
				}
				int targetColumnCount[]=new int[rowsCount+1];
				targetColumnCount[0]=1;//for heading ie. oth row it will be 1 only
				for(int fill=1; fill<=rowsCount;fill++)
				{
					targetColumnCount[fill]=0;
				}
				int sourceCount[]=new int[rowsCount+1];
				sourceCount[0]=1;//for heading ie. oth row it will be 1 only
				for(int fill=1; fill<=rowsCount;fill++)
				{
					sourceCount[fill]=0;
				}
				int sourceColumnCount[]=new int[rowsCount+1];
				sourceColumnCount[0]=1;//for heading ie. oth row it will be 1 only
				for(int fill=1; fill<=rowsCount;fill++)
				{
					sourceColumnCount[fill]=0;
				}
				for(int j=0;j<sequences.length;j++)
				{

					//System.out.println(sequences[j]);
					if(sequences[j].equalsIgnoreCase("SELECT")||sequences[j].equalsIgnoreCase("INSERT")||sequences[j].equalsIgnoreCase("UPDATE")||sequences[j].equalsIgnoreCase("DELETE"))
					{
						//finding 3, 4 and 5th column targets
						if(sequences[j].equalsIgnoreCase("INSERT")||sequences[j].equalsIgnoreCase("UPDATE")||sequences[j].equalsIgnoreCase("DELETE"))
						{
							//for insert
							if(sequences[j].equalsIgnoreCase("INSERT"))
							{
								System.out.println("hey i am intoooooo of insert block"+sequences[j+1]);
								int spaces=3;
								if(sequences[j+1].equalsIgnoreCase("INTO"))
								{
									//3 and 4 col
									//System.out.println("hey i am table and db name"+sequences[j+2]);
									StringTokenizer targetSchemaTable = new StringTokenizer(sequences[j+2],".");
								
									if(targetSchemaTable.hasMoreTokens())
									{
										String dbName=targetSchemaTable.nextToken();
										System.out.println("heyyyyyyyyyyyyy i am db name "+dbName);//working
										//targetSchemaTable.nextToken();
										String tableName=targetSchemaTable.nextToken();
										System.out.println("heyyyyyyyyyyyyy i am table name "+tableName);//working
										tableName=tableName.trim();
										targetCount[rowNum]++;
										increment[rowNum]++;
										if(targetCount[rowNum]==1)
										{
											//store only ith value for 2 columns
											targetSchema[i]=dbName;
											targetTable[i]=tableName;
										}
										else if(targetCount[rowNum]>1)
										{
											
											//store to new values
											targetSchema[i+targetCount[rowNum]-1]=dbName;
											targetTable[i+targetCount[rowNum]-1]=tableName;
											

											//System.out.println(targetSchema[i+targetCount[rowNum]-1]+"jywsegd"+targetSchema[i+targetCount[rowNum]]);
											
										}


									}
									int comp1=(targetColumnCount[rowNum]>targetCount[rowNum])?(targetColumnCount[rowNum]):(targetCount[rowNum]);
									int comp2=(sourceColumnCount[rowNum]>sourceCount[rowNum])?(sourceColumnCount[rowNum]):(sourceCount[rowNum]);
									increment[rowNum]=(comp1>comp2)?(comp1):(comp2);//this many time same value should come in actual column
									i=i+increment[rowNum]-1;


								}
								
								
							}//end of insert
							//for delete
							else if(sequences[j].equalsIgnoreCase("DELETE"))
								
							{
								System.out.println("hey i am intooo delete block"+sequences[j+1]);
										if(sequences[j+1].trim().equalsIgnoreCase("FROM"))
										{
											int spaces=2;
											while(sequences[j+spaces].isEmpty())
											{
												spaces=spaces+ 1;
												//System.out.println(spaces);
												continue;
		
											}
										System.out.println("hey this i delete of ..............."+sequences[j+spaces]);
										StringTokenizer targetSchemaTable = new StringTokenizer(sequences[spaces+j],".  \n");
										
										if(targetSchemaTable.hasMoreTokens())
										{
											String dbName;
											String tableName=null;
											dbName=targetSchemaTable.nextToken();
											System.out.println("heyyyyyyyyyyyyy i am db name "+dbName);
											/*if(targetSchemaTable.hasMoreTokens())
											{
												StringTokenizer test= new StringTokenizer(targetSchemaTable.nextToken(),"\n");*/
												tableName=targetSchemaTable.nextToken().trim();
												
												System.out.println("heyyyyyyyyyyyyy i am table name "+tableName);
										//	}
											
		
											targetCount[rowNum]++;
											increment[rowNum]++;
											if(targetCount[rowNum]==1)
											{
												//store only ith value for 2 columns
												targetSchema[i]=dbName;
												targetTable[i]=tableName;
											}
											else if(targetCount[rowNum]>1)
											{
												//store to new values
												
												targetSchema[i+targetCount[rowNum]-1]=dbName;
												targetTable[i+targetCount[rowNum]-1]=tableName;
		
											}
											spaces++;
											
											while(sequences[j+spaces].isEmpty())
											{
												spaces=spaces+ 1;
												//System.out.println(spaces);
												continue;
		
											}
		
											if(sequences[j+spaces].trim().contentEquals("WHERE"))
											{
												spaces++;
												while(sequences[j+spaces].isEmpty())
												{
													spaces=spaces+ 1;
													//System.out.println(spaces);
													continue;
		
												}
												System.out.println("new spaces is "+sequences[j+spaces]);
											}
		
											System.out.println(sequences[j+spaces]+"after where next sequence is this.");
										}
										
										}
											//find columns
										//find sources for delete from where
								
								//increment i
								int comp1=(targetColumnCount[rowNum]>targetCount[rowNum])?(targetColumnCount[rowNum]):(targetCount[rowNum]);
								int comp2=(sourceColumnCount[rowNum]>sourceCount[rowNum])?(sourceColumnCount[rowNum]):(sourceCount[rowNum]);
								increment[rowNum]=(comp1>comp2)?(comp1):(comp2);//this many time sam evalue should come in actual column
								i=i+increment[rowNum]-1;
								//break;
								
							}//end of delete
							
							//now do for update
							else if(sequences[j].equalsIgnoreCase("UPDATE"))
							{
								System.out.println("hey i am intoooooooo update block"+sequences[j+1]);
								int spaces=1;
									while(sequences[j+spaces].isEmpty())
									{
										spaces=spaces+ 1;
										//System.out.println(spaces);
										continue;

									}
									
								System.out.println("hey this i update of ..............."+sequences[j+spaces]);
								StringTokenizer targetSchemaTable = new StringTokenizer(sequences[spaces+j],".");
								String dbName=null;
								String tableName=null;
								String aliasName=null;
								int size=0;
								if(targetSchemaTable.hasMoreElements())
								{
									size++;
									aliasName=targetSchemaTable.nextToken();
									System.out.println("size of tokens"+size);
								}
								if(size<2)
								{
									System.out.println("alias name encountered"+aliasName);
									
								}
									spaces++;
									while(sequences[j+spaces].isEmpty())
									{
										spaces=spaces+ 1;
										//System.out.println(spaces);
										continue;

									}
									System.out.println("we got "+sequences[j+spaces]);
								
								if(sequences[j+spaces].trim().equalsIgnoreCase("FROM"))
								{
									System.out.println("we got "+sequences[j+spaces]);
									spaces++;
									while(sequences[j+spaces].isEmpty())
									{
										spaces=spaces+ 1;
										//System.out.println(spaces);
										continue;

									}
									//again check dot present or not
									targetSchemaTable = new StringTokenizer(sequences[spaces+j],". ");
									
									if(targetSchemaTable.hasMoreElements())
									{
										
										dbName=targetSchemaTable.nextToken();
										if(targetSchemaTable.hasMoreElements())
										{
											
										tableName=targetSchemaTable.nextToken();
										}
										System.out.println("size of tokens"+size);
									}
								}
								
								
								else
								{
									System.out.println("alias name encountered"+aliasName);
									String sb="FROM";
										StringTokenizer test = new StringTokenizer(sequences[spaces+j],sb,true);
										String fromMiss=null;
										String requiredStringTok=null;
											int make=0;
											
												while(test.hasMoreTokens())
												{
													fromMiss=test.nextToken();
													while(make<3)
													{
														make++;
														fromMiss=fromMiss.concat(test.nextToken());
													}
													
													/*if(test.nextToken().contentEquals(" "))
													{
														test.nextToken();
													}*/
													requiredStringTok=test.nextToken();
												
												}
												StringTokenizer tokName = new StringTokenizer(sequences[spaces+j],".");	
												if(tokName.hasMoreTokens())
												{
													dbName=tokName.nextToken();
													if(tokName.hasMoreTokens())
													{
														tableName=tokName.nextToken();
													}
													
												}
												dbName=dbName.substring(5);
												
									
									
								}
								targetCount[rowNum]++;
								increment[rowNum]++;
								if(targetCount[rowNum]==1)
								{
									//store only ith value for 2 columns
									targetSchema[i]=dbName;
									targetTable[i]=tableName;
								}
								else if(targetCount[rowNum]>1)
								{
									//store to new values
									
									targetSchema[i+targetCount[rowNum]-1]=dbName;
									targetTable[i+targetCount[rowNum]-1]=tableName;

								}
								//increment i
								int comp1=(targetColumnCount[rowNum]>targetCount[rowNum])?(targetColumnCount[rowNum]):(targetCount[rowNum]);
								int comp2=(sourceColumnCount[rowNum]>sourceCount[rowNum])?(sourceColumnCount[rowNum]):(sourceCount[rowNum]);
								increment[rowNum]=(comp1>comp2)?(comp1):(comp2);//this many time sam evalue should come in actual column
								i=i+increment[rowNum]-1;
								
							}//end of update

							//finding 3, 4 and 5th column sources i.e. insert update and delete
							
						}//finding 3, 4 and 5th column sources i.e. insert update and delete
					}//for 3, 4 and 5 the column i.e.e target

					if(sequences[j].equalsIgnoreCase("SELECT"))
					{
						//check what except select can have, the source table n columns
					}//for 6 and 7 the column i.e sources

					//here compare target and sources count, which is highest, increase the value of i till there
				}//end of j for loop to get target and sources

				count--;
				rowNum++;
			}//end of i for loop for rows in xls file
			System.out.println("value of total number of rows, ie, i is"+i);
			actualRows=i;
				System.out.println("here are the op columns");
				System.out.println("typeOfSql[k] and sqlIdentifier[k]..............................");
				for(int k =0;k<typeOfSql.length;k++)
				{
					if(typeOfSql[k]==null)
					{
						break;
					}
					for(int repeat=0;repeat<increment[k];repeat++)
					{
					System.out.println(typeOfSql[k]);
					System.out.println(sqlIdentifier[k]);
					}
	
				}
				System.out.println("target schema  and table..............................");
				for(int k =0;k<i;k++)
				{
									
					System.out.println(targetSchema[k]);
					System.out.println(targetTable[k]);
					
	
				}
			/* fileOut = new FileOutputStream(filename);

	   	 HSSFRow rowStartPointer = outSheet.createRow((short)(i-1));
	        rowStartPointer.createCell((short)(i-1)).setCellValue(token);
		    workbook.write(fileOut);
	        fileOut.close();*/
			/* String filename = "D:/result1.xlsx" ;
	     HSSFWorkbook outWorkbook = new HSSFWorkbook();
		    HSSFSheet outSheet = outWorkbook.createSheet("Sheet1");
		 fileOut = new FileOutputStream(filename);
		 fileOut.flush();
		 for(int i =0; i<6;i++)
		 {
			 HSSFRow row = outSheet.createRow((short)i);
		     row.createCell((short)0).setCellValue(typeOfSql[i]);
		 }

		    workbook.write(fileOut);
	        fileOut.close();*/
				



		}catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}//end of try

		/*JFrame frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);


		Object rowData[][]={};
		
		Object columnNames[] = { "Type of SQL Statement", "SQL Identifier", "Target Schema / Database","SQL Identifier","SQL Identifier"};

		
		int fill=0;	
		
		for(int start=0;start<count;start++)
		{
			
			for(int repeat=0;repeat<increment[start];repeat++)
			{
			rowData[fill][1]=typeOfSql[fill];
			rowData[fill][2]=sqlIdentifier[fill];
			fill++;
			}
		}
			for(int row=0;row<4;row++)
			{
				
				rowData[row][3]= targetSchema[row];
				rowData[row][4]=targetTable[row];
				rowData[row][5]=targetColumn[row];
				rowData[row][6]=sourceSchema[row];
				rowData[row][7]=sourceTable[row];
				rowData[row][8]=sourceColumn[row];
				rowData[row][9]=transformation[row];
			}*/
			
		/*
		JTable table = new JTable(rowData, columnNames);


		JScrollPane scrollPane = new JScrollPane(table);

		frame.add(scrollPane, BorderLayout.CENTER);

		frame.setSize(300, 150);*/
		int fill=0;	
			for(int start=0;start<count;start++)
			{
				
				for(int repeat=0;repeat<increment[start];repeat++)
				{
				System.out.println(typeOfSql[fill]+" ");
				System.out.println(sqlIdentifier[fill]+" ");
				fill++;
				}
			}
		 
		        for (int column = 0; column < actualRows; column++) {
		        	
		            System.out.print(targetSchema[column] + " ");
		            System.out.print(targetTable[column] + " ");
		            System.out.print(targetColumn[column] + " ");
		            System.out.print(sourceSchema[column] + " ");
		            System.out.print(sourceTable[column] + " ");
		            System.out.print(sourceColumn[column] + " ");
		            System.out.print(transformation[column] + " ");
		            System.out.println();
		        }
		        
		   
		

	}//end of main method

}//end of class



