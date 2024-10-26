package sample.dto;

import java.util.List;

/* Interface for a group of objects
 */

/**
 *
 * @author Hoa Doan
 */
public interface I_List {
  // Find the position of element which has code equal parameter coe
    int find(String code);

boolean add(); 

 void  update();

 boolean delete();
  // show detail of each element of List
  void show();
;
boolean saveToFile(String fileName);
 void loadFileToShow(String fileName);
// List<E> loadFromFile(String file);
}
