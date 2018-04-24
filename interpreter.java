package interpreter;
import java.util.*;
import java.io.*;


public class interpreter {
	public static void main(String[] args) throws IOException
	{
		//String input= "C:\\Users\\Manvijay\\Downloads\\TestCasesPart1\\sample_input3.txt";
		String input= "C:\\Users\\Manvijay\\Downloads\\TestCasesPart2\\in10.txt";
		String output= "C:\\Users\\Manvijay\\Downloads\\TestCasesPart3\\output_10.txt";
		interpreter(input,output);
	}
    
    // ===========================Nested Class definitions=================================
    
    public static class funct{
        public String ftype;
        public String fname;
        public List<String> l=new ArrayList<String>();
        public String arg;
        public int size;
        public NavigableMap<String,String> preFun=new TreeMap<String,String>();
        public Stack<String> s=new Stack<String> ();
        public Stack<NavigableMap<String,String>> mapStack= new Stack<NavigableMap<String,String>> ();
        
        public void disp()
        {
            System.out.println("Fucntion type: "+ftype);
            System.out.println("fname: "+fname);
            System.out.println("list: " +l);
            System.out.println("arg: "+arg);
            System.out.println("size: "+size);
            System.out.println("navigableMap"+ preFun);
            System.out.println("Stack:"+s);
            System.out.println("Mapstack: "+mapStack);
        }
    }
    
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<FUNCTION DEFINITIONS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	public static boolean isBoolean(String check)
	{
		if(check.equals(":true:") || check.equals(":false:") || check.equals(":unit:"))
		{
			return true;
		}
		else
			return false;
	}
	
	public static boolean toBool(String check)
	{
		if(check.contains(":true:"))
		return true;
		else
			return false;
	}
	
	public static boolean isInteger(String check)
	{
		try{
			Integer.parseInt(check);
			return true;
		}
		catch(NumberFormatException e)
		{
			
			//System.out.println("Hahahahahah.....");
			return false;
		}
	}
	
	public static boolean isFloat(String check)
	{
		try{
			Float.parseFloat(check);
			return true;
		}
		catch(NumberFormatException e)
		{
			
			//System.out.println("Hahahahahah.....");
			return false;
		}
	}
	
	public static boolean isDouble(String check)
	{
		try{
			Double.parseDouble(check);
			return true;
		}
		catch(NumberFormatException e)
		{
			
			//System.out.println("Hahahahahah.....");
			return false;
		}
	}
	
	public static boolean isName(String check)
	{
		String key="abcdefghijklmnopqrstuvwxyz";
		String Key="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		if(key.contains(check.substring(0,1)) || Key.contains(check.substring(0,1)))
		{
			return true;
		}
		else
			return false;
	}
	
	public static boolean isString(String check)
	{
		String key="abcdefghijklmnopqrstuvwxyz";
		String Key="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int s=0,cascii;
		char c;
		for(int i=0;i<check.length();i++)
		{
			c = check.charAt(i);
			cascii = (int)c;
			if(cascii <48 || (cascii > 57 && cascii < 65) || (cascii > 90 && cascii < 97) || cascii > 122)
				s++;
		}
		
		if(key.contains(check.substring(0,1)) || s>0 || Key.contains(check.substring(0,1)))
		{
			return true;
		}
		else
			return false;
	}
	
	public static boolean isValue(String check)
	{
		if(isInteger(check)|| isBoolean(check) || check.equals(":unit:") || isString(check) || check.charAt(0)=='"')
		return true;
		else
			return false;
	}
    
    // -----------------------------------------------------------------------------------------------------------------
    
