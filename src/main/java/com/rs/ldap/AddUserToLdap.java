package com.rs.ldap;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 *
 * @author ramesh
 */
public class AddUserToLdap {
    public static void main(String[] args) {
        DirContext dirContext = null;
        Hashtable<String,String> env = new Hashtable<String, String>(10, 1.0f);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
        env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        env.put(Context.SECURITY_CREDENTIALS, "secret");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        try{
            dirContext = new InitialDirContext(env);
            Attributes attributes = new BasicAttributes();
            System.out.println("Connected");
            
            Attribute objectClass = new BasicAttribute("objectClass", "organizationalPerson");
            Attribute cn = new BasicAttribute("cn", "raju");
            Attribute sn = new BasicAttribute("sn","p");
            Attribute description = new BasicAttribute("description", "Normal User");
            Attribute ou = new BasicAttribute("ou", "ou=user,ou=Icp,dc=radiant,dc=com");
            attributes.put("userPassword", "12345");
            
            attributes.put(objectClass);
            attributes.put(cn);
            attributes.put(sn);
            attributes.put(description);
            attributes.put(ou);
            dirContext.createSubcontext("cn=raju,ou=people,dc=radiant,dc=com", attributes);
            System.out.println("success");
        } catch(NamingException ne) {
            ne.printStackTrace();
        } finally {
            if(dirContext != null) {
                try{
                 dirContext.close();
                } catch(NamingException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
