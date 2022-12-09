package model.Comparators;

import java.util.Comparator;

import model.Contact;

public class ContactComparatorName implements Comparator<Contact>{
  @Override
  public int compare(Contact c1, Contact c2) {
      return c1.getNom().compareTo(c2.getNom());
  }
}