    public static void s_push(String readString,Stack<String> s,NavigableMap<String,String> dic,int flag)
    {
       
        String text=readString.substring(5,readString.length());
        int i;
					
					if(isInteger(text))
					{
						i=Integer.parseInt(text);
						if(i==-0)
						i=0;
						text=Integer.toString(i);
						s.push(text);
						System.out.println("\nYO I am pushing this : "+s.peek() +"\n");
						
					}
					
					else if(isFloat(text)|| isDouble(text))
					{
						s.push(":error:");
					}
					
					else
					{
						String key1="abcdefghijklmnopqrstuvwxyz";
						String Key1="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
						
						if(text.charAt(0)=='"')
						{
							s.push(text.substring(1,text.length()-1));
							System.out.println("\nYO I am pushing this : "+s.peek() +"\n");
						}
						else if(key1.contains(text.substring(0,1))||Key1.contains(text.substring(0,1)))
						{
                            if(flag==1)
							 {
                                if(dic.containsKey(text))
                                    s.push(dic.get(text));
							     else
								    s.push(text);
                             }
                            else
                                s.push(text);
                            
							
							System.out.println("\nYO I am pushing this : "+s.peek() +"\n");
						}
						else
						{
							//s.push(text);
							//System.out.println("hahahahahhah"+text);
							s.push(":error:");
						}
					}
					
    }
    
    public static void s_pop(Stack<String> s)
    {
        if(s.isEmpty())
					{
						s.push(":error:");
						
					}
					else
					{
						s.pop();
					} 
    }
    
    public static void s_swap(Stack<String> s)
    {
        String swap1,swap2;
					swap1= s.pop();
					if (s.isEmpty())
					{
						s.push(swap1);
						s.push(":error:");
					}
					swap2= s.pop();
					//System.out.println("YO ");
					s.push(swap1);
					s.push(swap2);
    }
    
    public static void s_neg(Stack<String> s,NavigableMap<String,String> dic)
    {
        String a,temp;
					a=temp=s.pop();
					if(dic.containsKey(a))
					{
						temp=dic.get(a);
					}
					if(!isInteger(temp))
					{
						s.push(a);
						s.push(":error:");
					}
					else s.push(Integer.toString(-Integer.parseInt(temp)));
    }
    
    public static void s_op(String readString,Stack<String> s,NavigableMap<String,String> dic)
    {
        String temp1,temp2,opr1,opr2;
        int x,op1,op2;
					if (s.isEmpty())// if #1
					{
						s.push(":error:");
					}// if #1
					else// else #1
					{
						if(dic.containsKey(s.peek()))
						{
							temp1=s.pop();
							opr1= dic.get(temp1);
						}
						else
							temp1= opr1=s.pop();
						if (isInteger(opr1)) // if #2
						{
							op1=Integer.parseInt(opr1);
							if(!s.isEmpty())// if #3
							{
								if(dic.containsKey(s.peek()))
								{
									temp2=s.pop();
									opr2= dic.get(temp2);
								}
								else
									temp2= opr2=s.pop();
								if (isInteger(opr2)) // if #4
								{
									op2=Integer.parseInt(opr2);
									if (readString.equals("equal"))
									{
										if(op1==op2)
										s.push(":true:");
										else
											s.push(":false:");
									}
									else if (readString.equals("lessThan"))
									{
										if(op1>op2)
										s.push(":true:");
										else
											s.push(":false:");
									}
									else if(readString.equals("add")) // if #5
									{
										x=op2+op1;
										s.push(Integer.toString(x));
									}
									else if(readString.equals("sub"))
									{
										x=op2-op1;
										s.push(Integer.toString(x));
									}
									else if(readString.equals("mul"))
									{
										x=op2*op1;
										s.push(Integer.toString(x));
									}
									else if(readString.equals("div"))
									{
										if(op1==0)
										{
											s.push(temp2);
											s.push(temp1);
											s.push(":error:");
										}
										else
											
										{
											x=op2/op1;
											s.push(Integer.toString(x));
										}
									}
									else if(readString.equals("rem"))
									{
										if(op1==0)
										{
											s.push(temp2);
											s.push(temp1);
											s.push(":error:");
										}
										else
										{
											x=op2%op1;
											s.push(Integer.toString(x));
										}
									}
									else // if-else #5
									{
										String text=Integer.toString(op1);
										s.push(text);
										s.push(":error:");
									}// if-else #5
								}// if-else #4
								else
								{
									s.push(temp2);
									s.push(temp1);
									s.push(":error:");
								}
							} // if-else #3
							else
							{
								s.push(temp1);
								s.push(":error:");
							}
						}// if-else #2
						else
						{
							s.push(temp1);
							s.push(":error:");
						}
					}// if-else #1
    }
    
