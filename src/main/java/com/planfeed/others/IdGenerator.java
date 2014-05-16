/*Copyright 2014 Santi Muñoz Mallofre
 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
package com.planfeed.others;


import java.util.Random;



public class IdGenerator {

	private static int ID_LENGHT = 12;
	private static IdGenerator uniqueInstance;
	private static String previousId;
	
	private IdGenerator(){}

	public static IdGenerator getInstance(){
		if(uniqueInstance == null){
			uniqueInstance=new IdGenerator();
			previousId=null;

		}
		return uniqueInstance;
	}
	
	public String generateId (){
		StringBuffer randomSequence = new StringBuffer();
		do{
			randomSequence = new StringBuffer();
			long milis = new java.util.GregorianCalendar().getTimeInMillis();
			Random r = new Random(milis);
			int i = 0;
			while ( i <ID_LENGHT){
				char c = (char)r.nextInt(255);
				if((c>='0' && c<='9')|| (c>='A' && c<='Z')||(c>='a'&&c<='z')){
					randomSequence.append(c);
					i ++;
				}
			}
		}while(randomSequence.toString().equalsIgnoreCase(previousId));
		previousId=randomSequence.toString();
		return randomSequence.toString();
	}
}
