package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact{
    private String nom;
    private String prenom;
    private String numero;
    private String mail;
    private Date dateNaissance;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom.toUpperCase();
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) throws ParseException {
        Pattern pat = Pattern.compile("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$");
        Matcher test = pat.matcher(numero);
        if (test.matches()) {
            this.numero = numero;
        } else {
            throw new ParseException("Format du numéro incorrect.", 0);
        }
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) throws ParseException {
        Pattern pat = Pattern.compile("^[a-zA-Z0-9_.-]+@{1}[a-zA-Z0-9_.-]{2,}\\.[a-zA-Z.]{2,10}$");
        Matcher test = pat.matcher(mail);
        if (test.matches()) {
            this.mail = mail;
        } else {
            throw new ParseException("Format du mail incorrect.", 0);
        }
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) throws ParseException {
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        this.dateNaissance = dtf.parse(dateNaissance);
    }

    public void enregistrer() {
        try {
            FileWriter writer = new FileWriter("contacts.csv", true);
            writer.write(this.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Contact> getContact(){

        ArrayList<Contact> contacts = new ArrayList<Contact>();
        
        try{
            Scanner scan = new Scanner(new File("contacts.csv"));
    
            while(scan.hasNextLine()){
                String[] data = scan.nextLine().split(";");

                Contact contact = new Contact();

                try{
                    contact.setNom(data[0]);
                    contact.setPrenom(data[1]);
                    contact.setMail(data[2]);
                    contact.setNumero(data[3]);
                    contact.setDateNaissance(data[4]);
                    contacts.add(contact);
                }
                catch(ParseException e){
                    System.out.println("Error, essaie encore");
                }
            }
            scan.close();
    
        }catch(FileNotFoundException e){
            System.out.println("Le fichier n'existe pas veuillez renseigner un bon fichier");
            
        }
        return contacts;
        
    

    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append(getNom());
        build.append(";");
        build.append(getPrenom());
        build.append(";");
        build.append(getMail());
        build.append(";");
        build.append(getNumero());
        build.append(";");
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        build.append(dtf.format(getDateNaissance()));
        build.append("\n");
        return build.toString();
    }
}

