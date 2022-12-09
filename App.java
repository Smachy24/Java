import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import model.Contact;
import model.Comparators.ContactComparatorName;
import model.Comparators.ContactComparatorDate;

public class App {

  private static Scanner scan = new Scanner(System.in);
  public static void main(String[] args) throws Exception {
    afficherMenu();
    while (true) {
        String choix = scan.nextLine();
        switch (choix) {
            case "1":
                ajouterContact();
                break;
            case "2":
                printContacts();
                break;

            case "3":
                System.out.println("MODIFIER CONTACT");
                modifyContact();
                break;

            case "4":
                deleteContact();
                break;

            case "5":
                System.out.println("Voici les contacts triés par noms");
                sortNames();
                break;
            case "6":
                System.out.println("Voici les contacts triés par date de naissance");
                sortDates();
                break;
            case "7":
                System.out.println("Recherche contact par nom");
                researchContact();
                break;
            case "q":
                scan.close();
                return;
            default:
                System.out.println("Boulet!!!!");
                break;
        }
        afficherMenu();
    }
}

    private static void setInputs(Contact contact){

        System.out.println("Saisir le nom:");
        contact.setNom(scan.nextLine());
        System.out.println("Saisir le prénom:");
        contact.setPrenom(scan.nextLine());

        do {
            try {
                System.out.println("Saisir le téléphone:");
                contact.setNumero(scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.println("Saisir le mail:");
                contact.setMail(scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.println("Saisir la date de naissance:");
                contact.setDateNaissance(scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println("Error, try again!");
            }
        } while (true);
    }

    private static void ajouterContact() {
      Contact c = new Contact();
      setInputs(c); 
      c.enregistrer();

  }

    public static void printContacts(){

        ArrayList<Contact> contacts = Contact.getContact();
            for(Contact contact : contacts){
                System.out.println("Prénom : "+ contact.getPrenom() + " / Numéro : " + contact.getNumero());
            }
        
    }

    public static void clearFile(){
        try {
            FileWriter writer = new FileWriter("contacts.csv");
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteContact() {

        System.out.println("Quel est le numéro qui doit etre supprimé ?");
        String number = scan.nextLine();

        ArrayList<Contact> contacts = Contact.getContact();

        clearFile();

        for(Contact contact : contacts){
            if(!contact.getNumero().equals(number)){
                contact.enregistrer();
            }
            else{
                System.out.println("Le numero "+ contact.getNumero() + " a été supprimé");
            }
        }
    
    }

    public static void modifyContact(){

        System.out.println("Saississez le numero du contact qui doit être modifié");
        String number = scan.nextLine();
        ArrayList<Contact> contacts = Contact.getContact();

        clearFile();

        for(Contact contact : contacts){
            if(contact.getNumero().equals(number)){
                setInputs(contact);

            }
            contact.enregistrer();
        }
    }

    public static void sortNames(){ 
        ArrayList<Contact> contacts = Contact.getContact();
        ContactComparatorName comparator = new ContactComparatorName();
        Collections.sort(contacts, comparator);


        clearFile();
        for (Contact contact : contacts) {
            contact.enregistrer();
            System.out.println(MessageFormat.format("Name : {0} / FirstName : {1} / Email : {2} / Number : {3} / Birthday :{4} ",
                contact.getNom(), contact.getPrenom(), contact.getMail(), contact.getNumero(), contact.getDateNaissance()));
        } 

        System.out.println("Les contacts ont été triés");
    }


    public static void sortDates(){ 
        ArrayList<Contact> contacts = Contact.getContact();
        ContactComparatorDate comparator = new ContactComparatorDate();
        Collections.sort(contacts, comparator);


        clearFile();
        for (Contact contact : contacts) {
            contact.enregistrer();
            System.out.println(MessageFormat.format("Name : {0} / FirstName : {1} / Email : {2} / Number : {3} / Birthday :{4} ",
                contact.getNom(), contact.getPrenom(), contact.getMail(), contact.getNumero(), contact.getDateNaissance()));
        } 

        System.out.println("Les contacts ont été triés");
    }


    public static void researchContact(){

        System.out.println("Saississez le nom qui doit être recherché ?");
        String name = scan.nextLine();
        ArrayList<Contact> contacts = Contact.getContact();

        for(Contact contact : contacts){
            if(contact.getNom().equals(name)){
                System.out.println(MessageFormat.format("Name : {0} / FirstName : {1} / Email : {2} / Number : {3} / Birthday :{4} ",
                contact.getNom(), contact.getPrenom(), contact.getMail(), contact.getNumero(), contact.getDateNaissance()));
            }
        }
    }




    public static void afficherMenu() {
        ArrayList<String> menus = new ArrayList<>();
        menus.add("-- MENU --");
        menus.add("1- Ajouter un contact");
        menus.add("2- Lister les contacts");
        menus.add("3- Modifier un contact");
        menus.add("4- Supprimer un contact");
        menus.add("5- Trier par nom");
        menus.add("6- Trier par date de naissance");
        menus.add("7- Rechercher un contact grace au nom");
        menus.add("q- Quitter");
        for (String s : menus) {
            System.out.println(s);
        }
    }
}



