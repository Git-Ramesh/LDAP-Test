package com.rs.ldap;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 *
 * @author ramesh
 */
public class ConnectToLDAP {

    public static void main(String[] args) {
        String ldapUrl = "ldap://localhost:10389/dc=radiant,dc=com";
        Hashtable env = new Hashtable();
        //Specify com.sun.jndi.ldap.LdapCtxFactory to use the LDAP service provider as the initial context.
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "cn=raju,ou=people,dc=radiant,dc=com");
        env.put(Context.SECURITY_CREDENTIALS, "raju");
        try {
            DirContext dirContext = new InitialDirContext(env);
            System.out.println("connected..");
            System.out.println("" + dirContext.getEnvironment());
            dirContext.close();
        } catch (NamingException ne) {
            ne.printStackTrace();
        } finally {
           
        }
    }

}