    public static void s_let(Stack<String> s,NavigableMap<String,String> dic,Stack<NavigableMap<String,String>> mapStack)
    {
        s.push("X");
        NavigableMap<String,String> preLet = new TreeMap<String, String>();
                    preLet.putAll(dic);
                    mapStack.push(preLet);
                    System.out.println("MAPSTACK:"+mapStack);
                    System.out.println(s);
                    System.out.println(dic);  
    }
    
    public static void s_end(Stack<String> s, NavigableMap<String,String> dic, Stack<NavigableMap<String,String>> mapStack)
    {
        System.out.println("Before Let: "+s + dic);
                    System.out.println("MAPSTACK:"+mapStack);
                    dic.clear();
                    dic.putAll(mapStack.pop());
                    System.out.println("After Let:"+ s + dic);
                    String t1=s.peek();
                    while(!(s.peek()).equals("X"))
                              s.pop();    
                    
                    System.out.println(s);
                    s.pop();
                    if(!t1.equals("X"))
                        s.push(t1);
                    System.out.println(s);
    }
    
    public static void s_if(Stack<String> s,NavigableMap<String,String> dic)
    {
        String temp1,temp2,temp3;
					boolean z;
					if (s.isEmpty())// if #1
					{
						s.push(":error:");
					}// if #1
					else// else #1
					{
						temp1= s.pop();
						if(!s.isEmpty())// if #2
						{
							temp2=s.pop();
							if(!s.isEmpty())// if-else#3
							{
								if(dic.containsKey(s.peek()))
								{
									temp3= dic.get(s.pop());
								}
								else
									temp3= s.pop();
								if(temp3==":true:"|| temp3==":false:")// if #4
								{
									z=toBool(temp3);
									if(z)
									s.push(temp1);
									else
										s.push(temp2);
									
								}// if-else #4
								else
								{
									s.push(temp3);
									s.push(temp2);
									s.push(temp1);
									s.push(":error:");
								}//if-else#4
							}
							else
							{
								s.push(temp2);
								s.push(temp1);
								s.push(":error:");
							}
						} //if-else #2
						else
						{
							s.push(temp1);
							s.push(":error:");
						}//if-else #2
					}//if-else #1
    }
    
    public static void s_not(Stack<String> s,NavigableMap<String,String> dic)
    {
        String temp;
					boolean ops;
					if (s.isEmpty())// if #1
					{
						s.push(":error:");
					}
					else
					{
						temp=s.pop();
						if (isBoolean((temp))|| dic.containsKey(temp))
						{
							if (dic.containsKey(temp))
							{
								ops = toBool(dic.get(temp));
								temp=Boolean.toString(!ops);
								s.push(":"+temp+":");
							}
							else
							{
								ops=toBool(temp);
								temp=Boolean.toString(!ops);
								s.push(":"+temp+":");	
							}
						}
						else
						{
							s.push(":error:");
						}
					}// if-else #1
    }
    
    public static void s_bind(Stack<String> s,NavigableMap<String,String> dic)
    {
        
					String temp1,temp2,name,value;
					if (s.isEmpty())// if #1
					{
						s.push(":error:");
					}// if #1
					else// else #1
					{
						if (isValue((s.peek())) || isName(s.peek()))//if #2
						{
							value=temp1=s.pop();
							if(isName(temp1)&&(!isInteger(temp1)))
							{
								if(dic.containsKey(temp1))
								value=dic.get(temp1);
								
							}
							if(!s.isEmpty() && isValue(value)&& !value.equals(":error:"))// if #3
							{
								temp2=name=s.pop();
								if(isName(temp2))// if #4
								{
									dic.put(name, value) ;
									s.push(":unit:");
									//System.out.print("\n yo"+ temp2+" : "+dic.get(temp2));
								}
								else/// if-else #4
								{
									s.push(temp2);
									s.push(temp1);
									s.push(":error:");
								}
							}//if #3
							else// if-else#3
							{
								s.push(temp1);
								s.push(":error:");
							}
						}// if-else#2
						else// if-else#2
						{
							s.push(":error:");
						}
					}//else#1
    }
    
