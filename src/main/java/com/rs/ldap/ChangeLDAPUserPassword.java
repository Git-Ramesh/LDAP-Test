package com.rs.ldap;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

/**
 * @author ramesh
 * Referenced Url: https://coderanch.com/t/434357/engineering/Change-Ldap-Password
 */
public class ChangeLDAPUserPassword {

    public static void main(String[] args){
        DirContext dirContext = null;
        Hashtable<String, String> env = new Hashtable<String, String>(10, 1.0f);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
        env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        env.put(Context.SECURITY_CREDENTIALS, "secret");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        try {
            String oldPass = "12345";
            String newPass = "raju";
            String userSearchBase = "ou=people,dc=radiant,dc=com";
            String userName = "raju";
            
            dirContext = new InitialDirContext(env);
            Attributes attributes = new BasicAttributes();
            System.out.println("Connected");
            ModificationItem[] modificationItems = new ModificationItem[1];
           // modificationItems[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute("userPassword", oldPass));
            modificationItems[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", newPass));
            dirContext.modifyAttributes("cn="+userName+","+userSearchBase, modificationItems);
            System.out.println("Password Changed Successfully");
        } catch (NamingException ex) {
            ex.printStackTrace();
        } finally {
            if (dirContext != null) {
                try{
                    dirContext.close();
                } catch(NamingException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
