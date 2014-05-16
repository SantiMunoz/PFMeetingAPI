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

import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooter extends PdfPageEventHelper {

	
	Date date;
    Phrase header;
    int pagenumber=0;
    
    public HeaderFooter(long dateInMilis) {
    	if(dateInMilis==0){
    		date = new Date();
    	}else{
    		date = new Date(dateInMilis);
    	}
		
	}

 
    public void onOpenDocument(PdfWriter writer, Document document) {
    	
        header = new Phrase(date.toString());
    }


   
    public void onStartPage(PdfWriter writer, Document document) {
        pagenumber++;
    }

   
    public void onEndPage(PdfWriter writer, Document document) {
        Rectangle rect = writer.getBoxSize("art");
       
         ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, header,
                    rect.getRight(), rect.getTop(), 0);

        
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber)),
                (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
    }

}