    public static void s_andor(String readString,Stack<String> s,NavigableMap<String,String> dic)
    {
        try{
					String temp1,temp2,opr1,opr2,temp;
                    boolean opt,opf;
					if (s.isEmpty())// if #1
					{
						s.push(":error:");
					}// if #1
					else// else #1
					{
						if(dic.containsKey(s.peek()))
						{
							temp1=s.pop();
							opr1=dic.get(temp1);
						}
						else
						{
							temp1=opr1= s.pop();
						}
						
						if (isBoolean(opr1))//if #2
						{
							opt=toBool(opr1);
							if(!s.isEmpty())// if #3
							{
								if(dic.containsKey(s.peek()))
								{
									temp2= s.pop();
									opr2=dic.get(temp2);
								}
								else
								{
									temp2= opr2=s.pop();
								}
								if(isBoolean(opr2))// if #4
								{
									
									opf = toBool(opr2);
									if(readString.equals("and"))
									{
										temp=Boolean.toString(opt & opf);
										s.push(":"+temp+":");
									}
									
									else if(readString.equals("or"))
									{
										temp=Boolean.toString(opt | opf);
										s.push(":"+temp+":");
									}
								}// if-else #4
								else
								{
									s.push(temp2);
									s.push(temp1);
									s.push(":error:");
								}//if-else#4
							} //if-else #3
							else
							{
								s.push(temp1);
								s.push(":error:");
							}//if-else #3
						}//if #2
						else
						{
							s.push(temp1);
							s.push(":error:");
						}//if-else #2
					}//if-else #1
				}//try block
				catch(Exception e){System.out.println(e.getClass().getName());}
    }
    
    // -----------------------------------------------------------------------------------------------------------------
	
    public static Stack<String> copyStack(Stack<String> stack) {
    Stack<String> copiedStack = new Stack<String>();
    Queue<String> q = new LinkedList<String>();
    while (!stack.isEmpty()) {
      q.add(stack.pop());
    }
    while (!q.isEmpty()) {
      stack.push(q.remove());
    }
    while (!stack.isEmpty()) {
      q.add(stack.pop());
    }
    while (!q.isEmpty()) {
      copiedStack.push(q.peek());
      stack.push(q.remove());
    }
    return copiedStack;
  }
    
