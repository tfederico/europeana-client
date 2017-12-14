package eu.europeana.api.client.search;

import static org.junit.Assert.assertTrue;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.junit.Test;

import eu.europeana.api.client.connection.EuropeanaConnection;
import eu.europeana.api.client.model.EuropeanaApi2Results;
import eu.europeana.api.client.model.search.EuropeanaApi2Item;
import eu.europeana.api.client.search.common.EuropeanaFields;
import eu.europeana.api.client.search.common.EuropeanaOperators;
import eu.europeana.api.client.search.query.EuropeanaComplexQuery;
import eu.europeana.api.client.search.query.adv.EuropeanaOperand;
import eu.europeana.api.client.search.query.adv.EuropeanaSearchTerm;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ComplexSearchTest {

    /*private static Object lock = new Object();
	@Test
	public void testComplexSearch() throws IOException{
		
		 long ms0 = System.currentTimeMillis();

         //build a complex query
         EuropeanaOperand opA = new EuropeanaOperand("girl");
         EuropeanaOperand opB = new EuropeanaOperand("pearl");
         EuropeanaOperand opC = new EuropeanaOperand("painting");
         EuropeanaOperand opSimple = new EuropeanaOperand("vermeer");
         EuropeanaOperand opComplex = new EuropeanaOperand(EuropeanaOperators.AND, opA, opB);
         opComplex.addOperand(EuropeanaOperators.AND, opC);
         EuropeanaOperand opComplex2 = new EuropeanaOperand(EuropeanaOperators.OR, opComplex, opSimple);
         EuropeanaSearchTerm stSimple = new EuropeanaSearchTerm(EuropeanaFields.TITLE, opSimple);
         EuropeanaSearchTerm stComplex = new EuropeanaSearchTerm(EuropeanaFields.TITLE, opComplex2);
         stComplex.addSearchTerm(EuropeanaOperators.OR, stSimple);
         
         //OR A QUICK AND SIMPLE SEARCH:
         //stComplex = new EuropeanaSearchTerm(EuropeanaFields.CREATOR, "jan vermeer");
         EuropeanaComplexQuery europeanaQuery = new EuropeanaComplexQuery(stSimple);
         //set query type
         europeanaQuery.setType(EuropeanaComplexQuery.TYPE.IMAGE);
         
         //invoke the search api

         EuropeanaConnection europeanaConnection = new EuropeanaConnection();
         final int FECTHED_RESULTS_COUNT = 20;
		EuropeanaApi2Results res = europeanaConnection.search(europeanaQuery, FECTHED_RESULTS_COUNT, 0);
         
         long t = System.currentTimeMillis() - ms0;
         System.out.println("*** Response time (client + server processing): " + (t / 1000d) + " seconds");
         System.out.println("Results: " + res.getAllItems().size() + " / " + res.getTotalResults());
         
         //Check results
         //verify the size of fetched results
         assertTrue(FECTHED_RESULTS_COUNT == res.getAllItems().size());
         //verify results count
         assertTrue(FECTHED_RESULTS_COUNT < res.getTotalResults());
         
         //display results

         if (res.getAllItems().size() > 0) {
             List<EuropeanaApi2Item> items = res.getAllItems();
             for (int i = 0; i < items.size(); i++) {
                 EuropeanaApi2Item item = items.get(i);
                 System.out.println();
                 System.out.println("**** " + (i + 1));
                 System.out.println("Title: " + item.getTitle());
                 System.out.println("Europeana URL: " + item.getObjectURL());
                 System.out.println("Type: " + item.getType());
                 System.out.println("Creator: " + item.getDcCreator());
                 System.out.println("Thumbnail: " + item.getEdmPreview());
                 System.out.println("Data provider: "+ item.getDataProvider());
                 System.out.println("Id: "+ item.getId());
                 System.out.println("Guid: "+ item.getGuid());

                 /*Image image = null;
                 try {
                     URL url = new URL(item.getEdmPreview().get(0));
                     image = ImageIO.read(url);
                     final JFrame frame = new JFrame();

                     JLabel lblimage = new JLabel(new ImageIcon(image));
                     frame.getContentPane().add(lblimage, BorderLayout.CENTER);
                     frame.setSize(300, 400);
                     frame.setVisible(true);

                     Thread thr = new Thread() {
                         public void run() {
                             synchronized(lock) {
                                 while (frame.isVisible())
                                     try {
                                         lock.wait();
                                     } catch (InterruptedException e) {
                                         e.printStackTrace();
                                     }
                                 System.out.println("Working now");
                             }
                         }
                     };
                     thr.start();

                     frame.addWindowListener(new WindowAdapter() {

                         @Override
                         public void windowClosing(WindowEvent arg0) {
                             synchronized (lock) {
                                 frame.setVisible(false);
                                 lock.notify();
                             }
                         }

                     });

                     try {
                         thr.join();
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 } catch (IOException e) {

                 } catch (NullPointerException e){

                 }



             }
         }
	}*/
}