    public static void checkline( String readString ,Stack<String> s,NavigableMap<String,String> dic ,Stack<NavigableMap<String,String>> mapStack,int flag,funct fcode[],int fcounter)
    {
                
            if(readString.contains("push"))
				    s_push(readString,s,dic,flag);	
				
				else if(readString.contains("pop"))
				    s_pop(s);	
				
				else if(readString.contains("swap"))
					s_swap(s);
				else if(readString.contains("neg"))
					s_neg(s,dic);
				
				
				else if(readString.contains(":true:"))
					s.push(":true:");
				
				else if(readString.contains(":false:"))
					s.push(":false:");
				
				
				else if(readString.contains("add")| readString.contains("mul")| readString.contains("sub")| readString.contains("div") | readString.contains("rem") || readString.contains("equal") || readString.contains("lessThan"))
					s_op(readString,s,dic);  
				else if(readString.contains("let"))
				{
				    s_let(s,dic,mapStack);
                }
				
				else if(readString.contains("end"))
				{
                    s_end(s,dic,mapStack);
				}
				
				else if(readString.contains("if"))
				    s_if(s,dic);	
				
				else if(readString.contains("not"))
				    s_not(s,dic);	
				
				else if(readString.contains("bind"))
                    s_bind(s,dic);
			
				
				else if(readString.contains("and") || readString.contains("or")) 
                    s_andor(readString,s,dic);
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------        
                else if(readString.startsWith("call"))
                 {
                    if(s.isEmpty())
                        s.push(":error:");
                    else
                    {
                        String t1=s.pop();
                    System.out.println("Inside CALL: \n String t1:" + t1);
                    if(s.isEmpty())
                    {
                        s.push(t1);
                        s.push(":error:");
                    }
                        else
                    {String t2=s.pop();
                    System.out.println("String t2:" + t2);
                    if(t2.equals(":error:") || t1.equals(":error"))
                    {
                        
                        s.push(t2);
                        s.push(t1);
                        s.push(":error:");
                    }
                    else
                    {    
                    int k=fcounter-1;
                    int m=-1;
                    System.out.println("k: " + k);
                    while(k>=0)
                    {
                        fcode[k].disp();
                        if(fcode[k].fname.equals(t1))
                        {
                            String rLine;
                            m=k;
                            int tempsize=fcode[k].size;
                            fcode[k].disp();
                            if(dic.containsKey(t2))
                            {
                               fcode[k].preFun.put(fcode[k].arg,dic.get(t2)); 
                            }
                            else
                             fcode[k].preFun.put(fcode[k].arg,t2);
                            for(int i1=0;i1<tempsize;i1++)
                            {
                                rLine=fcode[k].l.get(i1);
                                
                                //-------------------------------------------------------------------------------------------------------------------------------------------
                                if(rLine.startsWith("return"))
                                {
                                    s.push(fcode[m].s.pop());   
                                }
                                else if(fcode[k].ftype.equals("inOutFun"))
                                    checkline(rLine,fcode[k].s,fcode[k].preFun,fcode[k].mapStack,0,fcode,fcounter);
                                else
                                    checkline(rLine,fcode[k].s,fcode[k].preFun,fcode[k].mapStack,1,fcode,fcounter);
                              //-------------------------------------------------------------------------------------------------------------------------------------------
                            }
                            System.out.println("Stack: "+fcode[k].s +"Dict : "+fcode[k].preFun+"Hello");
                            if(fcode[k].ftype.equals("inOutFun"))
                            {
                                dic.remove(t2);
                                dic.put(t2,fcode[k].preFun.get(fcode[k].arg));
                            }
                            System.out.println("Stack: "+fcode[k].s +"Dict : "+fcode[k].preFun+"Hii");
                            fcode[k].preFun.clear();
                            fcode[k].s.clear();
                            fcode[k].mapStack.clear();
                            break;
                            
                        }
                        k--;
                    }
                    if(m==-1)
                    { 
                        s.push(t2);
                        s.push(t1);
                        s.push(":error:");
                    
                    }
                    }}
                    }
                }
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------        
        
        
        System.out.println("Stack: "+s +"Dict : "+dic);
               
    }

    
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<FUNCTION DEFINITIONS OVER>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	public static void interpreter(String input1, String output1)throws IOException, FileNotFoundException
	{
		try{
			int i,op1=0,op2=0,x=0,j=0,strcounter=0;
			boolean opt,opf;
			NavigableMap<String,String> dic = new TreeMap<String, String>();
			Stack<NavigableMap<String,String>> mapStack= new Stack<NavigableMap<String,String>> ();
            String[] myStr = new String[100];
            int [] strcount=new int[20];

			Stack<String> s= new Stack<String> ();
			Scanner sc= new Scanner(new File(input1));
			BufferedWriter bw= new BufferedWriter(new FileWriter(output1));
            
            //----------------------------------------------------
            
            funct[] fcode=new funct[20];
            int fcounter=0;
            
            //----------------------------------------------------
			
			String readString,text;
			while(sc.hasNextLine())
			{
				readString= sc.nextLine();
				System.out.print(readString+ " 1 ");
				
                if(readString.startsWith("fun") || readString.startsWith("inOutFun"))
                {
                    //int temp=fcounter;
                    fcode[fcounter]=new funct();
                    System.out.println("xx");
                    Scanner sc1= new Scanner(readString);
                    String tt1=sc1.next();
                    
                    System.out.println(tt1+"   --- ");
                    String tt2=sc1.next();
                    System.out.println("Hello");
                    fcode[fcounter].ftype=tt1;
                    System.out.println(tt2);
                    
                    fcode[fcounter].fname=tt2;
                    System.out.println("xx"+fcode[fcounter].fname);
                    
                    fcode[fcounter].preFun.putAll(dic);
                    
                     System.out.println(dic+"   "+ fcode[fcounter].preFun);
                    
                    fcode[fcounter].s=copyStack(s);
                    System.out.println(s+"   "+ fcode[fcounter].s);
                    fcode[fcounter].arg=sc1.next();
                     System.out.println("xx arg"+fcode[fcounter].arg);
                    String t1;
                    int sizetemp=0;
                    fcode[fcounter].l.clear();
                    while(!(t1=sc.nextLine()).equals("funEnd"))
                    {
                        fcode[fcounter].l.add(t1); 
                        sizetemp++;
                    }   
                    fcode[fcounter].size=sizetemp;
                    fcode[fcounter].disp();
                    //fcounter=temp;
                    fcounter++;
                    s.push(":unit:");
                }
                else if(readString.contains("call"))
                {
                    if(s.isEmpty())
                        s.push(":error:");
                    else
                    {
                        String t1=s.pop();
                    System.out.println("Inside CALL: \n String t1:" + t1);
                    if(s.isEmpty())
                    {
                        s.push(t1);
                        s.push(":error:");
                    }
                        else
                    {String t2=s.pop();
                    System.out.println("String t2:" + t2);
                    if(t2.equals(":error:") || t1.equals(":error"))
                    {
                        
                        s.push(t2);
                        s.push(t1);
                        s.push(":error:");
                    }
                    else
                    {    
                    int k=fcounter-1;// -1 dala tha
                    int m=-1;
                    System.out.println("k: " + k);
                    while(k>=0)
                    {
                        fcode[k].disp();
                        if(fcode[k].fname.equals(t1))
                        {
                            String rLine;
                            m=k;
                            int tempsize=fcode[k].size;
                            fcode[k].disp();
                            if(dic.containsKey(t2))
                            {
                               fcode[k].preFun.put(fcode[k].arg,dic.get(t2)); 
                            }
                            else
                             fcode[k].preFun.put(fcode[k].arg,t2);
                            for(int i1=0;i1<tempsize;i1++)
                            {
                                rLine=fcode[k].l.get(i1);
                                
                                //-------------------------------------------------------------------------------------------------------------------------------------------
                                if(rLine.startsWith("return"))
                                {
                                    s.push(fcode[m].s.pop());   
                                }
                                else if(fcode[k].ftype.equals("inOutFun"))
                                    checkline(rLine,fcode[k].s,fcode[k].preFun,fcode[k].mapStack,0,fcode,fcounter);
                                else
                                    checkline(rLine,fcode[k].s,fcode[k].preFun,fcode[k].mapStack,1,fcode,fcounter);
                              //-------------------------------------------------------------------------------------------------------------------------------------------
                            }
                            System.out.println("Stack: "+fcode[k].s +"Dict : "+fcode[k].preFun+"Hello");
                            if(fcode[k].ftype.equals("inOutFun"))
                            {
                                dic.remove(t2);
                                dic.put(t2,fcode[k].preFun.get(fcode[k].arg));
                            }
                            System.out.println("Stack: "+fcode[k].s +"Dict : "+fcode[k].preFun+"Hii");
                            fcode[k].preFun.clear();
                            fcode[k].s.clear();
                            fcode[k].mapStack.clear();
                            break;
                            
                        }
                        k--;
                    }
                    if(m==-1)
                    { 
                        s.push(t2);
                        s.push(t1);
                        s.push(":error:");
                    
                    }
                    }}
                    }
                }
                else if(readString.contains("quit"))
				{
					System.out.print("FINAL STACK: ");
					while(!s.isEmpty())
					{
						text=s.pop();
						System.out.println(text);
						bw.write(text);
						bw.newLine();
					}
				}//quit ends
                else
                    checkline(readString,s,dic,mapStack,0,fcode,fcounter);
                System.out.println("Stack: "+s +"Dict : "+dic);	
		}// while
		
		sc.close();
		bw.close();
	}
	catch(Exception e)
	{
		System.out.println(e.getClass().getName());
	}
	
}//interpreter ends}
}//project1_1 ends

